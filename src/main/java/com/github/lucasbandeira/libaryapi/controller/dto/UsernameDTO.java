package com.github.lucasbandeira.libaryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "Username")
public record UsernameDTO(
        @NotBlank(message = "Required Field!")
        String login,
        @Email(message = "Invalid e-mail!")
        @NotBlank(message = "Required Field!")
        String email,
        @NotBlank(message = "Required Field!")
        String password,
        List <String> roles) {
}
