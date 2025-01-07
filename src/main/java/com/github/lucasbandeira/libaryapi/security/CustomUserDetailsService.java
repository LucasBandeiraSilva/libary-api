package com.github.lucasbandeira.libaryapi.security;

import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsernameService usernameService;

    @Override
    public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException {
        Username username = usernameService.getByLogin(login);

        if (username == null) throw new UsernameNotFoundException("Username not found!");

        return User.builder()
                .username(username.getLogin())
                .password(username.getPassword())
                .roles(username.getRoles().toArray(new String[username.getRoles().size()]))
                .build();
    }
}
