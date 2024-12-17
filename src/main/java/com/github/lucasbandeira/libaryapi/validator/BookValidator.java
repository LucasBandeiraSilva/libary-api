package com.github.lucasbandeira.libaryapi.validator;

import com.github.lucasbandeira.libaryapi.exceprions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public void validate( Book book ) {
        if (isIsbnRegistered(book)) throw new DuplicateRegisterException("ISBN already registered!");
    }

    private boolean isIsbnRegistered( Book book ) {
        Optional <Book> bookOptional = bookRepository.findByIsbn(book.getIsbn());

        if (book.getId() == null) return bookOptional.isPresent();
        return bookOptional
                .map(Book::getId)
                .stream().
                anyMatch(id -> !id.equals(book.getId()));
    }
}
