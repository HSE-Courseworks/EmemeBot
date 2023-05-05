package ru.mamakapa.ememeemail.services.compiler.processors;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;
import ru.mamakapa.ememeemail.services.compiler.parts.PlainTextPart;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.IOException;
<<<<<<< HEAD
import java.nio.file.Path;

@Slf4j
public class PlainTextProcessor extends AbstractPartProcessor{
    public PlainTextProcessor(AbstractPartProcessor next, Path savingPath) {
        super(next, savingPath);
=======

@Slf4j
public class PlainTextProcessor extends AbstractPartProcessor{
    public PlainTextProcessor(AbstractPartProcessor next) {
        super(next);
>>>>>>> telegramBot
    }

    @Override
    public MessagePart process(Part message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")){
            log.info("part is plain text");
            String content = (String) message.getContent();
            return new PlainTextPart(content);
        }
<<<<<<< HEAD
        else return new AttachmentProcessor(next, savingPath).process(message);
=======
        else return new AttachmentProcessor(next).process(message);
>>>>>>> telegramBot
    }
}
