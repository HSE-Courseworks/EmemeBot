package ru.mamakapa.ememebot.service.sender;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import java.io.File;
import java.util.List;

@SpringBootTest()
public class SenderTest {

    @Autowired Sender sender;
    @Test
    void sendMessage() throws SendMessageException {
        String folderPath = System.getProperty("user.dir") +
                File.separator + "src" + File.separator + "main" + File.separator + "resources" +
                File.separator + "attachments" + File.separator;
        sender.sendMessage(EmailLetter.builder().
                htmlFilePaths(List.of()).
                attachmentFilePaths(List.of(folderPath+"SceneBuilder.exe.delete.me")).
                        envelope("HI").bodyPart("bp").build(),
                623783153);
    }
}