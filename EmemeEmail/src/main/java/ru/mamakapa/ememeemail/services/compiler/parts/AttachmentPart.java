package ru.mamakapa.ememeemail.services.compiler.parts;

import java.io.File;

public record AttachmentPart(File file) implements MessagePart {
}
