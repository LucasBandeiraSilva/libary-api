package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Author;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.model.BookGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see  BookRepositoryTest
 */

public interface BookRepository extends JpaRepository <Book, UUID> {

    List <Book> findByAuthor( Author author );

    List<Book>findByTitle(String title);

    List<Book>findByIsbn(String isbn);

    List<Book>findByTitleAndPrice( String title, BigDecimal price );

    List<Book>findByTitleOrIsbn( String title, String isbn );

    List<Book>findByPublicationDateBetween( LocalDate startDate, LocalDate endDate );

    @Query("select b from Book as b order by b.title, b.price")
    List <Book> showAllBooksSortedByTitleAndPrice();

    @Query("select a from Book b join b.author a ")
    List<Author>listBooksAuthors();

    @Query("select distinct b.title from Book b")
    List<String>listBookNames();

    @Query("""
            select b.gender from Book b
            join b.author a
            where a.nationality = 'Brazilian'
            order by b.gender
            """)
    List<String>listAuthorGenderBrazilian();

    // Named parameters
    @Query("select b from Book b where b.gender = :gender order by :sortParam")
    List <Book> findByGender(@Param("gender") BookGender bookGender, @Param("sortParam") String propertyName );

    // Positional Parameters
    @Query("select b from Book b where b.gender = ?1 order by ?2")
    List <Book> findByGenderPositionalParameters( BookGender bookGender,  String propertyName );
}

