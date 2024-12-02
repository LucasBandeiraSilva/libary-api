package com.github.lucasbandeira.libaryapi.dto;

import com.github.lucasbandeira.libaryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(String name, LocalDate birthDate, String nationality) {


    public Author toAuthor(){
        Author author = new Author();
        author.setName(this.name);
        author.setBirthdate(this.birthDate);
        author.setNationality(this.nationality);
        return  author;
    }
}