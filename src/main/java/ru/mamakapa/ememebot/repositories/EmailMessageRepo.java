package ru.mamakapa.ememebot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mamakapa.ememebot.entities.EmailMessage;

@Repository
public interface EmailMessageRepo extends JpaRepository<EmailMessage, Long> {

}
