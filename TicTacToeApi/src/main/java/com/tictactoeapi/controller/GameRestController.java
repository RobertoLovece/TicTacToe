package com.tictactoeapi.controller;

import com.tictactoeapi.model.CreateGameResponse;
import com.tictactoeapi.model.GetGameStatusResponse;
import com.tictactoeapi.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// 400 - bad request for bad parameters
// 404 - for bad UUID
@RestController
public class GameRestController {


    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create new game", description = "Creates a new TicTacToe game!")
    @ApiResponse(responseCode = "201", description = "Successfully created a new TicTacToe game",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CreateGameResponse.class)))
    @PostMapping(path = "/CreateNewGame")
    public ResponseEntity<CreateGameResponse> createNewGame() {
        CreateGameResponse createGameResponse = gameService.createNewGame();
        return new ResponseEntity<>(createGameResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all games")
    @ApiResponse(responseCode = "200", description = "Successfully got all TicTacToe games",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetGameStatusResponse.class)))
    @GetMapping(path = "/GetAllGames")
    public ResponseEntity<List<GetGameStatusResponse>> getAllGames() {
        List<GetGameStatusResponse> games = gameService.getAllGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @Operation(summary = "Get a game status", description = "Gets a TicTacToe game from a game UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully got the TicTacToe game",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetGameStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format", content = @Content),
            @ApiResponse(responseCode = "204", description = "No TicTacToe game matches the UUID", content = @Content)
    })
    @GetMapping(path = "/GetGameStatus/{gameId}")
    public ResponseEntity<GetGameStatusResponse> getGameStatus(@Parameter(description = "ID of game status to return", required = true) @PathVariable("gameId") String gameId) {
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

    @Operation(summary = "Delete a game", description = "Deletes a TicTacToe game from a game UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the TicTacToe game", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid UUID", content = @Content)
    })
    @DeleteMapping(path = "/DestroyGame/{gameId}")
    public ResponseEntity<HttpStatus> destroyGame(@Parameter(description = "ID of game to destroy", required = true) @PathVariable("gameId") String gameId) {

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

    @Operation(summary = "Make a move", description = "Makes a move for a TicTacToe game for a game UUID, x-position and y-position")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully made a move",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetGameStatusResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format", content = @Content),
    })
    @PutMapping(path = "/MakeMove/{gameId}/{xPos}/{yPos}")
    public ResponseEntity<GetGameStatusResponse> makeMove(
            @Parameter(description = "ID of game to make move", required = true) @PathVariable("gameId") String gameId,
            @Parameter(description = "x-position of move between 1-3", required = true) @PathVariable("xPos") int xPos,
            @Parameter(description = "y-position of move between 1-3", required = true) @PathVariable("yPos") int yPos)
    {
        GetGameStatusResponse response;
        try {
            response = gameService.makeMove(gameId, xPos, yPos);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
