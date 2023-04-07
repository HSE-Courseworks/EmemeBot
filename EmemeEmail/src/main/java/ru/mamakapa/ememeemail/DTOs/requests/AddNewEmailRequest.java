package ru.mamakapa.ememeemail.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AddNewEmailRequest(@NotBlank @Email String address, @NotBlank String appPassword, @NotBlank String host) {
}
