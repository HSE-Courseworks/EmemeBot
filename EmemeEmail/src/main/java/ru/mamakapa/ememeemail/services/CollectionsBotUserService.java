package ru.mamakapa.ememeemail.services;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.entities.BotUser;
import ru.mamakapa.ememeemail.exceptions.BadRequestEmemeException;
import ru.mamakapa.ememeemail.exceptions.NotFoundEmemeException;
import ru.mamakapa.ememeemail.repositories.collections.CollectionsBotUserRepository;

@Service
public class CollectionsBotUserService implements BotUserService{

    final CollectionsBotUserRepository userRepository;

    public CollectionsBotUserService(CollectionsBotUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(Long chatId) {
        if (userRepository.add(new BotUser(chatId)) == null)
            throw new BadRequestEmemeException("User with chatId = " + chatId + " already registered");
    }

    @Override
    public void delete(Long chatId) {
        if (userRepository.deleteByChatId(chatId) == null)
            throw new NotFoundEmemeException("User with chatId = " + chatId + " does not exist");
    }
}
