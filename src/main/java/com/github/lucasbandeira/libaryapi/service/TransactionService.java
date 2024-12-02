package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void execute() {

        Author author = new Author();
        author.setName("Test John");
        author.setNationality("Brazilian");
        author.setBirthdate(LocalDate.of(1977, 03, 01));

        authorRepository.save(author);

        Book book = new Book();
        book.setIsbn("36745-62561");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICTION);
        book.setTitle("The green alien");
        book.setPublicationDate(LocalDate.of(2020, 2, 1));

        book.setAuthor(author);

        bookRepository.save(book);

        if (author.getName().equals("Test John")){
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    public void updateWithoutUpdating(){
        var book = bookRepository.
                findById(UUID.fromString("76985ac4-996d-46a5-9b24-53f8b9474334"))
                .orElse(null);

        book.setPublicationDate(LocalDate.of(2024,11,7));

    }
}
