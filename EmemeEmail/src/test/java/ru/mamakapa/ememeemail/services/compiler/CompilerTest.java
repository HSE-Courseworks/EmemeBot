package ru.mamakapa.ememeemail.services.compiler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememeemail.IntegrationEnvironment;
import ru.mamakapa.ememeemail.configurations.ApplicationConfig;
import ru.mamakapa.ememeemail.entities.ImapEmail;
import ru.mamakapa.ememeemail.services.connection.EmailConnection;

import javax.mail.MessagingException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompilerTest extends IntegrationEnvironment {
    @Autowired
    EmailConnection connection;
    @Autowired
    ApplicationConfig applicationConfig;

    Path path = Paths.get("src/main/resources/savedir").normalize().toAbsolutePath();

    Compiler compiler = new CompilerImpl(path);

    @Test
    public void compile() throws MessagingException, IOException {
        //given
        var email = ImapEmail.builder()
                .email(applicationConfig.test().email())
                .appPassword(applicationConfig.test().password())
                .host(applicationConfig.test().host())
                .botId(888L)
                .id(13L)
                .build();

        connection.connect(email);
        var message = connection.getMessageByNumber(email, 4);

        //when
        var res = compiler.compile(message.get());

        //then
        System.out.println(res);
        assertAll(
                () -> assertEquals("""
                        От: Ememe Bot <ememebot@yandex.ru>\s
                        Тема: 4th Test
                        Отправленно: Thu Jan 26 17:34:00 MSK 2023
                        """,
                        res.getEnvelope()),
                () -> assertEquals("""
                        Ссылки из сообщения:
                        Вложение: LAB01-NETINTRO.ZIP
                        Вложение: RASPISANIE_2022-2023_-_IMIKN_-_3_MODULJH_-_BAK_-_PI.XLSX                                
                        """,
                        res.getBodyPart()),
                () -> assertEquals(3, res.getFiles().size())
        );

        compiler.deleteLetterFiles(res);
    }
}