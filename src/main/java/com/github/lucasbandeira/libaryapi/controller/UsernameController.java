package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.dto.UsernameDTO;
import com.github.lucasbandeira.libaryapi.controller.mappers.UsernameMapper;
import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usernames")
@RequiredArgsConstructor
public class UsernameController {

    private final UsernameService usernameService;
    private final UsernameMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save( @RequestBody UsernameDTO usernameDTO ){
        var username = mapper.toEntity(usernameDTO);
        usernameService.save(username);
    }
}
