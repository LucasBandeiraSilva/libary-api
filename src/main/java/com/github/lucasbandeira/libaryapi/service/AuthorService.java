package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.exceptions.OperationNotAllowedException;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import com.github.lucasbandeira.libaryapi.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;


    public Author save( Author author ) {
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public Optional <Author> getById( UUID id ) {
        return authorRepository.findById(id);
    }

    public void delete( Author author ) {
        if (hasBook(author)) throw new OperationNotAllowedException("It is not allowed exclude Authors " +
                "who already has books registered!");

        authorRepository.delete(author);
    }

    public List <Author> search( String name, String nationality ) {
        if (name != null && nationality != null) return authorRepository.findByNameAndNationality(name, nationality);
        if (name != null) return authorRepository.findByName(name);
        if (nationality != null) return authorRepository.findByNationality(nationality);
        return authorRepository.findAll();
    }

    public List <Author> searchByExample( String name, String nationality ) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "birthdate")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example <Author> authorExample = Example.of(author, matcher);
        return authorRepository.findAll(authorExample);
    }

    public void update( Author author ) {
        if (author.getId() == null) throw new IllegalArgumentException("Author does not exists in the database, " +
                "save it, to update");

        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public boolean hasBook( Author author ) {
        return bookRepository.existsByAuthor(author);
    }

}
