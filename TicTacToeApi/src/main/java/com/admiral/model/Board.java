package com.admiral.model;

/**
 * Represents the board in a game of tic tac toe and exposes methods to allow markers to be placed on the board and also
 * functionality to check it's self to see if the game has been won or drawn.
 */
public class Board {

    private static final String PLAYER_1_MARKER = "X";
    private static final String PLAYER_2_MARKER = "O";
    private final String[][] grid = new String[3][3];

    /**
     * Attempt to put a marker in the desired position on the board
     * @param xPos - Board X location (not 0 based)
     * @param yPos - Board Y location (not 0 based)
     * @return true if successful, otherwise false
     */
    public boolean updateLocation(int xPos, int yPos, GameState gameState) {
        if(isOutOfBounds(xPos,yPos) || isOccupied(xPos,yPos)) {
            return false;
        }
        switch (gameState) {
            case PLAYER_1_TURN:
                // falls through
            case PLAYER_2_TURN:
                updateBoard(xPos,yPos,gameState);
                return true;
            default:
                return false;
        }
    }

    /**
     * Get the contents of the given board location
     * @param xPos - Non zero based X co-ordinate of board
     * @param yPos - Non zero based Y co-ordinate of board
     * @return The contents of the board at the given location
     */
    public String getLocation(int xPos, int yPos) {
        return  grid[--yPos][--xPos];
    }

    /**
     * Check the board to see if a winning line of player tokens is present on the board.
     * will return true at the first occurrence.
     * @param winningLine - String, either "XXX" or "OOO"
     * @return true if present
     */
    public boolean isGameWon(String winningLine) {
        if ((grid[0][0]+grid[1][0]+grid[2][0]).equals(winningLine)) {
            return true;
        } else if ((grid[0][1]+grid[1][1]+grid[2][1]).equals(winningLine)) {
            return true;
        } else if ((grid[0][2]+grid[1][2]+grid[2][2]).equals(winningLine)) {
            return true;
        } else if ((grid[0][0]+grid[0][1]+grid[0][2]).equals(winningLine)) {
            return true;
        } else if ((grid[1][0]+grid[1][1]+grid[1][2]).equals(winningLine)) {
            return true;
        } else if ((grid[2][0]+grid[2][1]+grid[2][2]).equals(winningLine)) {
            return true;
        } else if ((grid[0][0]+grid[1][1]+grid[2][2]).equals(winningLine)) {
            return true;
        } else return (grid[2][0] + grid[1][1] + grid[0][2]).equals(winningLine);
    }

    /**
     * This method only checks to see if all the spaces of the board are occupied, use the specific isGameWon method to
     * check if game is won before calling this.
     * @return true if all spaces are taken otherwise false.
     */
    public boolean isGameDrawn() {
        for (int yPos = 0; yPos < 3; yPos++) {
            for (int xPos = 0; xPos < 3; xPos++) {
                if (grid[yPos][xPos] == null || grid[yPos][xPos].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Return true if there is already a marker at the given location
    private boolean isOccupied(int xPos, int yPos) {
        --xPos;
        --yPos;
        return !(grid[yPos][xPos] == null || grid[yPos][xPos].isEmpty());
    }

    // Return true if the move would be out of bounds
    private boolean isOutOfBounds(int xPos, int yPos) {
        return (xPos < 1 || xPos > 3 || yPos < 1 || yPos > 3);
    }

    // Place the appropriate player marker at the given position depending on who's turn it is
    private void updateBoard(int xPos,int yPos,GameState gameState) {
        if(gameState == GameState.PLAYER_1_TURN){
            grid[--yPos][--xPos] = PLAYER_1_MARKER;
        } else if (gameState == GameState.PLAYER_2_TURN) {
            grid[--yPos][--xPos] = PLAYER_2_MARKER;
        }
    }

}
