package com.tictactoeapi.model;

import com.tictactoeapi.controller.GameController;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "Create Game Response")
public class CreateGameResponse {

    @Schema(description = "TicTacToe game UUID", example = "3cb38a63-ee31-48f7-b937-4631b028c280")
    private final UUID gameID;

    public CreateGameResponse(GameController gameController) {
        this.gameID = gameController.getGameId();
    }

    public UUID getGameID() {
        return gameID;
    }
}
