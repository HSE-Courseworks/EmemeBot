package ru.mamakapa.ememebot.service.email;

import javax.mail.Part;

public interface EmailCompiler {
    EmailLetter constructLetter(Part p) throws Exception;
}
