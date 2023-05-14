package ru.mamakapa.vkbot.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.docs.Doc;
import com.vk.api.sdk.objects.docs.responses.DocUploadResponse;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveMessagesPhotoResponse;
import lombok.extern.slf4j.Slf4j;
import ru.mamakapa.ememeSenderFunctionality.bot.service.FileSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.apache.commons.io.FilenameUtils.getExtension;

@Slf4j
public class VkFileSender implements FileSender {
    private final GroupActor groupActor;
    private final VkApiClient vkApiClient = new VkApiClient(new HttpTransportClient());
    private final Random random = new Random();
    private static final Set<String> FILE_EXTENSIONS_DENIED = new HashSet<>(Arrays.asList(
            "exe",
            "mp3",
            ""
    ));
    private static final Set<String> PHOTO_EXTENSIONS = new HashSet<>(Arrays.asList(
            "png",
            "jpg",
            "jpeg"
    ));

    private enum FileType {
        DOCUMENT, PHOTO
    }

    public VkFileSender(GroupActor groupActor) {
        this.groupActor = groupActor;
    }

    public VkFileSender(String botToken) {
        this(new GroupActor(0, botToken));
    }

    @Override
    public void send(long chatId, File file) {
        switch (getFileType(file)) {
            case PHOTO -> sendPhoto(chatId, file);
            case DOCUMENT -> sendDocument(chatId, file);
        }
    }

    private void sendPhoto(long chatId, File file) {
        try {
            String attachment = getUploadPhotoAttach((int) chatId, file);
            sendAttachment(chatId, attachment);
        } catch (ClientException | ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDocument(long chatId, File file) {
        try {
            String attachment = getUploadDocAttach((int) chatId, file);
            sendAttachment(chatId, attachment);
        } catch (ClientException | FileNotFoundException | ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendAttachment(long chatId, String attachment) throws ClientException, ApiException {
        vkApiClient.messages()
                .send(groupActor)
                .randomId(getRandomId())
                .peerId((int) chatId)
                .attachment(attachment)
                .execute();
    }

    private FileType getFileType(File file) {
        String extensionFile = getExtension(file.getName());
        if (PHOTO_EXTENSIONS.contains(extensionFile)) {
            return FileType.PHOTO;
        } else {
            return FileType.DOCUMENT;
        }
    }

    private String getUploadPhotoAttach(int chatId, File file)
            throws ClientException, ApiException {
        log.info("Uploading photo in attachments");
        String uploadUrl = vkApiClient
                .photos()
                .getMessagesUploadServer(groupActor)
                .peerId(chatId)
                .execute()
                .getUploadUrl()
                .toString();
        MessageUploadResponse uploadResponse = vkApiClient
                .upload()
                .photoMessage(uploadUrl, file)
                .execute();
        SaveMessagesPhotoResponse photoResponse = vkApiClient
                .photos().saveMessagesPhoto(groupActor, uploadResponse.getPhoto())
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute()
                .get(0);
        return "photo%s_%s".formatted(photoResponse.getOwnerId(), photoResponse.getId());
    }

    private String getUploadDocAttach(int chatId, File file)
            throws FileNotFoundException, ClientException, ApiException {
        log.info("Uploading document in attachments");
        String fileExtension = getExtension(file.getName());
        if (fileExtensionDenied(fileExtension)) {
            file = tryRenameFile(file);
        }
        String uploadUrl = vkApiClient
                .docs()
                .getMessagesUploadServer(groupActor)
                .peerId(chatId)
                .execute()
                .getUploadUrl()
                .toString();
        DocUploadResponse docResponse = vkApiClient
                .upload()
                .doc(uploadUrl, file)
                .execute();
        Doc doc = vkApiClient
                .docs()
                .save(groupActor, docResponse.getFile())
                .execute()
                .getDoc();
        return "doc%s_%s".formatted(doc.getOwnerId(), doc.getId());
    }

    private File tryRenameFile(File file) throws FileNotFoundException {
        File tempFile = new File(file.getAbsolutePath() + ".delete.me");
        if (!file.renameTo(tempFile)) {
            throw new FileNotFoundException("Not successful renaming file...");
        }
        return tempFile;
    }

    private boolean fileExtensionDenied(String extension) {
        return FILE_EXTENSIONS_DENIED.contains(extension);
    }

    private int getRandomId() {
        return random.nextInt(Integer.MAX_VALUE);
    }


}
