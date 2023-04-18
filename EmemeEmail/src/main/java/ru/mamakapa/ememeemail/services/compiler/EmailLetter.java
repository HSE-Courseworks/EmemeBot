package ru.mamakapa.ememeemail.services.compiler;

import lombok.*;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailLetter {
    private String envelope;
    private String bodyPart;
    private List<File> files = new ArrayList<>();
}
