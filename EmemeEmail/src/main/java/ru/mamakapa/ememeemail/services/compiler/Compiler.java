package ru.mamakapa.ememeemail.services.compiler;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface Compiler {
    EmailLetter compile(Message message) throws MessagingException, IOException;

    void deleteLetterFiles(EmailLetter letter);
}
