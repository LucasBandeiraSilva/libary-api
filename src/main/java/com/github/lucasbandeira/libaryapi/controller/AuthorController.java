package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getDetails( @PathVariable String id ){
        var authorId = UUID.fromString(id);
        Optional <Author> authorOptional = authorService.getById(authorId);
        if (authorOptional.isPresent()){
            Author author = authorOptional.get();
            AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getName(), author.getBirthdate(), author.getNationality());
            return ResponseEntity.ok(authorDTO);
        }
        return ResponseEntity.notFound().build();
    }
}
