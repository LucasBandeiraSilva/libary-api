package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveTest(){
        Book book = new Book();
        book.setIsbn("90445-85761");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICTION);
        book.setTitle("UFO");
        book.setPublicationDate(LocalDate.of(2020,2,1));

        Author author = authorRepository.findById(UUID.fromString("d79958d3-db05-45c2-9e66-c736cede74e9")).orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);


    }
}
