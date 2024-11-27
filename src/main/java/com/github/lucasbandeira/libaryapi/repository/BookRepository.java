package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository <Book, UUID> {

    List <Book> findByAuthor( Author author );

    List<Book>findByTitle(String title);

    List<Book>findByIsbn(String isbn);

    List<Book>findByTitleAndPrice( String title, BigDecimal price );

    List<Book>findByTitleOrIsbn( String title, String isbn );
}
