package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.AuthorMapper;
import com.github.lucasbandeira.libaryapi.controller.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.security.SecurityService;
import com.github.lucasbandeira.libaryapi.service.AuthorService;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Tag(name = "Authors")
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final SecurityService securityService;
    private final AuthorMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Save", description = "Register a new Author")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "successfully registered."),
            @ApiResponse(responseCode = "422", description = "Validation Error."),
            @ApiResponse(responseCode = "409", description = "Author already Registered.")
    })
    public ResponseEntity <Void> saveAuthor( @RequestBody @Valid AuthorDTO authorDTO ) {

        Author author = mapper.toEntity(authorDTO);
        authorService.save(author);
        URI location = generateHeaderLocation(author.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "Get details", description = "get details about an author by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found."),
            @ApiResponse(responseCode = "404", description = "Author not found.")
    })
    public ResponseEntity <AuthorDTO> getDetails( @PathVariable String id ) {
        var authorId = UUID.fromString(id);

        return authorService
                .getById(authorId)
                .map(author -> ResponseEntity.ok(mapper.toDto(author))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "Delete", description = "Delete an existing author By ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Author found."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "400", description = "It is not possible to exclude an author with registered books.")
    })
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
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "search", description = "performs an author search by parameters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success."),
    })
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
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "update", description = "Update an Author.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Author updated successfully."),
            @ApiResponse(responseCode = "404", description = "Author not found."),
            @ApiResponse(responseCode = "409", description = "Author already Registered.")
    })
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
