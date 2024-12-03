package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService( AuthorRepository authorRepository ) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> getById( UUID id){
        return authorRepository.findById(id);
    }
}
