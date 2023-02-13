package ru.mamakapa.ememebot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mamakapa.ememebot.entities.EmailMessage;

import java.util.List;

@Repository
public interface EmailMessageRepo extends JpaRepository<EmailMessage, Long> {
    List<EmailMessage> getEmailMessagesByImapEmailId(String ImapEmailId);
    void deleteAllByImapEmailId(String imapEmailId);
    void deleteById(Long id);
    EmailMessage getTopByOrderBySendDateAsc();

    EmailMessage getTopByOrderByIdAsc();

}
