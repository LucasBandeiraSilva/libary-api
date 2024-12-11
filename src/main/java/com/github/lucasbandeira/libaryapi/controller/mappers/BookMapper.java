package com.github.lucasbandeira.libaryapi.controller.mappers;

import com.github.lucasbandeira.libaryapi.dto.BookRegisterDTO;
import com.github.lucasbandeira.libaryapi.dto.ResultSearchBookDTO;
import com.github.lucasbandeira.libaryapi.model.Book;
import com.github.lucasbandeira.libaryapi.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

//    @Autowired
//    AuthorMapper authorMapper;
    @Autowired
    protected AuthorRepository authorRepository;


    @Mapping(target = "author", expression = "java(authorRepository.findById(bookRegisterDTO.authorId()).orElse(null) )")
    public abstract Book toEntity( BookRegisterDTO bookRegisterDTO );

    //@Mapping(target = "authorDTO", expression = "java(authorMapper.toDto(book.getAuthor()))")
    public abstract ResultSearchBookDTO toDTO( Book book );
}
