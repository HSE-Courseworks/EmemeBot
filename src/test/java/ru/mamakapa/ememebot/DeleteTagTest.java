package ru.mamakapa.ememebot;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.service.HtmlService;

import java.util.ArrayList;
import java.util.List;

public class DeleteTagTest {

    HtmlService htmlService = new HtmlService();

    String html = "<div>Текст текст текст <a href=\"http:\\hello.com\"\\> <img src=\"cid:5376171670011769@jjcwlhzhm4ck4unt.iva.yp-c.yandex.net\" style=\"max-width:100%\" /></div> ";

    @Test
    public void deleteTag(){
        String tag = "img,a";

        htmlService.deleteTag(html, tag);

        System.out.println(htmlService.deleteTag(html, tag).toString());
    }
}
