package com.tictactoeapi.service;

import com.tictactoeapi.controller.GameController;
import com.tictactoeapi.model.CreateGameResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * This will contain the implementation for anything defined in the game service interface, we could also
 * have a collection in here which could be used to store active games
 */

@Service
public class GameServiceImpl implements GameService {

    private List<GameController> games = new ArrayList<>();

    public CreateGameResponse createNewGame() {

        GameController gameController = new GameController();
        games.add(gameController);

        return new CreateGameResponse(gameController);
    }

}
