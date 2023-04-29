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
import ru.mamakapa.ememeemail.services.BotUserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaBotUserServiceTest extends IntegrationEnvironment {

    @Autowired
    JpaBotUserService userService;

    @Autowired
    JpaBotUserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    void registerSomeUsers() {
        //when
        userService.register(888L, MessengerType.VK);
        userService.register(999L, MessengerType.TG);
        userService.register(888L, MessengerType.TG);

        //then
        assertAll(
                () -> assertEquals(3, userRepository.findAll().size()),
                () -> assertTrue(userRepository.findByChatIdAndType(888L, MessengerType.VK).isPresent()),
                () -> assertTrue(userRepository.findByChatIdAndType(888L, MessengerType.TG).isPresent()),
                () -> assertNotNull(userRepository.findByChatIdAndType(888L, MessengerType.VK).get().getEmails())
        );
        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @Transactional
    @Rollback
    void registerSameUserAndGetException(){
        //given
        userService.register(999L, MessengerType.TG);

        try {
            //when
            userService.register(999L, MessengerType.TG);
            fail();
        }catch (BadRequestEmemeException e){
            //then
            assertTrue(true);
        }
    }

    @Test
    @Transactional
    @Rollback
    void delete() {
        //given
        userService.register(888L, MessengerType.VK);

        //when
        userService.delete(888L, MessengerType.VK);

        //then
        var users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void deleteNotRegisteredUsers(){
        //given
        userService.register(888L, MessengerType.VK);

        //when
        try {
            userService.delete(888L, MessengerType.TG);
            fail();
        } catch (Exception e){
            //then
            assertTrue(true);
        }

        try {
            userService.delete(111L, MessengerType.VK);
            fail();
        } catch (Exception e){
            //then
            assertTrue(true);
        }
    }
}