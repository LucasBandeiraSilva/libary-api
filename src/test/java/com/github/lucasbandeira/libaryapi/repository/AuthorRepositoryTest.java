package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;


    @Test
    public void saveTest() {
        Author author = new Author();

        author.setName("Lucas");
        author.setNationality("Brazilian");
        author.setBirthdate(LocalDate.of(2003, 02, 02));

        System.out.println(authorRepository.save(author));
    }

    @Test
    public void updateTest(){
        var id = UUID.fromString("0a144802-acda-4dab-ac36-a47242a30a2a");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            author.setBirthdate(LocalDate.of(2000,02,02));
            authorRepository.save(author);
            System.out.println(author);
        }

    }
    @Test
    public void listTest(){
        authorRepository.findAll().forEach(System.out::println);
    }
    @Test
    public void countTest(){
        System.out.println("Author count: " + authorRepository.count());
    }

    @Test
    public void deleteByIdTest(){
        var id = UUID.fromString("0a144802-acda-4dab-ac36-a47242a30a2a");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()){
            authorRepository.deleteById(authorOptional.get().getId());
        }
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("e4940b7d-907c-4878-be93-3cd6a424598d");
        Optional <Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()){
            authorRepository.delete(authorOptional.get());
        }
    }
}
