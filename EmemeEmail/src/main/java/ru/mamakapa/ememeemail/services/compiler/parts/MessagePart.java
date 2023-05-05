package ru.mamakapa.ememeemail.services.compiler.parts;

public sealed interface MessagePart permits PlainTextPart, HtmlPart, AttachmentPart{
}
