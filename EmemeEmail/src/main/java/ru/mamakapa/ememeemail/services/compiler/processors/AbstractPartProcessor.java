package ru.mamakapa.ememeemail.services.compiler.processors;

import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractPartProcessor {

    protected final Path savingPath;

    protected AbstractPartProcessor next;

    public AbstractPartProcessor setNext(AbstractPartProcessor next){
        this.next = next;
        return this.next;
    }

    public AbstractPartProcessor(AbstractPartProcessor next, Path savingPath){
        this.savingPath = savingPath;
        this.next = next;
    }

    public abstract MessagePart process(Part message) throws MessagingException, IOException;
}
