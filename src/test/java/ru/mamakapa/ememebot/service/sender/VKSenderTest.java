package ru.mamakapa.ememebot.service.sender;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.service.email.AbstractEmailCompiler;
import ru.mamakapa.ememebot.service.email.EmailLetter;
import ru.mamakapa.ememebot.service.email.YandexEmailCompiler;
import ru.mamakapa.ememebot.service.sender.exceptions.SendMessageException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SpringBootTest()
public class VKSenderTest {
    @Autowired
    VkSender vkSender;
    @Test
    public void sendFragmentedLetter() throws SendMessageException {
        EmailLetter emailLetter = new EmailLetter();
        emailLetter.setEnvelope("""
                От: Жигунова Светлана Игоревна <szhigunova@hse.ru>
                Дата: 9 декабря 2022 г. в 16:51:13 GMT+3
                Кому: Терешина Наталья Петровна <ntereshina@hse.ru>, Шикина Дарья Дмитриевна <dshikina@hse.ru>, Гришакова Марина Николаевна <mgrishakova@hse.ru>, Коровина Наталья Михайловна <nkorovina@hse.ru>, Емельянова Мария Максимовна <memelyanova@hse.ru>, Мулихова Наталья Михайловна <nmulihova@hse.ru>, Ковалева Алина Валентиновна <avkovaleva@hse.ru>, Чуваткина Светлана Александровна <schuvatkina@hse.ru>, Белохлебова Наталья Александровна <nbelokhlebova@hse.ru>, Гапонова Надежда Сергеевна <ngaponova@hse.ru>, Анисимова Ульяна Владимировна <uslepova@hse.ru>, Шабанова Жанна Рубеновна <zhshabanova@hse.ru>, Ермохина Алена Евгеньевна <aermokhina@hse.ru>, Соколова Анна Юрьевна <sokolovaa@hse.ru>, Котова Мария Алексеевна <makotova@hse.ru>, Лапшина Наталья Борисовна <nblapshina@hse.ru>, Шульгина Нурила Калыбековна <nshulgina@hse.ru>, Ляшенко Татьяна Анатольевна <tlyashenko@hse.ru>, Жукова Валерия Валерьевна <vzhukova@hse.ru>, Патенкова Екатерина Николаевна <epatenkova@hse.ru>, Маслова Дарья Александровна <dmaslova@hse.ru>
                Копия: Лошкарева Мария Евгеньевна <mloshkareva@hse.ru>, Шлягина Евгения Николаевна <eryabinina@hse.ru>, Серова Екатерина Евгеньевна <eserova@hse.ru>, Дмитриева Ольга Сергеевна <odmitrieva@hse.ru>, Бурматников Алексей Сергеевич <aburmatnikov@hse.ru>, Осипова Анастасия Ивановна <aiosipova@hse.ru>, Шутова Нина Васильевна <nshutova@hse.ru>, Вершинина Ольга Вячеславовна <overshinina@hse.ru>, Трехлеб Ольга Юрьевна <otrehleb@hse.ru>, Конаваленак Ирина Александровна <ikonavalenak@hse.ru>, Попова Екатерина Петровна <ekpopova@hse.ru>, Берус Яна Владимировна <yberus@hse.ru>, Креховец Екатерина Владимировна <ekrekhovets@hse.ru>, Шушкин Михаил Александрович <mshushkin@hse.ru>, Савинова Светлана Юрьевна <ssavinova@hse.ru>, Мазова Ольга Николаевна <omazova@hse.ru>, Егорова Екатерина Игоревна <egorovaei@hse.ru>, Коренькова Марианна Максимовна <mkorenkova@hse.ru>, Банников Константин Валерьевич <kbannikov@hse.ru>, Тимошинов Павел Викторович <ptimoshinov@hse.ru>, Кузнецова Елена Викторовна <evkuzneczova@hse.ru>, Чебочко Наталья Георгиевна <nchebochko@hse.ru>, Климова Маргарита Андреевна <mfokina@hse.ru>, Гельфонд Мария Марковна <mgelfond@hse.ru>
                Тема: Расписание сессии 2 модуля
                """);
                emailLetter.setBodyPart("""
                Студенты, добрый день.
                                
                Во вложении расписание сессии 2 модуля 2022-2023.
                                
                С уважением,\s
                Емельянова Мария Максимовна
                Начальник ОСУП ОП ПИ факультета ИМиКН
                НИУ ВШЭ - Нижний Новгород
                                
                Начало переадресованного сообщения:
                                
                ﻿
                Уважаемые коллеги, здравствуйте!
                                
                Высылаем вам расписания промежуточного и итогового контроля 2 модуля!
                                
                \s
                                
                Просим довести информацию преподавателям и студентам.
                                
                \s
                                
                \s
                                
                \s
                                
                С уважением,
                                
                Светлана Жигунова
                                
                Учебный отдел
                                
                НИУ ВШЭ-Нижний Новгород
                                
                278-09-33, вн.6337
                                
                \s
                                
                                
                Это электронное сообщение и любые документы, приложенные к нему, содержат конфиденциальную информацию и предназначены исключительно для использования работниками НИУ ВШЭ, физическим или юридическим лицом, которому они адресованы. Уведомляем Вас о том, что, если это сообщение не предназначено Вам, использование, копирование, распространение информации, содержащейся в настоящем сообщении, а также осуществление любых действий на основе этой информации, не допускается. Если Вы считаете, что Вы получили это электронное сообщение по ошибке, пожалуйста, свяжитесь с отправителем и незамедлительно удалите электронное сообщение и любые вложения с компьютера. Заранее благодарим.
                                
                This e-mail and any attachments to it contain confidential information intended only for the use of the HSE University staff, the individual or entity who they are addressed to. We inform you that if you are not an intended recipient of this e-mail, the use, copying, distribution of the information contained in this message, as well as the conduction of any action based on this information is not allowed. If you believe that you have received this email in error, please contact the sender and immediately delete the email and any attachments from your computer. Thank you in advance.
                                
                                
                Это электронное сообщение и любые документы, приложенные к нему, содержат конфиденциальную информацию и предназначены исключительно для использования работниками НИУ ВШЭ, физическим или юридическим лицом, которому они адресованы. Уведомляем Вас о том, что, если это сообщение не предназначено Вам, использование, копирование, распространение информации, содержащейся в настоящем сообщении, а также осуществление любых действий на основе этой информации, не допускается. Если Вы считаете, что Вы получили это электронное сообщение по ошибке, пожалуйста, свяжитесь с отправителем и незамедлительно удалите электронное сообщение и любые вложения с компьютера. Заранее благодарим.
                                
                This e-mail and any attachments to it contain confidential information intended only for the use of the HSE University staff, the individual or entity who they are addressed to. We inform you that if you are not an intended recipient of this e-mail, the use, copying, distribution of the information contained in this message, as well as the conduction of any action based on this information is not allowed. If you believe that you have received this email in error, please contact the sender and immediately delete the email and any attachments from your computer. Thank you in advance.""");
        emailLetter.setHtmlFilePaths(List.of());
        emailLetter.setAttachmentFilePaths(List.of(System.getProperty("user.dir") + "\\src\\main\\resources\\" + "attachments\\"+"SESSIY IMIKN.PDF"));
        vkSender.sendMessage(emailLetter, VkSender.GROUP_PEER_ID+2);
    }
}
