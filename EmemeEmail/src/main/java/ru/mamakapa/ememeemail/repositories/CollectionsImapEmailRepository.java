package ru.mamakapa.ememeemail.repositories;

import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.entities.ImapEmail;

import java.util.ArrayList;
import java.util.List;


@Repository
public class CollectionsImapEmailRepository {
    public final List<ImapEmail> imapEmails = new ArrayList<>();
}
