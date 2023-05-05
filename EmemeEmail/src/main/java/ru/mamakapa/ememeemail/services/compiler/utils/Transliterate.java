package ru.mamakapa.ememeemail.services.compiler.utils;

import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class Transliterate {
    private static final Map<Character, String> CHAR_MAP = ofEntries(
            entry('А', "A"),
            entry('Б', "B"),
            entry('В', "V"),
            entry('Г', "G"),
            entry('Д', "D"),
            entry('Е', "E"),
            entry('Ё', "JE"),
            entry('Ж', "ZH"),
            entry('З', "Z"),
            entry('И', "I"),
            entry('Й', "Y"),
            entry('К', "K"),
            entry('Л', "L"),
            entry('М', "M"),
            entry('Н', "N"),
            entry('О', "O"),
            entry('П', "P"),
            entry('Р', "R"),
            entry('С', "S"),
            entry('Т', "T"),
            entry('У', "U"),
            entry('Ф', "F"),
            entry('Х', "KH"),
            entry('Ц', "C"),
            entry('Ч', "CH"),
            entry('Ш', "SH"),
            entry('Щ', "JSH"),
            entry('Ъ', "HH"),
            entry('Ы', "IH"),
            entry('Ь', "JH"),
            entry('Э', "EH"),
            entry('Ю', "JU"),
            entry('Я', "JA")
    );

    public static String cyrillicToLatin(String str) {
        StringBuilder sb = new StringBuilder(str.length() * 2);
        for (char symbol : str.toUpperCase().toCharArray()) {
            sb.append(CHAR_MAP.getOrDefault(symbol, String.valueOf(symbol)));
        }
        return sb.toString();
    }
}
