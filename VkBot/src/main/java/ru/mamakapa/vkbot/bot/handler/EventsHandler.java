package ru.mamakapa.vkbot.bot.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vk.api.sdk.events.Events;
import com.vk.api.sdk.objects.callback.MessageAllow;
import com.vk.api.sdk.objects.callback.MessageDeny;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EventsHandler {
    private static final String OVERRIDING_ERR = "Method of handling event is not overridden";
    private static final Logger LOG = LoggerFactory.getLogger(EventsHandler.class);
    protected final Gson gson = new Gson();

    public EventsHandler() {
    }

    private <T> T designateObject(JsonObject object, Events type) {
        return this.gson.fromJson(object, type.getType());
    }

    protected String confirmation() {
        LOG.error(OVERRIDING_ERR);
        return null;
    }

    protected void messageNew(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageReply(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageEdit(Integer groupId, Message message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageAllow(Integer groupId, MessageAllow message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected void messageDeny(Integer groupId, MessageDeny message) {
        LOG.error(OVERRIDING_ERR);
    }

    protected String parse(CallbackMessage message) {
        switch (message.getType()) {
            case CONFIRMATION -> {
                return this.confirmation();
            }
            case MESSAGE_NEW -> this.messageNew(message.getGroupId(),
                    gson.fromJson(
                            message.getObject().getAsJsonObject("message"),
                            message.getType().getType())
            );
            case MESSAGE_REPLY -> this.messageReply(
                    message.getGroupId(),
                    this.designateObject(message.getObject(), message.getType())
            );
            case MESSAGE_EDIT -> this.messageEdit(
                    message.getGroupId(),
                    this.designateObject(message.getObject(), message.getType())
            );
            case MESSAGE_ALLOW -> this.messageAllow(
                    message.getGroupId(),
                    this.designateObject(message.getObject(), message.getType())
            );
            case MESSAGE_DENY -> this.messageDeny(
                    message.getGroupId(),
                    this.designateObject(message.getObject(),
                            message.getType())
            );
            default -> {
                LOG.error("Unexpected callback event type received");
                return null;
            }
        }

        return "OK";
    }
}
