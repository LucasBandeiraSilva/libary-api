package com.github.lucasbandeira.libaryapi.validator;

import com.github.lucasbandeira.libaryapi.exceptions.DuplicateRegisterException;
import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator( AuthorRepository authorRepository ) {
        this.authorRepository = authorRepository;
    }

    public void validate( Author author ){
        if (isAuthorRegistered(author)) throw  new DuplicateRegisterException("Author already registered!");
    }

    private boolean isAuthorRegistered(Author author){
        Optional<Author>authorOptional = authorRepository.
                findByNameAndBirthdateAndNationality(author.getName(),
                        author.getBirthdate(),
                        author.getNationality());

        if (author.getId() == null) {
            return authorOptional.isPresent();
        }
        return !author.getId().equals(authorOptional.get().getId()) && authorOptional.isPresent();
    }
}
