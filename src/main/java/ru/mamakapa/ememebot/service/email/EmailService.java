package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Getter
@Setter
@Service
@Slf4j
public class EmailService {
    final private EmailCompiler emailCompiler;
    final private EmailConnection emailConnection;

    public EmailService(EmailCompiler emailCompiler, EmailConnection emailConnection) {
        this.emailCompiler = emailCompiler;
        this.emailConnection = emailConnection;
    }

    public boolean deleteLetter(EmailLetter letter){
        log.info("Deleting attachments");
        boolean result = true;
        for (String filePath : letter.getAttachmentFilePaths()){
            File file = new File(filePath);
            if (file.exists()){
                if (!file.delete()) result = false;
            }
        }
        letter.setAttachmentFilePaths(null);
        log.info("Attachments deleted");
        log.info("Deleting Htmls");
        for (String filePath : letter.getHtmlFilePaths()){
            File file = new File(filePath);
            if (file.exists()){
                if (!file.delete()) result = false;
            }
        }
        letter.setHtmlFilePaths(null);
        log.info("Htmls deleted");
        letter.setBodyPart(null);
        letter.setEnvelope(null);
        return result;
    }
}
