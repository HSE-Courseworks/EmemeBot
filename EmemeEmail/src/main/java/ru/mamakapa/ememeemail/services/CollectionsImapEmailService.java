package ru.mamakapa.ememeemail.services;

import org.springframework.stereotype.Service;
import ru.mamakapa.ememeemail.entities.ImapEmail;
import ru.mamakapa.ememeemail.exceptions.BadRequestEmemeException;
import ru.mamakapa.ememeemail.exceptions.NotFoundEmemeException;
import ru.mamakapa.ememeemail.repositories.BotUserRepository;
import ru.mamakapa.ememeemail.repositories.ImapEmailRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class CollectionsImapEmailService implements ImapEmailService{

    final ImapEmailRepository emailRepository;
    final BotUserRepository userRepository;

    public CollectionsImapEmailService(ImapEmailRepository repository, BotUserRepository userRepository) {
        this.emailRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ImapEmail> getAllEmailsForChatId(Long chatId){
        var user = userRepository.getUserByChatId(chatId);
        if (user == null) throw new NotFoundEmemeException("User with chatid = " + chatId + " is not registered");
        else return emailRepository.getAllByBotId(user.getId());
    }

    @Override
    public ImapEmail add(Long chatId, String email, String password, String host) {
        var user = userRepository.getUserByChatId(chatId);
        if (user == null) throw new NotFoundEmemeException("User with chatid = " + chatId + " is not registered");
        ImapEmail imapEmail = ImapEmail.builder()
                .botId(user.getId())
                .email(email)
                .appPassword(password)
                .host(host)
                .lastChecked(Timestamp.from(Instant.now()))
                .lastMessageTime(Timestamp.from(Instant.now()))
                .build();
        var res = emailRepository.add(imapEmail);
        if (res == null) throw new BadRequestEmemeException("This email already registered");
        return res;
    }

    @Override
    public ImapEmail remove(Long chatId, String email) {
        var user = userRepository.getUserByChatId(chatId);
        if (user == null) throw new NotFoundEmemeException("User with chatid = " + chatId + " is not registered");
        var res = emailRepository.deleteByBotIdAndEmail(user.getId(), email);
        if (res == null) throw new BadRequestEmemeException("This email does not subscribe by any user");
        return res;
    }

    @Override
    public void patch(ImapEmail emailWithUpdates) {
        emailRepository.updateById(emailWithUpdates);
    }
}
