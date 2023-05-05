package ru.mamakapa.ememeemail.services.compiler.utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.MimeUtility;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MimeDecoder {
    public static String decodeMIMEB(String line) {
        try {
            String decodedLine = MimeUtility.decodeText(line);
            if (!decodedLine.equals(line)) return decodedLine;

            Pattern encoding = Pattern.compile("\\=\\?.*?\\?\\w\\?");
            Matcher matchEncods = encoding.matcher(line.toUpperCase());
            List<String> encodings = new ArrayList<>();
            while (matchEncods.find()) {
                encodings.add(line.substring(matchEncods.start(),
                        matchEncods.end()).replaceAll(".*\\=\\?|\\?\\w\\?.*", ""));
            }

            Pattern inf = Pattern.compile("\\=\\?.*?\\=");
            Matcher matchEncInf = inf.matcher(line);
            List<String> passages = new ArrayList<>();
            while (matchEncInf.find()) {
                passages.add(line.substring(matchEncInf.start(),
                        matchEncInf.end()).replaceAll("\\=\\?.*?\\?\\w\\?", ""));
            }

            String result = "";

            for (int i = 0; i < Math.min(encodings.size(), passages.size()); ++i) {
                String passage = passages.get(i);
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decocdedBytes = decoder.decode(passage);
                result += new String(decocdedBytes, encodings.get(i)) + " ";
            }
            result += line.replaceAll("\\=\\?.*?\\=", "");
            return result;
        } catch (Exception e){
            log.info("Decoder Exception:" + e.getMessage() + "\n\nReturning basic line");
            return line;
        }
    }
}
