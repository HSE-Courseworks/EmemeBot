package ru.mamakapa.ememebot.service.sender;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

public interface Sender {
    void sendMessage() throws SendMessageException;
}
