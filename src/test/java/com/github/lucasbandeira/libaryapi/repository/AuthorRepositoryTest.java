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
import java.util.*;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;


    @Test
    public void saveTest() {
        Author author = new Author();

        author.setName("Lucas");
        author.setNationality("Brazilian");
        author.setBirthdate(LocalDate.of(2003, 02, 02));

        System.out.println(authorRepository.save(author));
    }

    @Test
    public void updateTest() {
        var id = UUID.fromString("0a144802-acda-4dab-ac36-a47242a30a2a");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setBirthdate(LocalDate.of(2000, 02, 02));
            authorRepository.save(author);
            System.out.println(author);
        }

    }

    @Test
    public void listTest() {
        authorRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Author count: " + authorRepository.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("0a144802-acda-4dab-ac36-a47242a30a2a");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            authorRepository.deleteById(authorOptional.get().getId());
        }
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("e4940b7d-907c-4878-be93-3cd6a424598d");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            authorRepository.delete(authorOptional.get());
        }
    }

    @Test
    void saveAuthorsWithBooksTest() {
        Author author = new Author();

        author.setName("Robert");
        author.setNationality("American");
        author.setBirthdate(LocalDate.of(1977, 11, 07));

        Book book = new Book();
        book.setIsbn("28298-514052");
        book.setPrice(BigDecimal.valueOf(222));
        book.setGender(BookGender.MYSTERY);
        book.setTitle("The Phantom Pain");
        book.setAuthor(author);

        book.setPublicationDate(LocalDate.of(2000,4,28));Book book2 = new Book();
        book2.setIsbn("82890-41552");
        book2.setPrice(BigDecimal.valueOf(250));
        book2.setGender(BookGender.MYSTERY);
        book2.setTitle("The Mystery of the Haunted House");
        book2.setPublicationDate(LocalDate.of(2015,2,25));
        book2.setAuthor(author);

        author.setBooks(new ArrayList <>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        authorRepository.save(author);
        //bookRepository.saveAll(author.getBooks());
    }
    @Test
    void listBooksAuthorTest(){
        UUID idAuthor = UUID.fromString("a2d8bb9f-f8d4-4a65-97b3-e6d606994e11");
        Author author = authorRepository.findById(idAuthor).get();

        List <Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);

        author.getBooks().forEach(System.out::println);

    }
}
