package com.admiral.model;

import com.admiral.controller.GameController;

import java.util.UUID;

/*
 * This is a placeholder for a response model, we can annotate the fields with swagger, we'll probably need more
 * of these response objects depending on what we need to send back to the consumers of the API
 */
public class CreateGameResponse {

    private final UUID gameID;

    public CreateGameResponse(GameController gameController) {
        this.gameID = gameController.getGameId();
    }

    public UUID getGameID() {
        return gameID;
    }
}
