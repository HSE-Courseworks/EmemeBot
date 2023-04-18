package ru.mamakapa.ememeemail.services.compiler;

import ru.mamakapa.ememeemail.services.compiler.processors.AbstractPartProcessor;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface Compiler {
    EmailLetter compile(Message message) throws MessagingException, IOException;
}
