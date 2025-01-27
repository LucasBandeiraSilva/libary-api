package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.BookMapper;
import com.github.lucasbandeira.libaryapi.controller.dto.BookRegisterDTO;
import com.github.lucasbandeira.libaryapi.controller.dto.ResultSearchBookDTO;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import com.github.lucasbandeira.libaryapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "Save",description = "Register a new Book")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "successfully registered."),
            @ApiResponse(responseCode = "422",description = "Validation Error."),
            @ApiResponse(responseCode = "409",description = "Book already registered.")
    })
    public ResponseEntity <Void> save( @RequestBody @Valid BookRegisterDTO bookRegisterDTO ) {
        Book book = mapper.toEntity(bookRegisterDTO);
        bookService.save(book);
        URI location = generateHeaderLocation(book.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "get book details",description = "get details about one book")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Book found."),
            @ApiResponse(responseCode = "404",description = "Book not found.")
    })
    public ResponseEntity <ResultSearchBookDTO> getDetails( @PathVariable String id ) {
        var bookId = UUID.fromString(id);
        return bookService.getById(bookId).map(book -> ResponseEntity.ok(mapper.toDTO(book))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "delete",description = "delete a book by ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Successfully deleted book"),
            @ApiResponse(responseCode = "404",description = "Book not found.")
    })
    public ResponseEntity <Object> deleteBook( @PathVariable String id ) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "search",description = "book search with queries.")
    public ResponseEntity <Page <ResultSearchBookDTO>> search(
            @RequestParam(required = false)
            String isbn,
            @RequestParam(required = false)
            String title,
            @RequestParam(value = "author-name", required = false)
            String authorName,
            @RequestParam(required = false)
            BookGender gender,
            @RequestParam(value = "publication-year", required = false)
            Integer publicationYear,
            @RequestParam(value = "page",defaultValue = "0")
            Integer page,
            @RequestParam(value = "page-size",defaultValue = "10")
            Integer pageSize
    ) {

        Page<Book> pageResult = bookService.search(isbn, title, authorName, gender, publicationYear,page,pageSize);

        Page <ResultSearchBookDTO> result = pageResult.map(mapper::toDTO);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERATOR','MANAGER')")
    @Operation(summary = "update",description = "Update a book.")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Book successfully updated"),
            @ApiResponse(responseCode = "404",description = "Book not found"),
            @ApiResponse(responseCode = "409",description = "Book already registered.")
    })
    public ResponseEntity <Object> update( @PathVariable String id, @Valid @RequestBody BookRegisterDTO bookRegisterDTO ) {

        return  bookService.getById(UUID.fromString(id))
                .map(book -> {
            Book auxEntity = mapper.toEntity(bookRegisterDTO);

            book.setPublicationDate(auxEntity.getPublicationDate());
            book.setIsbn(auxEntity.getIsbn());
            book.setPrice(auxEntity.getPrice());
            book.setGender(auxEntity.getGender());
            book.setTitle(auxEntity.getTitle());
            book.setAuthor(book.getAuthor());

            bookService.update(book);
            return ResponseEntity.noContent().build();
        }).orElseGet(()->ResponseEntity.notFound().build());



    }
}
