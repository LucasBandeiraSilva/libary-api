package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController( AuthorService authorService ) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthor( @RequestBody AuthorDTO authorDTO ){
        Author author = authorDTO.toAuthor();
        authorService.save(author);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
