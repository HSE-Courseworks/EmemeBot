package ru.mamakapa.ememeemail.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.entities.jpa.BotUserEntity;

import java.util.Optional;

@Repository
public interface JpaBotUserRepository extends JpaRepository<BotUserEntity, Long> {
    Optional<BotUserEntity> findByChatIdAndType(Long chatId, MessengerType type);
    Integer deleteByChatIdAndType(Long chatId, MessengerType type);
}
