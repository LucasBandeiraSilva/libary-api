package com.github.lucasbandeira.libaryapi.controller.dto;

import java.util.List;

public record UsernameDTO(String login, String password, List<String>roles) {
}
