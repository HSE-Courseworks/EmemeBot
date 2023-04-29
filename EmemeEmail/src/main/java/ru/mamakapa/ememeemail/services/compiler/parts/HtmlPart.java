package ru.mamakapa.ememeemail.services.compiler.parts;

import java.io.File;
import java.net.URI;
import java.util.List;

public record HtmlPart(File image, List<URI> links) implements MessagePart {
}
