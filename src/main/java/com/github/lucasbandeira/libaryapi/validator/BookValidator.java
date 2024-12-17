package com.github.lucasbandeira.libaryapi.validator;

import com.github.lucasbandeira.libaryapi.exceptions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.exceptions.InvalidCampException;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int YEAR_REQUIREMENT_PRICE = 2020;
    private final BookRepository bookRepository;

    public void validate( Book book ) {
        if (isIsbnRegistered(book)) throw new DuplicateRegisterException("ISBN already registered!");
        if (isRequiredPriceNull(book))
            throw new InvalidCampException("price", "For books with publication date greater than 2020, the pricing is mandatory!");
    }

    private boolean isRequiredPriceNull( Book book ) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= YEAR_REQUIREMENT_PRICE;
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
