package ru.mamakapa.ememebot.service.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class EmailService {
    final private EmailCompiler emailCompiler;
    final private EmailConnection emailConnection;

    public EmailService(EmailCompiler emailCompiler, EmailConnection emailConnection) {
        this.emailCompiler = emailCompiler;
        this.emailConnection = emailConnection;
    }
}
