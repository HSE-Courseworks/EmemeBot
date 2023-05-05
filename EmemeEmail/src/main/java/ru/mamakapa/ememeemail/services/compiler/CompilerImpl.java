package ru.mamakapa.ememeemail.services.compiler;

import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.parts.AttachmentPart;
import ru.mamakapa.ememeemail.services.compiler.parts.HtmlPart;
import ru.mamakapa.ememeemail.services.compiler.parts.PlainTextPart;
import ru.mamakapa.ememeemail.services.compiler.processors.AbstractPartProcessor;
import ru.mamakapa.ememeemail.services.compiler.processors.HtmlTextProcessor;
import ru.mamakapa.ememeemail.services.compiler.processors.PlainTextProcessor;

import javax.mail.*;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.mamakapa.ememeemail.services.compiler.utils.MimeDecoder.decodeMIMEB;

@Slf4j
public class CompilerImpl implements Compiler{
    private final AbstractPartProcessor processor = new HtmlTextProcessor(new PlainTextProcessor(null));

    private final StringBuilder bodyPartOfLetter = new StringBuilder();

    @Override
    public EmailLetter compile(Message message) throws MessagingException, IOException {
        EmailLetter letter = new EmailLetter();
        letter.setEnvelope(compileEnvelope(message));
        if (message.isMimeType("multipart/*")){
            Multipart multipart = (Multipart) message.getContent();
            int mpCount = multipart.getCount();
            for (int i = 0; i < mpCount; ++i){
                buildLetter(multipart.getBodyPart(i), letter);
            }
        } else {
            buildLetter(message, letter);
        }
        letter.setBodyPart(bodyPartOfLetter.toString());
        return letter;
    }

    @Override
    public void deleteLetterFiles(EmailLetter letter) {
        log.info("Deleting files");
        for (var f : letter.getFiles()){
            if (f.exists()) {
                log.info("deleting " + f.getName());
                f.delete();
                log.info("file deleted");
            }
        }
    }

    public String compileEnvelope(Message message) throws MessagingException {
        StringBuilder envelope = new StringBuilder();
        Address[] addresses = message.getFrom();
        if (addresses != null){
            envelope.append("От: ");
            for (Address address : addresses) {
                var decodedAddress = decodeMIMEB(address.toString());
                envelope.append(decodedAddress).append(" ");
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

    private void buildLetter(Part part, EmailLetter letter) throws MessagingException, IOException {
        switch (processor.process(part)){
            case HtmlPart htmlPart -> {
                letter.getFiles().add(htmlPart.image());
                bodyPartOfLetter.append(getLinksForBodyPart(htmlPart.links()));
            }
            case PlainTextPart plainTextPart -> {
                bodyPartOfLetter.append(plainTextPart.content()).append("\n");
            }
            case AttachmentPart attachmentPart -> {
                letter.getFiles().add(attachmentPart.file());
                bodyPartOfLetter.append("Вложение: ").append(attachmentPart.file().getName()).append("\n");
            }
            case default -> log.info("Message part that cannot be processed");
        }
    }

    private String getLinksForBodyPart(List<URI> links){
        return "Ссылки из сообщения:\n" +
                links.stream()
                .map(URI::toString)
                .collect(Collectors.joining("\n"));
    }
}
