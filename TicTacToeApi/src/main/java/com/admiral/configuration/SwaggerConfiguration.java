package com.admiral.configuration;

/*
 * When we enable Swagger Documentation, this is where we will need to configure and enable swagger
 */

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SwaggerConfiguration {

    @Bean
    public OpenAPI productOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("TicTacToe API")
                        .description("Simple TicTacToe API")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
