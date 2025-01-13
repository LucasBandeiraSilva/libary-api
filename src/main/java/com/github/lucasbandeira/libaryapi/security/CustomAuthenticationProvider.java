package com.github.lucasbandeira.libaryapi.security;

import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsernameService usernameService;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
        String login = authentication.getName();
        String passwordEntered = authentication.getCredentials().toString();
        Username userFound = usernameService.getByLogin(login);

        if (userFound == null) throw getUsernameNotFoundException();

        String passwordEncrypted = userFound.getPassword();

        boolean passwordsCheck = encoder.matches(passwordEntered, passwordEncrypted);

        if (passwordsCheck) return new CustomAuthentication(userFound);

        throw getUsernameNotFoundException();
    }

    private UsernameNotFoundException getUsernameNotFoundException() {
        return new UsernameNotFoundException("user and/or password incorrect!");
    }

    @Override
    public boolean supports( Class <?> authentication ) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
