package ru.mamakapa.ememeemail.services.connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.mamakapa.ememeemail.configurations.ApplicationConfig;
import ru.mamakapa.ememeemail.entities.ImapEmail;

import javax.mail.MessagingException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmailConnectionTest {

    @SpyBean
    EmailConnection emailConnection;

    @Autowired
    ApplicationConfig applicationConfig;

    ImapEmail email = ImapEmail.builder()
            .email(applicationConfig.test().email())
            .appPassword(applicationConfig.test().password())
            .host(applicationConfig.test().host())
            .botId(888L)
            .id(13L)
            .build();

    @BeforeEach
    public void connectToEmail() throws MessagingException {
        emailConnection.connect(email);
    }

    @AfterEach
    public void closeConnectionToEmail() throws MessagingException {
        emailConnection.closeConnection(email);
    }

    @Test
    void canConnect() throws MessagingException {
        assertTrue(emailConnection.isConnected(email));
    }

    @Test
    void getNewLetters() throws ParseException, MessagingException {
        //given
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse("23/09/2007");
        email.setLastMessageTime(new Timestamp(date.getTime()));

        //when
        var res = emailConnection.getNewLetters(email);

        //then
        res.ifPresentOrElse(
                messages -> assertEquals(EmailConnection.LAST_MESSAGES_TO_CHECK_COUNT, messages.size()),
                () -> fail()
        );
    }

    @Test
    void getLastMessagesByCount() throws MessagingException {
        //given
        int count = 5;

        //when
        var res = emailConnection.getLastMessagesByCount(email, count);

        //then
        res.ifPresentOrElse(
                (messages) -> assertEquals(count, messages.size()),
                () -> fail()
        );
    }

    @Test
    void getMessagesWithOpeningFolderMultipleTimes() throws MessagingException {
        //when
        var mes1 = emailConnection.getMessageByNumber(email, 1);
        var mes2 = emailConnection.getMessageByNumber(email, 2);
        var mes3 = emailConnection.getMessageByNumber(email, 3);

        //then
        assertTrue(mes1.isPresent());
        assertTrue(mes2.isPresent());
        assertTrue(mes3.isPresent());
    }
}