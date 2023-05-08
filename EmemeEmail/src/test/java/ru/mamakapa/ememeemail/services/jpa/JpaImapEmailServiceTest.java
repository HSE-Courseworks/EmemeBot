package ru.mamakapa.ememeemail.services.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.mamakapa.ememeemail.DTOs.requests.MessengerType;
import ru.mamakapa.ememeemail.IntegrationEnvironment;
import ru.mamakapa.ememeemail.exceptions.BadRequestEmemeException;
import ru.mamakapa.ememeemail.repositories.jpa.JpaBotUserRepository;
import ru.mamakapa.ememeemail.repositories.jpa.JpaImapEmailRepository;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaImapEmailServiceTest extends IntegrationEnvironment {

    @Autowired
    JpaImapEmailService emailService;
    @Autowired
    JpaBotUserService userService;
    @Autowired
    JpaBotUserRepository userRepository;
    @Autowired
    JpaImapEmailRepository emailRepository;

    @Test
    @Transactional
    @Rollback
    void getAllEmailsForChatId() {
        //given
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        var first = emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "jjdjasjajja", "host.yandex.ru");
        var second = emailService.add(888L, MessengerType.VK, "tutut@yandex.ru", "jjdjasjajja", "host.yandex.ru");
        var third = emailService.add(888L, MessengerType.VK, "kryakrya@yandex.ru", "jjdjasjajja", "host.yandex.ru");

        //when
        var res = emailService.getAllEmailsForChatId(888L, MessengerType.VK);

        //then
        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertEquals(first.getEmail(), res.get(0).getEmail()),
                () -> assertEquals(second.getId(), res.get(1).getId()),
                () -> assertEquals(third.getLastChecked(), res.get(2).getLastChecked())
        );
    }

    @Test
    @Transactional
    @Rollback
    void addSameEmailToDifferentUsers() {
        //when
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "jjdjasjajja", "host.yandex.ru");
        var testEmail = emailService.add(888L, MessengerType.TG, "emmebot@yandex.ru", "jjdjasjajja", "host.yandex.ru");
        emailService.add(888L, MessengerType.VK, "kkwwee@yandex.ru", "jjdjasjajja", "host.yandex.ru");

        //then
        var userVK = userRepository.findByChatIdAndType(888L, MessengerType.VK).get();
        assertAll(
                () -> assertEquals("emmebot@yandex.ru", userVK.getEmails().get(0).getAddress()),
                () -> assertEquals("jjdjasjajja", emailRepository.findByAddress("emmebot@yandex.ru").get().getPassword()),
                () -> assertEquals(2, userVK.getEmails().size()),
                () -> assertEquals(testEmail.getId(), userRepository.findByChatIdAndType(888L, MessengerType.TG).get().getEmails().get(0).getId()),
                () -> assertEquals(2, emailRepository.findAll().size()),
                () -> assertEquals(2, emailService.getAllSubscribedUsersForEmail("emmebot@yandex.ru").size())
        );
    }

    @Test
    @Transactional
    @Rollback
    void addSameEmailToOneUser(){
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "jjdjasjajja", "host.yandex.ru");
        try {
            emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "jjdjasjajja", "host.yandex.ru");
            fail();
        }
        catch (BadRequestEmemeException e){
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void removeEmailFromOneUser() {
        //given
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        var first = emailService.add(888L, MessengerType.TG, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");
        emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");

        //when
        var res = emailService.remove(888L, MessengerType.TG, "emmebot@yandex.ru");

        //then
        assertAll(
                () -> assertEquals(first, res),
                () -> assertEquals(1, userRepository.findByChatIdAndType(888L, MessengerType.VK).get().getEmails().size())
        );
    }

    @Test
    @Transactional
    @Rollback
    void removeEmailAfterAllUsersWillUnsubscribeFromIt(){
        //given
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        emailService.add(888L, MessengerType.TG, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");
        emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");

        //when
        emailService.remove(888L, MessengerType.TG, "emmebot@yandex.ru");
        var deletedEmail = emailService.remove(888L, MessengerType.VK, "emmebot@yandex.ru");

        //then
        assertAll(
                () -> assertEquals("emmebot@yandex.ru", deletedEmail.getEmail()),
                () -> assertTrue(emailRepository.findByAddress("emmebot@yandex.ru").isEmpty())
        );
    }

    @Test
    @Transactional
    @Rollback
    void deleteEmailWithUserDeletion(){
        //given
        userService.register(888L, MessengerType.VK);
        userService.register(888L, MessengerType.TG);
        emailService.add(888L, MessengerType.TG, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");
        emailService.add(888L, MessengerType.VK, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");

        //when
        userService.delete(888L, MessengerType.VK);

        //then
        assertAll(
                () -> assertEquals(1, userRepository.findByChatIdAndType(888L, MessengerType.TG).get().getEmails().size())
        );
    }

    @Test
    @Transactional
    @Rollback
    void patch() {
        //given
        userService.register(888L, MessengerType.TG);
        var email = emailService.add(888L, MessengerType.TG, "emmebot@yandex.ru", "dsjajdaj", "host.yandex.ru");
        email.setLastMessageTime(Timestamp.from(Instant.now()));
        email.setAppPassword("s3roowow211");
        email.setLastChecked(Timestamp.from(Instant.now()));

        //when
        emailService.patch(email);

        //then
        var updatedEmail = emailRepository.findById(email.getId());

        assertAll(
                () -> assertEquals(email.getAppPassword(), updatedEmail.get().getPassword()),
                () -> assertEquals(email.getLastChecked().toInstant().getEpochSecond(),
                        updatedEmail.get().getLastChecked().toInstant().getEpochSecond()),
                () -> assertEquals(email.getLastMessageTime().toInstant().getEpochSecond(),
                        updatedEmail.get().getLastUpdated().toInstant().getEpochSecond())
        );
    }

    @Test
    @Transactional
    @Rollback
    void emptyLinksListForUser(){
        //given
        userService.register(888L, MessengerType.TG);

        //when
        var res = emailService.getAllEmailsForChatId(888L, MessengerType.TG);

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void noEmailsGetLatest(){
        try {
            emailService.getLatestCheckedEmail();
            fail();
        } catch (BadRequestEmemeException e){
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void getLatestCheckedEmail(){
        userService.register(888L, MessengerType.TG);
        var latest = emailService.add(888L, MessengerType.TG, "dadad@emai.com", "sdads" ,"saddad");
        emailService.add(888L, MessengerType.TG, "akkakakak@emai.com", "sdads" ,"saddad");
        emailService.add(888L, MessengerType.TG, "wkeekwke@eee.com", "sdads" ,"saddad");

        var res = emailService.getLatestCheckedEmail();
        assertEquals(latest, res);
    }
}