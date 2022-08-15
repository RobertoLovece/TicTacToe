package com.admiral;

/*
 * This Class will be needed to create an entry point in order to run it as a spring boot application!
 * The implementation will be up to you.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicTacToeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }

}
