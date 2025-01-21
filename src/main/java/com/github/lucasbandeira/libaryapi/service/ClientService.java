package com.github.lucasbandeira.libaryapi.service;

import com.github.lucasbandeira.libaryapi.model.Client;
import com.github.lucasbandeira.libaryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client save(Client client){
        var encodedPassword = encoder.encode(client.getClientSecret());
        client.setClientSecret(encodedPassword);
        return clientRepository.save(client);
    }

    public Client getByClientId(String clientId){
        return clientRepository.findByClientId(clientId);
    }
}
