package ru.mamakapa.ememeemail.services.compiler.processors;

import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;

import javax.mail.MessagingException;
import javax.mail.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractPartProcessor {

    protected final static Path SAVING_PATH = new File(".").toPath()
            .toAbsolutePath()
            .getParent()
            .resolve("src")
            .resolve("main")
            .resolve("resources")
            .resolve("savedir");

    protected AbstractPartProcessor next;

    public AbstractPartProcessor setNext(AbstractPartProcessor next){
        this.next = next;
        return this.next;
    }

    public AbstractPartProcessor(AbstractPartProcessor next){
        this.next = next;
    }

    public abstract MessagePart process(Part message) throws MessagingException, IOException;
}
