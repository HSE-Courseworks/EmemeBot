package ru.mamakapa.ememeemail.repositories.collections;

import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.entities.ImapEmail;
import ru.mamakapa.ememeemail.repositories.ImapEmailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class CollectionsImapEmailRepository implements ImapEmailRepository {
    final List<ImapEmail> imapEmails = new ArrayList<>();

    private static long AUTO_INCREMENT = 1;

    @Override
    public ImapEmail add(ImapEmail imapEmail) {
        if (imapEmails.stream().noneMatch(e -> e.getEmail().equals(imapEmail.getEmail()))){
            imapEmail.setId(AUTO_INCREMENT++);
            imapEmails.add(imapEmail);
            return imapEmail;
        }
        return null;
    }

    @Override
    public ImapEmail deleteById(Long id) {
        var emails = imapEmails.stream().filter(e -> e.getId().equals(id)).toList();
        if (!emails.isEmpty()) {
            imapEmails.remove(emails.get(0));
            return emails.get(0);
        }
        return null;
    }

    @Override
    public List<ImapEmail> getAll() {
        return imapEmails;
    }

    @Override
    public List<ImapEmail> getAllByBotId(Long botId) {
        return imapEmails.stream().filter(e -> e.getBotId().equals(botId)).toList();
    }

    @Override
    public ImapEmail getByEmail(String email){
        var res = imapEmails.stream().filter(e -> e.getEmail().equals(email)).toList();
        if (res.isEmpty()) return null;
        return res.get(0);
    }

    @Override
    public ImapEmail deleteByBotIdAndEmail(Long botId, String email) {
        var emails = imapEmails.stream().filter(e -> e.getBotId().equals(botId) && e.getEmail().equals(email)).toList();
        if (!emails.isEmpty()){
            imapEmails.remove(emails.get(0));
            return emails.get(0);
        }
        return null;
    }

    @Override
    public void updateById(ImapEmail email) {
        var emailToChange = imapEmails.stream()
                .filter(e -> e.getId().equals(email.getId()))
                .toList()
                .get(0);
        imapEmails.remove(emailToChange);
        imapEmails.add(email);
    }
}
