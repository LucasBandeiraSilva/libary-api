package com.github.lucasbandeira.libaryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                version = "V1",
                contact = @Contact(
                        name = "Lucas Bandeira da Silva",
                        email = "lucasbandeirai40@gmail.com")
        )
)
public class OpenApiConfiguration {
}
