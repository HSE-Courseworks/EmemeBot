package ru.mamakapa.ememeemail.services.updateSenders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.entities.BotUser;
import ru.mamakapa.telegramBot.service.TelegramFileSender;
import ru.mamakapa.vkbot.service.VkFileSender;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileUploaderImpl implements FileUploader{
    private final VkFileSender vkFileSender;
    private final TelegramFileSender telegramFileSender;

    @Override
    public void uploadFilesToUser(BotUser user, List<File> files) {
        log.info("uploading files");
        for (var f : files){
            try {
                uploadFileToMessenger(f, user.getChatId(), user.getMessengerType());
            } catch (RuntimeException exception){
                log.info("Error sending file to user {} {}\nexception: {}",
                        user.getChatId(), user.getMessengerType(), exception.getMessage());
            }
        }
        log.info("files were uploaded");
    }

    @Override
    public void uploadFileToMessenger(File file, Long chatId, MessengerType messengerType) {
        switch (messengerType){
            case TG -> telegramFileSender.send(chatId, file);
            case VK -> vkFileSender.send(chatId, file);
        }
    }
}
