package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.dto.ErrorResponse;
import com.github.lucasbandeira.libaryapi.exceprions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController( AuthorService authorService ) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity <Object> saveAuthor( @RequestBody AuthorDTO authorDTO ) {
        try {
            Author author = authorDTO.toAuthor();
            authorService.save(author);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity <AuthorDTO> getDetails( @PathVariable String id ) {
        var authorId = UUID.fromString(id);
        Optional <Author> authorOptional = authorService.getById(authorId);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO authorDTO = new AuthorDTO(author.getId(), author.getName(), author.getBirthdate(), author.getNationality());
            return ResponseEntity.ok(authorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> delete( @PathVariable String id ) {
        var authorId = UUID.fromString(id);
        Optional <Author> authorOptional = authorService.getById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity <List <AuthorDTO>> search( @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "nationality", required = false) String nationality ) {
        List <Author> result = authorService.search(name, nationality);
        List <AuthorDTO> authorList = result
                .stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthdate(),
                        author.getNationality()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorList);

    }

    @PutMapping("{id}")
    public ResponseEntity <Object> update( @PathVariable String id, @RequestBody AuthorDTO dto ) {
        try {
            var authorId = UUID.fromString(id);
            Optional <Author> authorOptional = authorService.getById(authorId);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var author = authorOptional.get();
            author.setName(dto.name());
            author.setBirthdate(dto.birthDate());
            author.setNationality(dto.nationality());

            authorService.update(author);
            return ResponseEntity.noContent().build();
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }
}
