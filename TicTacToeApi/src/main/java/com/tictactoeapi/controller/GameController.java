package com.tictactoeapi.controller;

import com.tictactoeapi.model.Board;
import com.tictactoeapi.model.GameState;

import java.util.UUID;

/**
 * Functionality to play tic tac toe and also exposes public methods to allow game to be played and examined.
 */
public class GameController {

    private final Board board;
    private GameState gameState;
    private final UUID gameId;

    private static final String PLAYER_1_WINNING_LINE = "XXX";
    private static final String PLAYER_2_WINNING_LINE = "OOO";

    /**
     * Initialise a new game
     */
    public GameController() {
        gameId = UUID.randomUUID();
        board = new Board();
        gameState = GameState.PLAYER_1_TURN;
    }

    /**
     * Get the game board object for this game which includes the board its-self and functionality
     * @return the game board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the current game state
     * @return the state of this game
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * This tries to move at the given location, the marker which will be placed depends on game state. If the game falls
     * into an end condition (win or draw)then the game state will be updated accordingly
     * @param xPos - Non-Zero based xPos to move
     * @param yPos - Non-Zero based yPos to move
     */
    public void makeMove(int xPos, int yPos) {
        if(gameState == GameState.PLAYER_1_TURN || gameState == GameState.PLAYER_2_TURN) {
            // Try to move
            if (!board.updateLocation(xPos, yPos, gameState)) {
                throw new IllegalArgumentException("That space is taken or out of bounds.");
            }
            // Is it game over?
            if (board.isGameWon(PLAYER_1_WINNING_LINE)) {
                gameState = GameState.PLAYER_1_WIN;
                return;
            } else if (board.isGameWon(PLAYER_2_WINNING_LINE)) {
                gameState = GameState.PLAYER_2_WIN;
                return;
            } else if (board.isGameDrawn()) {
                gameState = GameState.DRAW;
                return;
            }
            // Game is still in play and move made so switch players
            switchPlayers();
        }
    }

    // Switch control of game to other player
    private void switchPlayers() {
        switch(gameState) {
            case PLAYER_1_TURN:
                gameState = GameState.PLAYER_2_TURN;
                return;
            case PLAYER_2_TURN:
                gameState = GameState.PLAYER_1_TURN;
                return;
            default:
        }
    }

    /**
     * Get this game's unique ID
     * @return gameId GUID
     */
    public UUID getGameId() {
        return gameId;
    }
}
