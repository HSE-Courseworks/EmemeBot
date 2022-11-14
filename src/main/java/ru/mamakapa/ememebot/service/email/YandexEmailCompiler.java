package ru.mamakapa.ememebot.service.email;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememebot.service.HtmlService;

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

@Service
public class YandexEmailCompiler extends AbstractEmailCompiler{
    private final HtmlService htmlService;
    private int level = 0;
    private int attNum = 0;
    private static int imgNum = 1;
    private List<String> attachments = new ArrayList<>();
    private List<String> htmls = new ArrayList<>();

    public YandexEmailCompiler(HtmlService htmlService) {
        this.htmlService = htmlService;
    }

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
        String bodyPart = "";
        try {
            if (p.isMimeType("text/html")) {
                bodyPart += processHtml((String) p.getContent()) + "\n";
            }
            else if (p.isMimeType("text/*") && p.getDisposition() == null) {
                bodyPart += (String) p.getContent() + "\n";
            }
            else if (p.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) p.getContent();
                level++;
                int mpCount = multipart.getCount();
                for (int i = 0; i < mpCount; ++i) {
                    bodyPart += processPart(multipart.getBodyPart(i));
                }
                level--;
            } else if (p.isMimeType("messege/rfc822")) {
                level++;
                bodyPart += processPart((Part) p.getContent());
                level--;
            }

            if (level != 0 && (p instanceof MimeBodyPart) && !p.isMimeType("multipart/*")) {
                String filename = processAttachment(p);
                if (filename != null) bodyPart += "Вложение: " + filename+ "\n";
            }
        }
        catch (Exception e){
            throw e;
        }

        return bodyPart;
    }
    @Override
    protected String processEnvelope(Message message) throws Exception {
        String envelope = "";
        Address[] addresses;
        if ((addresses = message.getFrom()) != null){
            envelope += "From:\n";
            for (Address address : addresses) {
                envelope += decodeMIMEB(address.toString()) + "\n";
            }
        }
        if ((addresses = message.getReplyTo()) != null){
            envelope += "Reply to:\n";
            for (Address address : addresses) {
                envelope += decodeMIMEB(address.toString()) + "\n";
            }
        }
        if ((addresses = message.getRecipients(Message.RecipientType.TO)) != null){
            envelope += "To:\n";
            for (Address address : addresses) {
                envelope += decodeMIMEB(address.toString()) + "\n";
                InternetAddress ia = (InternetAddress) address;
                if (ia.isGroup()) {
                    InternetAddress[] groupAddresses = ia.getGroup(false);
                    envelope += "    Group:\n";
                    for (InternetAddress groupAddress : groupAddresses) {
                        envelope += decodeMIMEB(groupAddress.toString());
                    }
                }
            }
        }
        envelope += "SUBJECT: " + message.getSubject() + "\n";
        Date date = message.getSentDate();
        if (date != null){
            envelope += "Send date: " + date + "\n";
        }
        return envelope;
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
                    String linkFile = SAVING_DIRECTORY + "attachments" + File.separator + fileName;
                    ((MimeBodyPart) p).saveFile(linkFile);
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
    protected String processHtml(String html) throws Exception{
        String mesName = "message" + imgNum++ + ".png";
        String filePath = SAVING_DIRECTORY + "templates" + File.separator + mesName ;
        htmlService.convertHtmlToImage(html, filePath);
        htmls.add(filePath);

        List<String> links = htmlService.extractLinks(htmlService.parseHTMLToXML(html));
        String linksPassage = "Ссылки из сообщения:\n";
        for (String link : links){
            linksPassage += link + "\n";
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