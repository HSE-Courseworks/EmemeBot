package ru.mamakapa.ememeemail.services;

import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;

import java.io.File;

public interface FileUploader {
    void uploadFileToMessenger(File file, Long chatId, MessengerType messengerType);
}
