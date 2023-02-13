package ru.mamakapa.ememebot.repositories;

import org.springframework.stereotype.Repository;
import ru.mamakapa.ememebot.entities.EmailMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmailMessageRepoImpl{
    @PersistenceContext
    private EntityManager entityManager;
    public List<EmailMessage> findOrderedBySeatNumberLimitedTo(int limit) {
        return entityManager.createQuery("SELECT em FROM EmailMessage em ORDER BY em.sendDate",
                EmailMessage.class).setMaxResults(limit).getResultList();
    }
}
