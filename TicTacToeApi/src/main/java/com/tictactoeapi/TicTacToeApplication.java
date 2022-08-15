package com.tictactoeapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicTacToeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }

    @Bean
    public OpenAPI ticTacTieOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("TicTacToe API")
                        .description("Simple TicTacToe API")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
