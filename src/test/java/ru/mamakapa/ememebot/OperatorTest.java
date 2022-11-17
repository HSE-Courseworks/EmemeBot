package ru.mamakapa.ememebot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mamakapa.ememebot.service.sender.VkSender;

@SpringBootTest
public class OperatorTest {
    @Autowired
    private Operator operator;

    @Test
    public void runOperator(){
        operator.run();
    }
}
