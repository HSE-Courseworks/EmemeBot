package ru.mamakapa.ememeemail.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DeleteEmailRequest(@NotBlank @Email String address) {
}
