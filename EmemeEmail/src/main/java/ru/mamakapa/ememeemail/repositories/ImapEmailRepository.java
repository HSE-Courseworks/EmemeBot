package ru.mamakapa.ememeemail.repositories;

import ru.mamakapa.ememeemail.entities.ImapEmail;

import java.util.List;

public interface ImapEmailRepository {
    ImapEmail add(ImapEmail imapEmail);
    ImapEmail deleteById(Long id);
    List<ImapEmail> getAll();
    List<ImapEmail> getAllByBotId(Long botId);
    ImapEmail getByEmail(String email);
    ImapEmail deleteByBotIdAndEmail(Long botId, String email);
    void updateById(ImapEmail email);
}
