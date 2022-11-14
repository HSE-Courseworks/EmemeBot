package ru.mamakapa.ememebot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class EmemeBotApplicationTests {

    @Test
    void contextLoads() throws UnsupportedEncodingException {
       System.out.println(MimeUtility.decodeText("=?utf-8?B?0KHRg9Cy0L7RgNC+0LLQsCDQkNC70ZHQvdCwINCS0LvQsNC00LjQvA==?= =?utf-8?B?0LjRgNC+0LLQvdCwICjQvtGC0L/RgNCw0LLQu9C10L3QviDRh9C10YDQtdC3?= =?utf-8?B?IGVkdS5oc2UucnUp?= <smartlms@hse.ru>"));
    }

}
