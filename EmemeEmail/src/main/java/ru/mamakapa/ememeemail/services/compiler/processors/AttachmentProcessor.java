package ru.mamakapa.ememeemail.services.compiler.processors;

import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.parts.AttachmentPart;
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;
import ru.mamakapa.ememeemail.services.compiler.utils.Transliterate;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

import static ru.mamakapa.ememeemail.services.compiler.utils.MimeDecoder.decodeMIMEB;

@Slf4j
public class AttachmentProcessor extends AbstractPartProcessor{
    public AttachmentProcessor(AbstractPartProcessor next, Path savingPath) {
        super(next, savingPath);
    }

    @Override
    public MessagePart process(Part message) throws MessagingException, IOException {
        String disposition = message.getDisposition();
        if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)){
            log.info("part is attachment");
            var file = tryToSaveAttachment(message);
            return new AttachmentPart(file);
        } else return next == null ? null : next.process(message);
    }

    private File tryToSaveAttachment(Part message) throws MessagingException, IOException {
        var fileName = decodeMIMEB(message.getFileName());
        if (fileName == null) {
            fileName = "NoNameAttachment" + Instant.now().toString();
        }
        String filepath = savingPath + File.separator + Translit.cyrillicToLatin(fileName);
        File file = new File(filepath);
        if (!file.exists()) {
            log.info("Saving attachment " + fileName);
            ((MimeBodyPart) message).saveFile(filepath);
            log.info(fileName + " saved!");
        }
        return file;
    }
}
