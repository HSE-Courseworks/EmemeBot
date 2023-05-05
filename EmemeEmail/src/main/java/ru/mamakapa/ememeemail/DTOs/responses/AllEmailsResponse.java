package ru.mamakapa.ememeemail.DTOs.responses;

import java.util.List;

public record AllEmailsResponse(List<EmailResponse> emails) {
}
