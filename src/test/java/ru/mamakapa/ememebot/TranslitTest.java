package ru.mamakapa.ememebot;

import org.junit.jupiter.api.Test;
import ru.mamakapa.ememebot.service.Translit;

public class TranslitTest {
    @Test
    void test(){
        System.out.println(Translit.cyrillicToLatin("Контесты_Алгосы.txt"));
        System.out.println(Translit.cyrillicToLatin("Курицын Никита 21Пи2.pdf"));
    }
}
