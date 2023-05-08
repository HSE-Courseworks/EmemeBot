package ru.mamakapa.ememeemail.services.compiler;

import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EmailLetter {
    private String envelope;
    private String bodyPart;
    private List<File> files = new ArrayList<>();
}
