package ru.mamakapa.ememeemail.services.compiler.processors;

<<<<<<< HEAD
=======
import lombok.NoArgsConstructor;
>>>>>>> telegramBot
import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeemail.services.compiler.utils.HtmlService;
import ru.mamakapa.ememeemail.services.compiler.parts.HtmlPart;
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.File;
import java.io.IOException;
import java.net.URI;
<<<<<<< HEAD
import java.nio.file.Path;
=======
>>>>>>> telegramBot
import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
public class HtmlTextProcessor extends AbstractPartProcessor{
<<<<<<< HEAD
    public HtmlTextProcessor(AbstractPartProcessor next, Path savingPath) {
        super(next, savingPath);
=======
    public HtmlTextProcessor(AbstractPartProcessor next) {
        super(next);
>>>>>>> telegramBot
    }

    @Override
    public MessagePart process(Part message) throws MessagingException, IOException {
        if (message.isMimeType("text/html")) {
            log.info("part is html");
            String html = (String) message.getContent();
            File image = tryToSaveImage(html);
            var links = HtmlService.extractLinks(html).stream().map(URI::create).collect(Collectors.toList());
            return new HtmlPart(image, links);
        }
<<<<<<< HEAD
        else return new AttachmentProcessor(next, savingPath).process(message);
    }

    private File tryToSaveImage(String html) throws IOException {
        String filePath = savingPath + File.separator + Instant.now().toString() + ".png";
=======
        else return new AttachmentProcessor(next).process(message);
    }

    private File tryToSaveImage(String html) throws IOException {
        String filePath = SAVING_PATH + File.separator + Instant.now().toString() + ".png";
>>>>>>> telegramBot
        try {
            return HtmlService.saveHtmlAsImage(html, filePath);
        } catch (Exception ex) {
            log.info("Exception in first conversion, tries to delete img tags");
            return HtmlService.saveHtmlAsImage(HtmlService.deleteTag(html, "img"), filePath);
        }
    }
}
