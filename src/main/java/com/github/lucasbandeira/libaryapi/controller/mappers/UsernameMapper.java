package com.github.lucasbandeira.libaryapi.controller.mappers;

import com.github.lucasbandeira.libaryapi.controller.dto.UsernameDTO;
import com.github.lucasbandeira.libaryapi.model.Username;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsernameMapper {

    Username toEntity( UsernameDTO usernameDTO );
    
}
