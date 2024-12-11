package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public Book save( Book book ) {
        return bookRepository.save(book);
    }

    public Optional<Book>getById( UUID id ){
        return bookRepository.findById(id);
    }
}
