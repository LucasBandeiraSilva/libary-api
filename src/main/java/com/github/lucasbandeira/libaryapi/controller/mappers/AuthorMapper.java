package com.github.lucasbandeira.libaryapi.controller.mappers;

import com.github.lucasbandeira.libaryapi.controller.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {


    Author toEntity( AuthorDTO authorDTO );


    AuthorDTO toDto (Author author);
}
