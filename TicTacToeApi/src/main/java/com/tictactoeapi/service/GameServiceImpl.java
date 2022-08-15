package com.tictactoeapi.service;

import com.tictactoeapi.controller.GameController;
import com.tictactoeapi.model.CreateGameResponse;
import com.tictactoeapi.model.GetGameStatusResponse;
import io.swagger.v3.oas.models.media.UUIDSchema;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * This will contain the implementation for anything defined in the game service interface, we could also
 * have a collection in here which could be used to store active games
 */

@Service
public class GameServiceImpl implements GameService {

    private final Map<UUID, GameController> games = new HashMap<>();

    public CreateGameResponse createNewGame() {

        GameController gameController = new GameController();
        games.put(gameController.getGameId(), gameController);

        return new CreateGameResponse(gameController);
    }

    public GetGameStatusResponse getGameStatus(UUID gameId) {
        GameController game = games.get(gameId);
        return (game != null) ? new GetGameStatusResponse(game) : null;
    }

    public List<GetGameStatusResponse> getAllGames() {

        List<GetGameStatusResponse> responseList = new ArrayList<>();

        for (GameController gameController : games.values()) {
            GetGameStatusResponse response = new GetGameStatusResponse(gameController);
            responseList.add(response);
        }

        return responseList;
    }

    public boolean destroyGame(UUID gameId) {
        return games.remove(gameId) != null;
    }

    public GetGameStatusResponse makeMove(String gameId, int xPos, int yPos) {
        UUID uuid = UUID.fromString(gameId);
        GameController game = games.get(uuid);
        game.makeMove(xPos, yPos);
        return new GetGameStatusResponse(game);
    }

}
