package ru.mamakapa.ememeemail.services;

import java.io.File;
import java.net.URI;

public interface FileUploader {
    String uploadFileAndGetDownloadLink(File file);
}
