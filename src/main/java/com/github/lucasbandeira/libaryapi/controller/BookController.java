package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.mappers.BookMapper;
import com.github.lucasbandeira.libaryapi.dto.BookRegisterDTO;
import com.github.lucasbandeira.libaryapi.dto.ResultSearchBookDTO;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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
        return bookService.getById(bookId).map(book -> ResponseEntity.ok(mapper.toDTO(book))).orElseGet(()-> ResponseEntity.notFound().build());
    }

}
