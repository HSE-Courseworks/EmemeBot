package ru.mamakapa.vkbot.bot;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class VkBotTest {
    @Test
    void getTextPartsMethodTest() throws Exception {
        Class vkClass = VkBot.class;
        Method getTextParts = vkClass.getDeclaredMethod("getTextParts", String.class);
        getTextParts.setAccessible(true);
        Field constantField = vkClass.getDeclaredField("MAX_COUNT_WORDS_IN_MESSAGE");
        constantField.setAccessible(true);
        final int MAX_COUNT_WORDS = constantField.getInt(null);
        StringBuilder aMessageBuilder = new StringBuilder();
        IntStream.range(0, MAX_COUNT_WORDS)
                .forEach(i -> aMessageBuilder.append("a"));
        StringBuilder bMessageBuilder = new StringBuilder();
        final int someSize = 100;
        IntStream.range(0, someSize)
                .forEach(i -> bMessageBuilder.append("b"));
        String aMessage = aMessageBuilder.toString();
        String bMessage = bMessageBuilder.toString();
        String fullMessage = aMessage + bMessage;
        Stream<String> parts = (Stream<String>) getTextParts.invoke(null, fullMessage);
        List<String> textParts = parts.toList();
        assertArrayEquals(List.of(aMessage, bMessage).toArray(), textParts.toArray());
    }
}