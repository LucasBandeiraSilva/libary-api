package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.controller.dto.UsernameDTO;
import com.github.lucasbandeira.libaryapi.controller.mappers.UsernameMapper;
import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usernames")
@RequiredArgsConstructor
@Tag(name = "Username")
public class UsernameController {

    private final UsernameService usernameService;
    private final UsernameMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "save",description = "Register a client")
    @ApiResponse(responseCode = "201",description = "successfully registered")
    public void save( @RequestBody @Valid UsernameDTO usernameDTO ){
        var username = mapper.toEntity(usernameDTO);
        usernameService.save(username);
    }
}
