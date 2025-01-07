package com.github.lucasbandeira.libaryapi.controller.dto;

import com.github.lucasbandeira.libaryapi.model.BookGender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        AuthorDTO author) {
}
