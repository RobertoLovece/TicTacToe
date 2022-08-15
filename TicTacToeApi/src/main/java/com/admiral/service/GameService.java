package com.admiral.service;

import com.admiral.model.CreateGameResponse;

/*
 * We should define the public facing methods to interact with our game here and implement them in the implementation
 * class for this interface.
 * The first method we'll need to make will be createNewGame
 */
public interface GameService {

    CreateGameResponse createNewGame();

}
