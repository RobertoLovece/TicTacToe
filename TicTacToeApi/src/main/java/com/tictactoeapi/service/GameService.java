package com.tictactoeapi.service;

import com.tictactoeapi.model.CreateGameResponse;
import com.tictactoeapi.model.GetGameStatusResponse;

import java.util.List;
import java.util.UUID;

/*
 * We should define the public facing methods to interact with our game here and implement them in the implementation
 * class for this interface.
 * The first method we'll need to make will be createNewGame
 */
public interface GameService {

    CreateGameResponse createNewGame();
    GetGameStatusResponse getGameStatus(UUID gameId);
    List<GetGameStatusResponse> getAllGames();
    boolean destroyGame(UUID gameId);
    GetGameStatusResponse makeMove(String gameId, int xPos, int yPos);
}
