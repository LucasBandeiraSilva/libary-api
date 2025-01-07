package com.github.lucasbandeira.libaryapi.repository;

import com.github.lucasbandeira.libaryapi.model.Username;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsernameRepository extends JpaRepository<Username, UUID> {
    Username findByLogin( String login );
}
