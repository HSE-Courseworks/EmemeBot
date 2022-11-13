package ru.mamakapa.ememebot.service.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;

public interface EmailCompiler {
    EmailLetter constructLetter(Part p) throws Exception;
}
