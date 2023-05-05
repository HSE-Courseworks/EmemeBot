package ru.mamakapa.ememeemail.services;

import com.dropbox.core.v2.DbxClientV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

@Slf4j
@Service
public class DropboxFileUploader implements FileUploader{
    final DbxClientV2 client;

    public DropboxFileUploader(DbxClientV2 client) {
        this.client = client;
    }

    @Override
    public String uploadFileAndGetDownloadLink(File file) {
        try {
            var filePath = uploadFile(file);
            return client.files().getTemporaryLink(filePath).getLink();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private String uploadFile(File file){
        String filePath = "/" + file.getName();
        try(InputStream in = new FileInputStream(file)) {
            client.files().upload(filePath).uploadAndFinish(in);
            return filePath;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
