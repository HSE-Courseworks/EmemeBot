package ru.mamakapa.ememebot.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.service.HtmlService;
import ru.mamakapa.ememebot.service.Translit;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@Service
public class YandexEmailCompiler extends AbstractEmailCompiler{
    private int level = 0;
    private int attNum = 0;
    private static int imgNum = 1;
    private List<String> attachments = new ArrayList<>();
    private List<String> htmls = new ArrayList<>();
    private String decodeMIMEB(String line) {
        try {
            String decodedLine = MimeUtility.decodeText(line);
            if (!decodedLine.equals(line)) return decodedLine;

            Pattern encoding = Pattern.compile("\\=\\?.*?\\?\\w\\?");
            Matcher matchEncods = encoding.matcher(line.toUpperCase());
            List<String> encodings = new ArrayList<>();
            while (matchEncods.find()) {
                encodings.add(line.substring(matchEncods.start(), matchEncods.end()).replaceAll(".*\\=\\?|\\?\\w\\?.*", ""));
            }

            Pattern inf = Pattern.compile("\\=\\?.*?\\=");
            Matcher matchEncInf = inf.matcher(line);
            List<String> passages = new ArrayList<>();
            while (matchEncInf.find()) {
                passages.add(line.substring(matchEncInf.start(), matchEncInf.end()).replaceAll("\\=\\?.*?\\?\\w\\?", ""));
            }

            String result = "";

            for (int i = 0; i < Math.min(encodings.size(), passages.size()); ++i) {
                String passage = passages.get(i);
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decocdedBytes = decoder.decode(passage);
                result += new String(decocdedBytes, encodings.get(i)) + " ";
            }
            result += line.replaceAll("\\=\\?.*?\\=", "");

            return result;
        }
        catch (Exception e){
            System.out.println("Decoder Exception:" + e.getMessage() + "\n\nReturning basic line");
            return line;
        }
    }
    @Override
    protected String processPart(Part p) throws Exception {
        StringBuilder bodyPart = new StringBuilder();
        if (p.isMimeType("text/*") && p.getDisposition() == null) {
            try {
                String content = (String) p.getContent();
                if (p.isMimeType("text/html")){
                    content = processHtml(content);
                }
                bodyPart.append(content).append("\n");
            }catch (Exception e){
                bodyPart.append("Извините, я не могу обработать эту часть письма :(\n");
            }
        }
        else if (p.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) p.getContent();
            level++;
            int mpCount = multipart.getCount();
            for (int i = 0; i < mpCount; ++i) {
                bodyPart.append(processPart(multipart.getBodyPart(i)));
            }
            level--;
        } else if (p.isMimeType("messege/rfc822")) {
            level++;
            bodyPart.append(processPart((Part) p.getContent()));
            level--;
        }

        if (level != 0 && (p instanceof MimeBodyPart) && !p.isMimeType("multipart/*")) {
            String filename = processAttachment(p);
            if (filename != null) bodyPart.append("Вложение: ").append(filename).append("\n");
        }

        return bodyPart.toString();
    }
    @Override
    protected String processEnvelope(Message message) throws Exception {
        StringBuilder envelope = new StringBuilder();
        Address[] addresses;
        if ((addresses = message.getFrom()) != null){
            envelope.append("От: ");
            for (Address address : addresses) {
                envelope.append(decodeMIMEB(address.toString())).append(" ");
            }
            envelope.append("\n");
        }
        envelope.append("Тема: ").append(message.getSubject()).append("\n");
        Date date = message.getSentDate();
        if (date != null){
            envelope.append("Отправленно: ").append(date).append("\n");
        }
        return envelope.toString();
    }
    @Override
    protected String processAttachment(Part p) throws Exception {
        String disp = p.getDisposition();
        String fileName = null;
        if (disp != null && disp.equalsIgnoreCase(Part.ATTACHMENT)){
            fileName = decodeMIMEB(p.getFileName());
            if (fileName == null) fileName = "NoName Attachment #" + attNum++;
            try {
                File file = new File(fileName);
                if (!file.exists()){
                    log.info("Saving attachment " + fileName);
                    String linkFile = SAVING_DIRECTORY + "attachments" + File.separator + Translit.cyrillicToLatin(fileName);
                    ((MimeBodyPart) p).saveFile(linkFile);
                    log.info(fileName + " saved!");
                    attachments.add(linkFile);
                }
            }
            catch (IOException e) {
                System.out.println("Failed to save attachment: " + e);
            }
        }
        return fileName;
    }
    @Override
    protected String processHtml(String html) throws Exception {
        log.info("Processing Html");
        String mesName = "message" + imgNum++ + ".png";
        String filePath = SAVING_DIRECTORY + "templates" + File.separator + mesName;
        try {
            HtmlService.convertHtmlToImage(html, filePath);
            htmls.add(filePath);
        } catch (Exception e) {
            log.info("Exception in first conversion, tries to delete img tags");
            try {
                HtmlService.convertHtmlToImage(HtmlService.deleteTag(html, "img"), filePath);
                htmls.add(filePath);
            } catch (Exception ex) {
                log.info("HTML processing exception!");
                throw ex;
            }
        }

        List<String> links = HtmlService.extractLinks(HtmlService.parseHTMLToXML(html));
        String linksPassage = "";
        if (links != null && links.size() != 0) {
            linksPassage = "Ссылки из сообщения:\n";
            for (String link : links) {
                linksPassage += link + "\n";
            }
        }
        return linksPassage;
    }

    @Override
    public EmailLetter constructLetter(Part p) throws Exception {
        EmailLetter letter = EmailLetter.builder()
                .envelope(processEnvelope((Message) p))
                .bodyPart(processPart(p))
                .attachmentFilePaths(attachments)
                .htmlFilePaths(htmls)
                .build();

        attachments = new ArrayList<>();
        htmls = new ArrayList<>();

        return letter;
    }
}
