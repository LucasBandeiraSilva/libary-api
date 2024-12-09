package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.AuthorMapper;
import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.dto.ErrorResponse;
import com.github.lucasbandeira.libaryapi.exceprions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.exceprions.OperationNotAllowedException;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity <Object> saveAuthor( @RequestBody @Valid AuthorDTO authorDTO ) {
        try {
            Author author = mapper.toEntity(authorDTO);
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
//        Optional <Author> authorOptional = authorService.getById(authorId);
//        authorOptional.ifPresent(author -> System.out.println("Author birthdate: " + author.getBirthdate()));


        return  authorService
                .getById(authorId)
                .map(author -> ResponseEntity.ok(mapper.toDto(author))).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Object> delete( @PathVariable String id ) {
        try {
            var authorId = UUID.fromString(id);
            Optional <Author> authorOptional = authorService.getById(authorId);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.delete(authorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotAllowedException e) {
            var dtoError = ErrorResponse.standardResponse(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }

    @GetMapping
    public ResponseEntity <List <AuthorDTO>> search( @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "nationality", required = false) String nationality ) {
        List <Author> result = authorService.searchByExample(name, nationality);
        List <AuthorDTO> authorList = result
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorList);

    }

    @PutMapping("{id}")
    public ResponseEntity <Object> update( @PathVariable String id, @Valid @RequestBody AuthorDTO dto ) {
        try {
            var authorId = UUID.fromString(id);
            Optional <Author> authorOptional = authorService.getById(authorId);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            var author = authorOptional.get();
            author.setName(dto.name());
            author.setBirthdate(dto.birthdate());
            author.setNationality(dto.nationality());

            authorService.update(author);
            return ResponseEntity.noContent().build();
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }
}
