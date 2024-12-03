package com.github.lucasbandeira.libaryapi.dto;

import com.github.lucasbandeira.libaryapi.model.Author;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(UUID id, String name, LocalDate birthDate, String nationality) {


    public Author toAuthor(){
        Author author = new Author();
        author.setName(this.name);
        author.setBirthdate(this.birthDate);
        author.setNationality(this.nationality);
        return  author;
    }
}