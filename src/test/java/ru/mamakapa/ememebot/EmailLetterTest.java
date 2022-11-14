package ru.mamakapa.ememebot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.mamakapa.ememebot.service.email.EmailLetter;

import java.util.ArrayList;
import java.util.List;

public class EmailLetterTest {

    @Test
    public void settersGettersTest(){
        EmailLetter emailLetter = EmailLetter.builder().
                attachmentFilePaths(new ArrayList<>()).
                build();

        emailLetter.getAttachmentFilePaths().add("bebe");
        emailLetter.getAttachmentFilePaths().add("bubu");

        List<String> expected = new ArrayList<>();
        expected.add("bebe"); expected.add("bubu");

        Assert.assertEquals(emailLetter.getAttachmentFilePaths(), expected);
    }
}
