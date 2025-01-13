package com.github.lucasbandeira.libaryapi.security;

import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsernameService usernameService;

    public Username getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        return usernameService.getByLogin(login);
    }
}
