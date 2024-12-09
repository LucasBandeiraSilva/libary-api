package com.github.lucasbandeira.libaryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field!")
        @Size(message = "Field outside the standard size!", min = 2, max = 100)
        String name,
        @NotNull(message = "Required field!")
        @Past(message = "It Cannot be a future date!")
        LocalDate birthdate,
        @NotBlank(message = "Required field!")
        @Size(min = 2, max = 50, message = "Field outside the standard size!")
        String nationality) {
}