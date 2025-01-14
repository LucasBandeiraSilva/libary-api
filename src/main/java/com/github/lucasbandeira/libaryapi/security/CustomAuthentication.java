package com.github.lucasbandeira.libaryapi.security;

import com.github.lucasbandeira.libaryapi.model.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

    private final Username username;

    @Override
    public Collection <GrantedAuthority> getAuthorities() {
        return this.username.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.username;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated( boolean isAuthenticated ) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.username.getLogin();
    }
}
