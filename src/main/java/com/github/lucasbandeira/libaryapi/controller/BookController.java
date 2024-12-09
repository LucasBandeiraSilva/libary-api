package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.dto.BookRegisterDTO;
import com.github.lucasbandeira.libaryapi.dto.ErrorResponse;
import com.github.lucasbandeira.libaryapi.exceprions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    @PostMapping
    public ResponseEntity <Object> save(@RequestBody @Valid BookRegisterDTO bookRegisterDTO ){
        try {
            return ResponseEntity.ok(bookRegisterDTO);
        } catch (DuplicateRegisterException e) {
            var dtoError = ErrorResponse.conflict(e.getMessage());
            return  ResponseEntity.status(dtoError.status()).body(dtoError);
        }
    }


}
