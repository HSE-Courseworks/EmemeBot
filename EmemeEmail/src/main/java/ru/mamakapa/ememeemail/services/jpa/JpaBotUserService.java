package ru.mamakapa.ememeemail.services.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.entities.jpa.BotUserEntity;
import ru.mamakapa.ememeemail.exceptions.BadRequestEmemeException;
import ru.mamakapa.ememeemail.exceptions.NotFoundEmemeException;
import ru.mamakapa.ememeemail.repositories.jpa.JpaBotUserRepository;
import ru.mamakapa.ememeemail.services.BotUserService;

import java.util.ArrayList;

@Service
public class JpaBotUserService implements BotUserService {

    final JpaBotUserRepository userRepository;

    public JpaBotUserService(JpaBotUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(Long chatId, MessengerType messengerType) {
        try {
            userRepository.save(new BotUserEntity(chatId, messengerType));
        } catch (Exception e){
            throw new BadRequestEmemeException(
                    String.format("User with id = %d and messenger type = %s already registered", chatId, messengerType));
        }

    }

    @Override
    @Transactional
    public void delete(Long chatId, MessengerType messengerType) {
        var deleteResult = userRepository.deleteByChatIdAndType(chatId, messengerType);
        if (deleteResult.equals(0)){
            throw new NotFoundEmemeException(
                    String.format("User with id = %d and messenger type = %s does not registered", chatId, messengerType));
        }
    }
}
