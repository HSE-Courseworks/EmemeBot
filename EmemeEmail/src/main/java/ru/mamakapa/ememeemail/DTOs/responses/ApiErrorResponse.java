package ru.mamakapa.ememeemail.DTOs.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record ApiErrorResponse(
        String message,
        List<String> stackTrace
) {
}
