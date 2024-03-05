package com.resendegabriel.investwalletapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Gabriel Resende",
                        email = "gabriellrr2005@gmail.com",
                        url = "https://www.linkedin.com/in/gabrielreisresende/"
                ),
                description = "This API exposes endpoints to manage an investment wallet. With this API you can make a great wallet dashboard and some analysis about your wallet of 'Fundos Imobiliários'. ",
                title = "InvestWallet API Rest",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api/v1"
                ),
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "Bearer Authentication",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                description = "Put your token JWT received in the login response to be able to access all endpoints."
        ),
        @SecurityScheme(
                name = "Basic Auth",
                type = SecuritySchemeType.HTTP,
                scheme = "basic",
                bearerFormat = "Basic",
                description = "Put your user credentials (email and password) to be able to log in the application. This authentication is used to encrypt the user data in the log in process."
        )})
public class SwaggerConfiguration {


}
