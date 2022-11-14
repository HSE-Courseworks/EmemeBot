package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;

import javax.mail.Message;
import javax.mail.Part;

@Getter
@Setter
public abstract class AbstractEmailCompiler implements EmailCompiler{
    protected final static String SAVING_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\";

    protected abstract String processPart(Part p) throws Exception;
    protected abstract String processEnvelope(Message message) throws Exception;
    protected abstract String processAttachment(Part p) throws Exception;
    protected abstract String processHtml(String html) throws Exception;
}
