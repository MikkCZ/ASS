package main.loadgame;

import board.Board;

/**
 * Class to hold the loaded game Boards.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class LoadedGame {

    private final Board playersTarget, opponents;

    /**
     * Constructor requres both players and opponents Board.
     * @param playersTarget Board the player fires on
     * @param opponentsTarget Board the opponent fires on
     */
    public LoadedGame(Board playersTarget, Board opponentsTarget) {
        this.playersTarget = playersTarget;
        this.opponents = opponentsTarget;
    }

    /**
     * Return Board the player fires on.
     * @return Board the player fires on
     */
    public Board getPlayersTarget() {
        return playersTarget;
    }

    /**
     * Return Board the opponent fires on.
     * @return Board the opponent fires on
     */
    public Board getOpponentsTarget() {
        return opponents;
    }
}
