package ru.mamakapa.ememebot.service.sender;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vk.api.sdk.queries.upload.UploadDocQuery;
import com.vk.api.sdk.queries.upload.UploadPhotoMessageQuery;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
@Slf4j
public class VkSender extends AbstractSender {
    private TransportClient transportClient;
    private VkApiClient vk;
    private GroupActor actor;
    private Random random = new Random();
    public static final int GROUP_PEER_ID = 2000000000;
    private static final int MAX_MESSAGE_LENGTH = 4096;
    private static final Set<String> fileExtensionsDenied = new HashSet<>(Arrays.asList(
            "exe",
            "mp3"
    ));
    private static final Set<String> photoExtensions = new HashSet<>(Arrays.asList(
            "jpg",
            "png",
            "gif"));
    @Autowired
    public VkSender(VkBotConfig vkBotConfig){
        this.setBotConfig(vkBotConfig);
        this.transportClient = new HttpTransportClient();
        this.vk = new VkApiClient(this.transportClient);
        this.actor = new GroupActor(Integer.parseInt(vkBotConfig.getIdentificator()),
                vkBotConfig.getToken());
    }
    @Override
    public void sendMessage(EmailLetter emailLetter, int recipientId) throws SendMessageException {
        try {
            if(emailLetter.getEnvelope().length()+emailLetter.getBodyPart().length()<=MAX_MESSAGE_LENGTH) {
                MessagesSendQuery message = this.getMessagesSendQuery(recipientId).
                        message(emailLetter.getEnvelope() +
                                emailLetter.getBodyPart());
                addMessageAttachments(emailLetter, recipientId, message);
                log.info("Sending message");
                message.execute();
            }else {
                log.info("Splitting letter...");
                int currentMessageStartPosition = 0;
                log.info("Sending envelope");
                while (currentMessageStartPosition<emailLetter.getEnvelope().length()){
                    this.getMessagesSendQuery(recipientId).message(emailLetter.getEnvelope().
                                    substring(currentMessageStartPosition,
                                            Math.min(currentMessageStartPosition + MAX_MESSAGE_LENGTH, emailLetter.getEnvelope().length()))).
                            execute();
                    currentMessageStartPosition += MAX_MESSAGE_LENGTH;
                }
                log.info("Sending bodyPart");
                currentMessageStartPosition = 0;
                while (currentMessageStartPosition<emailLetter.getBodyPart().length()){
                    this.getMessagesSendQuery(recipientId).message(emailLetter.getBodyPart().
                                    substring(currentMessageStartPosition,
                                            Math.min(currentMessageStartPosition + MAX_MESSAGE_LENGTH, emailLetter.getBodyPart().length()))).
                            execute();
                    currentMessageStartPosition += MAX_MESSAGE_LENGTH;
                }
                MessagesSendQuery message = this.getMessagesSendQuery(recipientId);
                addMessageAttachments(emailLetter, recipientId, message);
                log.info("Sending files");
                message.execute();
            }
        } catch (ApiException | ClientException | IOException e) {
            throw new SendMessageException();
        }
    }

    private void addMessageAttachments(EmailLetter emailLetter, int recipientId, MessagesSendQuery message) throws IOException, ClientException, ApiException {
        StringBuilder attachmentStringBuilder = new StringBuilder("");
        if (emailLetter.getHtmlFilePaths().size() != 0) {
            for (String path : emailLetter.getHtmlFilePaths())
                attachmentStringBuilder.append(getUploadPhotoAttachId(new File(path), recipientId)).append(",");
        }
        if (emailLetter.getAttachmentFilePaths().size() != 0) {
            for (String path : emailLetter.getAttachmentFilePaths())
                attachmentStringBuilder.append(getUploadDocAttachId(new File(path), recipientId)).append(",");
        }
        if(!attachmentStringBuilder.isEmpty()){
            attachmentStringBuilder.delete(attachmentStringBuilder.length() - 1, attachmentStringBuilder.length());
        }
        message.attachment(attachmentStringBuilder.toString());
    }

    public MessagesSendQuery getMessagesSendQuery(int peerId){
        if(peerId>=GROUP_PEER_ID){
            return this.vk.messages().send(this.actor).chatId(peerId-GROUP_PEER_ID).randomId(random.nextInt(10000));
        }else{
            return this.vk.messages().send(this.actor).userId(peerId).randomId(random.nextInt(10000));
        }
    }
    public String getUploadDocAttachId(File file, int peerId) throws IOException, ClientException, ApiException, JSONException {
        log.info("Uploading document in attachments");
        String[] fileParts = file.getName().split("\\.");
        String fileExtension = fileParts[fileParts.length-1];
        if(fileExtensionsDenied.contains(fileExtension)){
            File tempFile = new File(file.getAbsolutePath()+".delete.me");
            if(!file.renameTo(tempFile)){
                throw new FileNotFoundException("Not successful renaming file...");
            }
            file = tempFile;
        }
        UploadDocQuery uploadDocQuery = vk.upload().doc(vk.docs().getMessagesUploadServer(actor).peerId(peerId).execute().
                getUploadUrl().toString(), file);
        JSONObject jsonObject = new JSONObject(uploadDocQuery.executeAsString());
        try{
            JSONObject json = new JSONObject(this.vk.docs().save(this.actor, jsonObject.getString("file")).executeAsString());
            JSONObject jsonObj = json.getJSONObject("response").getJSONObject("doc");
            return "doc"+jsonObj.getLong("owner_id")+"_"+jsonObj.getLong("id");
        }catch (JSONException e){
            System.out.println("Add extension '"+fileExtension+"' in DENIED EXTENSIONS!!!");
            return "";
        }
    }
    public String getUploadPhotoAttachId(File file, int peerId) throws IOException, ClientException, ApiException, JSONException {
        log.info("Uploading photo in attachments");
        URI url = vk.photos().getMessagesUploadServer(actor).peerId(peerId).execute().getUploadUrl();
        UploadPhotoMessageQuery uploadPhotoMessageQuery = vk.upload().photoMessage(url.toString(), file);
        JSONObject photoJsonObject = new JSONObject(uploadPhotoMessageQuery.executeAsString());
        JSONObject responseJsonObject = new JSONObject(vk.photos().
                saveMessagesPhoto(actor, photoJsonObject.getString("photo")).
                server(photoJsonObject.getInt("server")).
                hash(photoJsonObject.getString("hash"))
                .executeAsString());
        JSONObject content = responseJsonObject.
                getJSONArray("response").getJSONObject(0);
        int ownerId = content.getInt("owner_id");
        int id = content.getInt("id");
        return "photo"+ownerId+"_"+id;
    }
}
