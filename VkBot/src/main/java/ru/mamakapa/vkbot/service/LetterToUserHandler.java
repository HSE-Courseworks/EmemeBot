package ru.mamakapa.vkbot.service;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeSenderFunctionality.bot.service.FileSender;
import ru.mamakapa.ememeSenderFunctionality.bot.service.MessageSender;
import ru.mamakapa.vkbot.config.VkBotConfig;
import ru.mamakapa.vkbot.data.LetterToUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class LetterToUserHandler {
    private final MessageSender<Integer, String> messageSender;
    private final AWSService awsService;
    private final static Path FILES_DIRECTORY_PATH = Paths
            .get("EmemeEmail/src/main/resources/savedir")
            .toAbsolutePath();
    private final FileSender fileSender;

    public LetterToUserHandler(
            MessageSender<Integer, String> messageSender,
            AWSService awsService,
            VkBotConfig vkBotConfig
    ) {
        this.messageSender = messageSender;
        this.awsService = awsService;
        this.fileSender = new VkFileSender(vkBotConfig.token());
    }

    public void handle(LetterToUser letterToUser) throws Exception {
        int chatId = Math.toIntExact(letterToUser.chatId());
        List<File> files = letterToUser
                .fileKeys()
                .stream()
                .map(fileKey -> {
                    try {
                        return awsService.downloadFile(fileKey, FILES_DIRECTORY_PATH);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        messageSender.send(chatId, letterToUser.messageContent());
        files.forEach(file -> fileSender.send(chatId, file));
    }
}
