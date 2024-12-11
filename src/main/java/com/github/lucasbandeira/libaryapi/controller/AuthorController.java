package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.AuthorMapper;
import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity <Void> saveAuthor( @RequestBody @Valid AuthorDTO authorDTO ) {
        Author author = mapper.toEntity(authorDTO);
        authorService.save(author);
        URI location = generateHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <AuthorDTO> getDetails( @PathVariable String id ) {
        var authorId = UUID.fromString(id);

        return authorService
                .getById(authorId)
                .map(author -> ResponseEntity.ok(mapper.toDto(author))).orElseGet(() -> ResponseEntity.notFound().build());
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
        List <Author> result = authorService.searchByExample(name, nationality);
        List <AuthorDTO> authorList = result
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(authorList);

    }

    @PutMapping("{id}")
    public ResponseEntity <Void> update( @PathVariable String id, @Valid @RequestBody AuthorDTO dto ) {
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
    }
}
