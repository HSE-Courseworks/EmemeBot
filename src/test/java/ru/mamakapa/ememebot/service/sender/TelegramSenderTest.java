package ru.mamakapa.ememebot.service.sender;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import java.util.List;

@SpringBootTest
public class TelegramSenderTest {
    @Autowired
    TelegramSender telegramSender;
    @Value("${user.group_id}")
    private int USER_GROUP_ID;
    @Test
    public void sendLetter() throws SendMessageException {
        EmailLetter emailLetter = EmailLetter.builder()
                .bodyPart("Body part\n")
                .envelope("Envelope\n")
                .attachmentFilePaths(List.of(
                        "src\\main\\resources\\attachments\\SceneBuilder.exe.delete.me"
                ))
                .htmlFilePaths(List.of())
                .build();
        telegramSender.sendMessage(emailLetter, USER_GROUP_ID);
    }
}