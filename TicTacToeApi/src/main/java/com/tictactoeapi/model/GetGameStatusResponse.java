package com.tictactoeapi.model;

import com.tictactoeapi.controller.GameController;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "Game Status Response")
public class GetGameStatusResponse {

    @Schema(description = "TicTacToe game UUID", example = "3cb38a63-ee31-48f7-b937-4631b028c280")
    private final UUID gameID;

    @Schema(description = "Current game state", example = "Player One Turn")
    private final GameState gameState;

    @Schema(description = "TicTacToe game board", example = "[ \n\"X__\", \n\"OX_\", \n\"__O\"]")
    private final List<String> board;

    public GetGameStatusResponse(GameController gameController) {
        this.gameID = gameController.getGameId();
        this.gameState = gameController.getGameState();
        this.board = gameController.drawBoard();
    }

    public UUID getGameID() {
        return gameID;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<String> getBoard() {
        return board;
    }
}
