package com.github.lucasbandeira.libaryapi.controller.dto;

import com.github.lucasbandeira.libaryapi.model.BookGender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Result Search")
public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate publicationDate,
        BookGender gender,
        BigDecimal price,
        AuthorDTO author) {
}
