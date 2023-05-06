package ru.mamakapa.ememeemail.repositories.collections;

import org.springframework.stereotype.Repository;
import ru.mamakapa.ememeemail.entities.BotUser;
import ru.mamakapa.ememeemail.repositories.BotUserRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectionsBotUserRepository implements BotUserRepository {
    final List<BotUser> botUsers = new ArrayList<>();
    private static long AUTO_INCREMENT = 1;

    @Override
    public BotUser add(BotUser user) {
        if (botUsers.stream().noneMatch(e -> e.getChatId().equals(user.getChatId()))){
            user.setId(AUTO_INCREMENT++);
            botUsers.add(user);
            return user;
        }
        return null;
    }

    @Override
    public BotUser deleteById(Long id) {
        var emails = botUsers.stream().filter(e -> e.getId().equals(id)).toList();
        if (!emails.isEmpty()) {
            botUsers.remove(emails.get(0));
            return emails.get(0);
        }
        return null;
    }

    @Override
    public BotUser getUserByChatId(Long chatId) {
        var res = botUsers.stream().filter(e -> e.getChatId().equals(chatId)).toList();
        if (res.isEmpty()) return null;
        return res.get(0);
    }

    @Override
    public List<BotUser> getAll() {
        return botUsers;
    }

    @Override
    public BotUser deleteByChatId(Long chatId) {
        var emails = botUsers.stream().filter(e -> e.getChatId().equals(chatId)).toList();
        if (!emails.isEmpty()) {
            botUsers.remove(emails.get(0));
            return emails.get(0);
        }
        return null;
    }
}
