package ru.mamakapa.vkbot.bot.handler;

import com.google.gson.JsonObject;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CallbackHandler extends EventsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CallbackHandler.class);
    private final String confirmationCode;
    private final String secretKey;

    private boolean isSecretKeyValid(String receivedKey) {
        if (this.secretKey == null && receivedKey == null) {
            return true;
        } else {
            return this.secretKey != null && this.secretKey.equals(receivedKey);
        }
    }

    protected String confirmation() {
        return this.confirmationCode;
    }

    protected CallbackHandler(String confirmationCode) {
        this.confirmationCode = confirmationCode;
        this.secretKey = null;
    }

    protected CallbackHandler(String confirmationCode, String secretKey) {
        this.confirmationCode = confirmationCode;
        this.secretKey = secretKey;
    }

    public String parse(String json) {
        return this.parse(this.gson.fromJson(json, CallbackMessage.class));
    }

    public String parse(JsonObject json) {
        return this.parse(this.gson.fromJson(json, CallbackMessage.class));
    }

    public String parse(CallbackMessage message) {
        if (this.isSecretKeyValid(message.getSecret())) {
            return super.parse(message);
        } else {
            LOG.error("Secret key check was failed");
            return "failed";
        }
    }
}
