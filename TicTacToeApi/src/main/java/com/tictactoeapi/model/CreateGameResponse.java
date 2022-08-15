package com.tictactoeapi.model;

import com.tictactoeapi.controller.GameController;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/*
 * This is a placeholder for a response model, we can annotate the fields with swagger, we'll probably need more
 * of these response objects depending on what we need to send back to the consumers of the API
 */

@Schema(name = "Create Game Service Response")
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
