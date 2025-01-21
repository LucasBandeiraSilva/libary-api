package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId( String clientId );
}
