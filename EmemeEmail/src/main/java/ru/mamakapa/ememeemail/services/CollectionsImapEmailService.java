package ru.mamakapa.ememeemail.services;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.entities.ImapEmail;
import ru.mamakapa.ememeemail.repositories.CollectionsImapEmailRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionsImapEmailService implements ImapEmailService{

    final CollectionsImapEmailRepository repository;

    public CollectionsImapEmailService(CollectionsImapEmailRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ImapEmail> getAllEmailsByBotId(Long userId) {
        return repository.imapEmails.stream().filter(e -> e.getBotId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public ImapEmail add(Long chatId, String email, String password, String host) {
        ImapEmail imapEmail = ImapEmail.builder()
                .id((long) (repository.imapEmails.size()+1))
                .botId(chatId)
                .email(email)
                .appPassword(password)
                .host(host)
                .lastChecked(Timestamp.from(Instant.now()))
                .lastMessageTime(Timestamp.from(Instant.now()))
                .build();
        repository.imapEmails.add(imapEmail);
        return imapEmail;
    }

    @Override
    public ImapEmail remove(Long chatId, String email) {
        var emailToDelete = repository.imapEmails.stream()
                .filter(e -> e.getBotId().equals(chatId) && e.getEmail().equals(email))
                .toList();
        repository.imapEmails.removeAll(
                emailToDelete
        );
        return emailToDelete.get(0);
    }

    @Override
    public ImapEmail patch(ImapEmail emailWithUpdates) {
        var emailToChange = repository.imapEmails.stream()
                .filter(e -> e.getId().equals(emailWithUpdates.getId()))
                .toList()
                .get(0);
        repository.imapEmails.remove(emailToChange);
        repository.imapEmails.add(emailWithUpdates);
        return emailWithUpdates;
    }
}
