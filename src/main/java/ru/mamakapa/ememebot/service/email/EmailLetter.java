package ru.mamakapa.ememebot.service.email;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmailLetter {
    private String envelope;
    private String bodyPart;
    private List<String> attachmentFilePaths;
    private List<String> htmlFilePaths;
}
