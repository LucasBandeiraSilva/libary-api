package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.repository.BookRepository;
import com.github.lucasbandeira.libaryapi.security.SecurityService;
import com.github.lucasbandeira.libaryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final BookValidator validator;
    private final SecurityService securityService;


    public Book save( Book book ) {
        validator.validate(book);
        Username username = securityService.getLoggedUser();
        book.setUsername(username);
        return bookRepository.save(book);
    }

    public Optional <Book> getById( UUID id ) {
        return bookRepository.findById(id);
    }

    public void delete( Book book ) {
        bookRepository.delete(book);
    }

    public Page <Book> search(
            String isbn,
            String title,
            String authorName,
            BookGender gender,
            Integer publicationYear,
            Integer page,
            Integer pageSize) {

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

        Pageable pageable = PageRequest.of(page,pageSize);
        return bookRepository.findAll(specification,pageable);
    }

    public void update( Book book ) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book does not exists in the database, " +
                    "save it, to update");
        }
        bookRepository.save(book);
    }
}
