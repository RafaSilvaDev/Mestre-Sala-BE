package com.project.mestresala.mestresalabe.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Rafael Barbosa da Silva",
            email = "barbosa.rafael1@aluno.ifsp.edu.br"
        ),
        title = "OpenApi specification - Mestre Sala API",
        description = "OpenApi Swagger documentation for 'Mestre Sala' Spring API",
        version = "1.0.0"
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8090"
        ),
        @Server(
            description = "Production ENV (not set yet)",
            url = "https://my-production-website.com"
        )
    }
)
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
