package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.github.lucasbandeira.libaryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public Book save( Book book ) {
        return bookRepository.save(book);
    }

    public Optional <Book> getById( UUID id ) {
        return bookRepository.findById(id);
    }

    public void delete( Book book ) {
        bookRepository.delete(book);
    }

    public List <Book> search( String isbn, String title, String authorName, BookGender gender, Integer publicationYear ) {

        Specification <Book> specification = Specification.where(( root, query, criteriaBuilder ) -> criteriaBuilder.conjunction());

        if (isbn != null) {
            specification = specification.and(isbnEqual(isbn));
        }
        if (title != null) {
            specification = specification.and(titleLike(title));
        }
        if (gender != null) {
            specification = specification.and(genderEqual(gender));
        }
        if (publicationYear != null) {
            specification = specification.and(publicationYearEqual(publicationYear));
        }

        if (authorName != null) {
            specification = specification.and(authorNameLike(authorName));
        }

        return bookRepository.findAll(specification);
    }

    public void update( Book book ) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book does not exists in the database, " +
                    "save it, to update");
        }
        bookRepository.save(book);
    }
}
