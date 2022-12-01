package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.File;

@Getter
@Setter
@Service
public class EmailService {
    final private EmailCompiler emailCompiler;
    final private EmailConnection emailConnection;

    public EmailService(EmailCompiler emailCompiler, EmailConnection emailConnection) {
        this.emailCompiler = emailCompiler;
        this.emailConnection = emailConnection;
    }

    public boolean deleteLetter(EmailLetter letter){
        for (String filePath : letter.getAttachmentFilePaths()){
            File file = new File(filePath);
            if (file.exists()){
                if (!file.delete()) return false;
            }
        }
        letter.setAttachmentFilePaths(null);
        for (String filePath : letter.getHtmlFilePaths()){
            File file = new File(filePath);
            if (file.exists()){
                if (!file.delete()) return false;
            }
        }
        letter.setHtmlFilePaths(null);
        letter.setBodyPart(null);
        letter.setEnvelope(null);
        return true;
    }
}
