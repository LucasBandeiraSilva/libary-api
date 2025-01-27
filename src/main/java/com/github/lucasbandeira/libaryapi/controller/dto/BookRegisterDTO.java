package com.github.lucasbandeira.libaryapi.controller.dto;

import com.github.lucasbandeira.libaryapi.model.BookGender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Book")
public record BookRegisterDTO(
        @ISBN(message = "Please provide a valid ISBN!")
        @NotBlank(message = "Required field!")
        String isbn,
        @NotBlank(message = "Required field!")
        String title,
        @NotNull(message = "Required field!")
        @Past(message = "It Cannot be a future date!")
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        @NotNull(message = "Required field!")
        UUID authorId
) {
}
