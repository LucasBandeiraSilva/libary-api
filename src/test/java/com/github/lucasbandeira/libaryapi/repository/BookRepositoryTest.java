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

        Author author = authorRepository
                .findById(UUID.fromString("d79958d3-db05-45c2-9e66-c736cede74e9"))
                .orElse(null);
        book.setAuthor(author);

        bookRepository.save(book);

    }

    @Test
    public void saveAuthorAndBookTest(){
        Book book = new Book();
        book.setIsbn("36745-62561");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICTION);
        book.setTitle("Third Book");
        book.setPublicationDate(LocalDate.of(2020,2,1));

        Author author = new Author();

        author.setName("Bruce");
        author.setNationality("Brazilian");
        author.setBirthdate(LocalDate.of(1977, 03, 01));

        authorRepository.save(author);

        book.setAuthor(author);


        bookRepository.save(book);

    }

    @Test
    public void saveCascadeTest(){
        Book book = new Book();
        book.setIsbn("36745-62561");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICTION);
        book.setTitle("Other Book");
        book.setPublicationDate(LocalDate.of(2020,2,1));

        Author author = new Author();

        author.setName("Steve");
        author.setNationality("Brazilian");
        author.setBirthdate(LocalDate.of(1977, 03, 01));
        book.setAuthor(author);

        bookRepository.save(book);

    }
}
