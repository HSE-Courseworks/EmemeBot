package ru.mamakapa.ememeemail.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.telegramBot.service.TelegramFileSender;
import ru.mamakapa.vkbot.service.VkFileSender;

import java.io.File;

@RequiredArgsConstructor
@Service
public class FileUploaderImpl implements FileUploader{
    private final VkFileSender vkFileSender;
    private final TelegramFileSender telegramFileSender;

    @Override
    public void uploadFileToMessenger(File file, Long chatId, MessengerType messengerType) {
        switch (messengerType){
            case TG -> telegramFileSender.send(chatId, file);
            case VK -> vkFileSender.send(chatId, file);
        }
    }
}
