package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailLetter {
    private String Envelope;
    private String bodyPart;
    private List<String> attachmentFilePaths;
    private List<String> htmlFilePaths;
    private List<String> links;
}
