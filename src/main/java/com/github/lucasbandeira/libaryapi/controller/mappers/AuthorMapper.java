package com.github.lucasbandeira.libaryapi.controller.mappers;

import com.github.lucasbandeira.libaryapi.dto.AuthorDTO;
import com.github.lucasbandeira.libaryapi.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {


    Author toEntity( AuthorDTO authorDTO );


    AuthorDTO toDto (Author author);
}
