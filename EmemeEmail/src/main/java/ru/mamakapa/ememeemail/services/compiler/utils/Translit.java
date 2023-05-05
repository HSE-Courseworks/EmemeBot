package ru.mamakapa.ememeemail.services.compiler.utils;

public class Translit {
    private static String fromCyrillicToLatin(char ch){
        return switch (ch) {
            case 'А' -> "A";
            case 'Б' -> "B";
            case 'В' -> "V";
            case 'Г' -> "G";
            case 'Д' -> "D";
            case 'Е' -> "E";
            case 'Ё' -> "JE";
            case 'Ж' -> "ZH";
            case 'З' -> "Z";
            case 'И' -> "I";
            case 'Й' -> "Y";
            case 'К' -> "K";
            case 'Л' -> "L";
            case 'М' -> "M";
            case 'Н' -> "N";
            case 'О' -> "O";
            case 'П' -> "P";
            case 'Р' -> "R";
            case 'С' -> "S";
            case 'Т' -> "T";
            case 'У' -> "U";
            case 'Ф' -> "F";
            case 'Х' -> "KH";
            case 'Ц' -> "C";
            case 'Ч' -> "CH";
            case 'Ш' -> "SH";
            case 'Щ' -> "JSH";
            case 'Ъ' -> "HH";
            case 'Ы' -> "IH";
            case 'Ь' -> "JH";
            case 'Э' -> "EH";
            case 'Ю' -> "JU";
            case 'Я' -> "JA";
            default -> String.valueOf(ch);
        };
    }

    public static String cyrillicToLatin(String s){
        StringBuilder sb = new StringBuilder(s.length()*2);
        s = s.toUpperCase();
        for(char ch: s.toCharArray())
            sb.append(fromCyrillicToLatin(ch));
        return sb.toString();
    }
}
