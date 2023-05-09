package ru.mamakapa.ememeemail.services.updateSenders;

import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.entities.BotUser;

import java.io.File;
import java.util.List;

public interface FileUploader {
    void uploadFilesToUser(BotUser user, List<File> files);
    void uploadFileToMessenger(File file, Long chatId, MessengerType messengerType);
}
