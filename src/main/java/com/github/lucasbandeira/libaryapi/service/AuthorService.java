package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService( AuthorRepository authorRepository ) {
        this.authorRepository = authorRepository;
    }

    public Author save(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> getById( UUID id){
        return authorRepository.findById(id);
    }

    public void delete( Author author ) {
        authorRepository.delete(author); ;
    }

    public List<Author>search(String name, String nationality){
        if (name != null && nationality != null)  return authorRepository.findByNameAndNationality(name,nationality);
        if (name != null) return  authorRepository.findByName(name);
        if (nationality != null) return  authorRepository.findByNationality(nationality);
        return authorRepository.findAll();
    }

    public void update(Author author){
        if (author.getId() == null) throw new IllegalArgumentException("Author does not exists in the database, " +
                "save it, to update");
        authorRepository.save(author);
    }

}
