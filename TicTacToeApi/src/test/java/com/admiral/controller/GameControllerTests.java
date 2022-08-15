package com.admiral.controller;

import com.admiral.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains tests for the game controller which ensures that the game can be played correctly
 */
public class GameControllerTests {

    private GameController gameController;
    private static final String GUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final Pattern GUID_PATTERN = Pattern.compile(GUID_REGEX);

    // Tests should not be dependent on each other so reset state between them
    @BeforeEach
    void resetCleanState() {
        gameController = new GameController();
    }

    // Given: We have created a new instance of game controller
    // When: We check if it is not null
    // Then: It is not null
    @Test
    void canMakeInstance() {
        assertNotNull(gameController);
    }

    // Given: We have created a new instance of game controller
    // When: We get check the state before moving anywhere
    // Then: The status is P1 turn, board has been instantiated, game id is a valid GUID
    @Test
    void gameIsCorrectlyInitialised() {
        assertNotNull(gameController.getBoard());
        assertEquals(GameState.PLAYER_1_TURN, gameController.getGameState());
        Matcher matcher = GUID_PATTERN.matcher(gameController.getGameId().toString());
        assertTrue(matcher.matches());
    }

    // Given: We have created a game
    // When: We take turns but it's not a win
    // Then: We observe game state switching between P1 and P2 turn properly
    @Test
    void takingTurnSwitchesPlayer() {
        gameController.makeMove(1,1);
        assertEquals(GameState.PLAYER_2_TURN, gameController.getGameState());
        gameController.makeMove(1,2);
        assertEquals(GameState.PLAYER_1_TURN, gameController.getGameState());
    }

    // Given: We have a game in progress
    // When: We make moves for each player
    // Then: We can see the tokens appearing on the board in the places we expect
    @Test
    void takingTurnsUpdatesTheBoard() {
        gameController.makeMove(1,1);
        assertEquals("X", gameController.getBoard().getLocation(1,1));
        gameController.makeMove(1,2);
        assertEquals("O", gameController.getBoard().getLocation(1,2));
    }

    // Given: We have a game in progress
    // When: We try to move somewhere that already has a player token
    // Then: We get an error
    @Test
    void takingTurnOnOccupiedSpaceIsHandled() {
        assertThrows(IllegalArgumentException.class, ()-> {
            gameController.makeMove(1,1);
            gameController.makeMove(1,1);
        });
    }

    // Given: We have a game in progress
    // When: We try to move in various places outside the limits of the grid
    // Then: We get an error
    @Test
    void movingOutOfBoundsIsHandled() {
        assertThrows(IllegalArgumentException.class, ()-> gameController.makeMove(0,1));
        assertThrows(IllegalArgumentException.class, ()-> gameController.makeMove(1,0));
        assertThrows(IllegalArgumentException.class, ()-> gameController.makeMove(4,1));
        assertThrows(IllegalArgumentException.class, ()-> gameController.makeMove(0,4));
        assertThrows(IllegalArgumentException.class, ()-> gameController.makeMove(-1,-1));
    }

    // Given: We have a game in progress
    // When: Player 1 gets 3 in a row
    // Then: The game status changes to player 1 win
    @Test
    void playerOneCanWin() {
        gameController.makeMove(1,1);
        gameController.makeMove(1,2);
        gameController.makeMove(2,1);
        gameController.makeMove(1,3);
        gameController.makeMove(3,1);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());
    }

    // Given: We have a game in progress
    // When: Player 2 gets 3 in a row
    // Then: The game status changes to player 2 win
    @Test
    void playerTwoCanWin() {
        gameController.makeMove(2,1);
        gameController.makeMove(1,2);
        gameController.makeMove(3,1);
        gameController.makeMove(2,2);
        gameController.makeMove(3,3);
        gameController.makeMove(3,2);
        assertEquals(GameState.PLAYER_2_WIN, gameController.getGameState());
    }

    // Given: We have a game in progress
    // When: All the spaces are taken without a win for either player
    // Then: The game status changes to draw
    @Test
    void gameCanBeDrawn() {
        gameController.makeMove(1,1);
        gameController.makeMove(2,1);
        gameController.makeMove(1,2);
        gameController.makeMove(2,2);
        gameController.makeMove(3,1);
        gameController.makeMove(3,2);
        gameController.makeMove(2,3);
        gameController.makeMove(1,3);
        gameController.makeMove(3,3);
        assertEquals(GameState.DRAW, gameController.getGameState());
    }

    // Given: We initialise games
    // When: We make moves to get all 8 possible winning lines
    // Then: The game is set to win in each case
    @Test
    void allWinningConditionsWork() {
        //XXX
        //OO
        //X
        gameController = new GameController();
        gameController.makeMove(1,1);
        gameController.makeMove(1,2);
        gameController.makeMove(2,1);
        gameController.makeMove(2,2);
        gameController.makeMove(3,1);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //
        //XXX
        //OO
        gameController = new GameController();
        gameController.makeMove(1,2);
        gameController.makeMove(1,3);
        gameController.makeMove(2,2);
        gameController.makeMove(2,3);
        gameController.makeMove(3,2);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //
        //OO
        //XXX
        gameController = new GameController();
        gameController.makeMove(1,3);
        gameController.makeMove(1,2);
        gameController.makeMove(2,3);
        gameController.makeMove(2,2);
        gameController.makeMove(3,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //XO
        //XO
        //X
        gameController = new GameController();
        gameController.makeMove(1,1);
        gameController.makeMove(2,1);
        gameController.makeMove(1,2);
        gameController.makeMove(2,2);
        gameController.makeMove(1,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //OX
        //OX
        // X
        gameController = new GameController();
        gameController.makeMove(2,1);
        gameController.makeMove(1,1);
        gameController.makeMove(2,2);
        gameController.makeMove(1,2);
        gameController.makeMove(2,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //OOX
        //  X
        //  X
        gameController = new GameController();
        gameController.makeMove(3,1);
        gameController.makeMove(1,1);
        gameController.makeMove(3,2);
        gameController.makeMove(2,1);
        gameController.makeMove(3,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //XOO
        // X
        //  X
        gameController = new GameController();
        gameController.makeMove(1,1);
        gameController.makeMove(2,1);
        gameController.makeMove(2,2);
        gameController.makeMove(3,1);
        gameController.makeMove(3,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());

        //OOX
        // X
        //X
        gameController = new GameController();
        gameController.makeMove(3,1);
        gameController.makeMove(1,1);
        gameController.makeMove(2,2);
        gameController.makeMove(2,1);
        gameController.makeMove(1,3);
        assertEquals(GameState.PLAYER_1_WIN, gameController.getGameState());
    }

    @Test
    void playerTwoWins() {
        gameController.makeMove(1,1);
        gameController.makeMove(2,2 );
        gameController.makeMove(1,2 );
        gameController.makeMove(1,3 );
        gameController.makeMove(2,1 );
        gameController.makeMove(3,1 );
        assertEquals(GameState.PLAYER_2_WIN, gameController.getGameState());
    }

}
