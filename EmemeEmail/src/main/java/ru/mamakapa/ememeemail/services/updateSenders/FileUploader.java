package ru.mamakapa.ememeemail.services.updateSenders;

import java.io.File;
import java.util.List;

public interface FileUploader {
    List<String> uploadFiles(List<File> files);
}
