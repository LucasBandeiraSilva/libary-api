package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.model.Client;
import com.github.lucasbandeira.libaryapi.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@Tag(name = "Clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MANAGER')")
    @Operation(summary = "save",description = "Register a client")
    @ApiResponse(responseCode = "201",description = "successfully registered")
    public void save( @RequestBody Client client ){
        log.info("Registering a new Client: {} with scope: {}",client.getClientId(),client.getScope());
        clientService.save(client);
    }
}
