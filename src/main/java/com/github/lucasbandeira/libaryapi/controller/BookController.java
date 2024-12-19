package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.BookMapper;
import com.github.lucasbandeira.libaryapi.dto.BookRegisterDTO;
import com.github.lucasbandeira.libaryapi.dto.ResultSearchBookDTO;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import com.github.lucasbandeira.libaryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper mapper;

    @PostMapping
    public ResponseEntity <Void> save( @RequestBody @Valid BookRegisterDTO bookRegisterDTO ) {
        Book book = mapper.toEntity(bookRegisterDTO);
        bookService.save(book);
        URI location = generateHeaderLocation(book.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <ResultSearchBookDTO> getDetails( @PathVariable String id ) {
        var bookId = UUID.fromString(id);
        return bookService.getById(bookId).map(book -> ResponseEntity.ok(mapper.toDTO(book))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Object> deleteBook( @PathVariable String id ) {
        return bookService.getById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
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
