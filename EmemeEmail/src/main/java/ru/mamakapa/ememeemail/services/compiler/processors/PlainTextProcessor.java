package ru.mamakapa.ememeemail.services.compiler.processors;

import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;
import ru.mamakapa.ememeemail.services.compiler.parts.PlainTextPart;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class PlainTextProcessor extends AbstractPartProcessor{
    public PlainTextProcessor(AbstractPartProcessor next, Path savingPath) {
        super(next, savingPath);
    }

    @Override
    public MessagePart process(Part message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")){
            log.info("part is plain text");
            String content = (String) message.getContent();
            return new PlainTextPart(content);
        } else return new AttachmentProcessor(next, savingPath).process(message);
    }
}
