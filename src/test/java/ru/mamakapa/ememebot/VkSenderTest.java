package ru.mamakapa.ememebot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.sender.VkSender;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VkSenderTest {
    @Autowired
    private VkSender vkSender;
    @Test
    public void sendMessage() throws Exception{
        EmailLetter emailLetter1 = new EmailLetter();
        emailLetter1.setEnvelope("""
                Actual string:
                From:
                =?koi8-r?B?68/MxMnOwSDsyczJ0SD3wczF0tjF187B <lvkoldina@hse.ru>
                Reply to:
                =?koi8-r?B?68/MxMnOwSDsyczJ0SD3wczF0tjF187B <lvkoldina@hse.ru>
                To:
                "bse-22@ya.ru" <bse-22@ya.ru>
                "bse-21@ya.ru" <bse-21@ya.ru>
                "19pi-2.hse.nn@mail.ru" <19pi-2.hse.nn@mail.ru>
                "bse191@ya.ru" <bse191@ya.ru>
                SUBJECT: Физкультура
                Send date: Thu Nov 10 11:49:47 MSK 2022
                """);
        emailLetter1.setBodyPart("""
                Уважаемые студенты, добрый день!
                Напоминаю вам о зачете по физкультуре!
                Присылайте документы - справки/договоры, чеки!
                Все данные размещаются в таблице (ссылка ниже), если есть ошибки/неточности, пишите, все поправлю!
                                
                https://docs.google.com/spreadsheets/d/1j7M4EgbtUe_OlUCgGb-ppr_73F3So1NifGxFV5JgSQ4/edit?usp=sharing
                                
                Специалист по УМР 1 кат. ОП <Программная инженерия>
                Колдина Лилия Валерьевна.
                тел. 432-00-89, вн. 6403
                                
                                
                ________________________________
                                
                Это электронное сообщение и любые документы, приложенные к нему, содержат конфиденциальную информацию и предназначены исключительно для использования работниками НИУ ВШЭ, физическим или юридическим лицом, которому они адресованы. Уведомляем Вас о том, что, если это сообщение не предназначено Вам, использование, копирование, распространение информации, содержащейся в настоящем сообщении, а также осуществление любых действий на основе этой информации, не допускается. Если Вы считаете, что Вы получили это электронное сообщение по ошибке, пожалуйста, свяжитесь с отправителем и незамедлительно удалите электронное сообщение и любые вложения с компьютера. Заранее благодарим.
                                
                This e-mail and any attachments to it contain confidential information intended only for the use of the HSE University staff, the individual or entity who they are addressed to. We inform you that if you are not an intended recipient of this e-mail, the use, copying, distribution of the information contained in this message, as well as the conduction of any action based on this information is not allowed. If you believe that you have received this email in error, please contact the sender and immediately delete the email and any attachments from your computer. Thank you in advance.
                """);
        emailLetter1.setLinks(Arrays.asList("https://docs.google.com/spreadsheets/d/1j7M4EgbtUe_OlUCgGb-ppr_73F3So1NifGxFV5JgSQ4/edit?usp=sharing"));
        emailLetter1.setHtmlFilePaths(Arrays.asList("src/main/resources/uploadedFiles/message1.png"));

        EmailLetter emailLetter = new EmailLetter();
        emailLetter.setBodyPart("BodyPart");
        emailLetter.setEnvelope("envelope");
        emailLetter.setAttachmentFilePaths(Arrays.asList("src/main/resources/uploadedFiles/image.jpg"));
        emailLetter.setLinks(Arrays.asList("https://dev.vk.com/api/upload"));
        vkSender.setRecipientId(623783153);
        vkSender.setEmailLetter(emailLetter1);
        vkSender.sendMessage();
    }
}
