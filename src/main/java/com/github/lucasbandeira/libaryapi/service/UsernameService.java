package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Username;
import com.github.lucasbandeira.libaryapi.repository.UsernameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameService {

    private final UsernameRepository usernameRepository;
    private final PasswordEncoder encoder;

    public void save( Username username ) {
        var password = username.getPassword();
        username.setPassword(encoder.encode(password));
        usernameRepository.save(username);
    }

    public Username getByLogin( String login ) {
        return usernameRepository.findByLogin(login);

    }

    public Username getByEmail( String email ) {
        return usernameRepository.findByEmail(email);
    }
}
