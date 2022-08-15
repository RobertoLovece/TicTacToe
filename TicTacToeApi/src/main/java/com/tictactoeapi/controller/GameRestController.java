package com.tictactoeapi.controller;

import com.tictactoeapi.model.CreateGameResponse;
import com.tictactoeapi.model.GetGameStatusResponse;
import com.tictactoeapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @ApiResponse(responseCode = "201", description = "Created a new tictactoe game",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateGameResponse.class)))
    @PostMapping(path = "/CreateNewGame")
    public ResponseEntity<CreateGameResponse> createNewGame() {
        CreateGameResponse createGameResponse = gameService.createNewGame();
        return new ResponseEntity<>(createGameResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/GetAllGames")
    public ResponseEntity<List<GetGameStatusResponse>> getAllGames() {
        List<GetGameStatusResponse> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping(path = "/GetGameStatus/{gameId}")
    public ResponseEntity<GetGameStatusResponse> getGameStatus(@PathVariable("gameId") String gameId) {
        GetGameStatusResponse getGameStatusResponse = null;
        try {
            getGameStatusResponse = gameService.getGameStatus(UUID.fromString(gameId));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (getGameStatusResponse != null) {
            return new ResponseEntity<>(getGameStatusResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/DestroyGame/{gameId}")
    public ResponseEntity<HttpStatus> destroyGame(@PathVariable("gameId") String gameId) {

        UUID uuid;

        try {
            uuid = UUID.fromString(gameId);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (gameService.destroyGame(uuid)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/MakeMove/{gameId}/{xPos}/{yPos}")
    public ResponseEntity<GetGameStatusResponse> makeMove(@PathVariable("gameId") String gameId, @PathVariable("xPos") int xPos, @PathVariable("yPos") int yPos) {
        GetGameStatusResponse response;
        try {
            response = gameService.makeMove(gameId, xPos, yPos);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
