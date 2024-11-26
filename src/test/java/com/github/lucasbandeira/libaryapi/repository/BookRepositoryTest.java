package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    void updateBookAuthorTest(){
        UUID id = UUID.fromString("a54a9613-a838-487c-92d1-dc1f0196fc1d");
        var bookToUpdate = bookRepository.findById(id)
                .orElse(null);

        UUID authorId = UUID.fromString("d79958d3-db05-45c2-9e66-c736cede74e9");
        Author author = authorRepository.findById(authorId).orElse(null);

        bookToUpdate.setAuthor(author);

        bookRepository.save(bookToUpdate);
    }

    @Test
    void deleteByIdTest(){
        UUID id = UUID.fromString("a54a9613-a838-487c-92d1-dc1f0196fc1d");
        bookRepository.deleteById(id);
    }

    @Test
    void deleteCascadeTest(){
        UUID id = UUID.fromString("ec527ce8-64fb-4f4e-abb7-f636630e8cdd");
        bookRepository.deleteById(id);
    }

    @Test
    @Transactional
    void findBookTest(){
        UUID bookId = UUID.fromString("76985ac4-996d-46a5-9b24-53f8b9474334");
        Book book = bookRepository.findById(bookId).orElse(null);
        System.out.println("Book: ");
        System.out.println(book.getTitle());
        System.out.println("Author: ");
        System.out.println(book.getAuthor().getName());
    }
}
