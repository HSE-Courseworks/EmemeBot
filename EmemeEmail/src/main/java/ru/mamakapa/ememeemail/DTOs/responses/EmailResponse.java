package ru.mamakapa.ememeemail.DTOs.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailResponse(@NotBlank @Email String address, @NotBlank String appPassword, @NotBlank String host) {
}
