package ru.mamakapa.ememeemail.services.compiler.processors;

<<<<<<< HEAD
=======
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
>>>>>>> telegramBot
import ru.mamakapa.ememeemail.services.compiler.parts.MessagePart;

import javax.mail.MessagingException;
import javax.mail.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractPartProcessor {

<<<<<<< HEAD
    protected final Path savingPath;
=======
    protected final static Path SAVING_PATH = new File(".").toPath()
            .toAbsolutePath()
            .getParent()
            .resolve("src")
            .resolve("main")
            .resolve("resources")
            .resolve("savedir");
>>>>>>> telegramBot

    protected AbstractPartProcessor next;

    public AbstractPartProcessor setNext(AbstractPartProcessor next){
        this.next = next;
        return this.next;
    }

<<<<<<< HEAD
    public AbstractPartProcessor(AbstractPartProcessor next, Path savingPath){
        this.savingPath = savingPath;
=======
    public AbstractPartProcessor(AbstractPartProcessor next){
>>>>>>> telegramBot
        this.next = next;
    }

    public abstract MessagePart process(Part message) throws MessagingException, IOException;
}
