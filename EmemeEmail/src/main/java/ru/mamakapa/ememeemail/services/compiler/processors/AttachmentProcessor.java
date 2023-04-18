package ru.mamakapa.ememeemail.services.compiler.processors;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.utils.Translit;
import ru.mamakapa.ememeemail.services.compiler.parts.AttachmentPart;
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

import static ru.mamakapa.ememeemail.services.compiler.utils.MimeDecoder.decodeMIMEB;

@Slf4j
public class AttachmentProcessor extends AbstractPartProcessor{
    public AttachmentProcessor(AbstractPartProcessor next) {
        super(next);
    }

    @Override
    public MessagePart process(Part message) throws MessagingException, IOException {
        String disposition = message.getDisposition();
        if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)){
            log.info("part is attachment");
            var file = tryToSaveAttachment(message);
            return new AttachmentPart(file);
        }
        else return next == null ? null : next.process(message);
    }

    private File tryToSaveAttachment(Part message) throws MessagingException, IOException {
        var fileName = decodeMIMEB(message.getFileName());
        if (fileName == null) {
            fileName = "NoNameAttachment" + Instant.now().toString();
        }
        File file = new File(fileName);
        if (!file.exists()) {
            log.info("Saving attachment " + fileName);
            String linkFile = SAVING_PATH + Translit.cyrillicToLatin(fileName);
            ((MimeBodyPart) message).saveFile(linkFile);
            log.info(fileName + " saved!");
            return file;
        }
        else throw new MessagingException("File does not exist");
    }
}
