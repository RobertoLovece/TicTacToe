package com.admiral.controller;

import com.admiral.model.CreateGameResponse;
import com.admiral.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * This will be a rest controller which will contain all our end points when we add the API
 */
@RestController
public class GameRestController {

    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(path="/CreateNewGame")
    public ResponseEntity<CreateGameResponse> createNewGame() {
        CreateGameResponse createGameResponse = gameService.createNewGame();
        return new ResponseEntity<>(createGameResponse, HttpStatus.CREATED);
    }

}
