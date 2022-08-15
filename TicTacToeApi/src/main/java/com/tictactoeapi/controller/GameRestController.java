package com.tictactoeapi.controller;

import com.tictactoeapi.model.CreateGameResponse;
import com.tictactoeapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Operation(summary = "Create new game", description = "Creates a new tictactoe game!")
    @ApiResponse(responseCode = "201",  description = "Created a new tictactoe game",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateGameResponse.class)))
    @PostMapping(path="/CreateNewGame")
    public ResponseEntity<CreateGameResponse> createNewGame() {
        CreateGameResponse createGameResponse = gameService.createNewGame();
        return new ResponseEntity<>(createGameResponse, HttpStatus.CREATED);
    }

}
