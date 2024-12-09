package com.github.lucasbandeira.libaryapi.dto;

import com.github.lucasbandeira.libaryapi.model.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRegisterDTO(
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        UUID authorId
) {
}
