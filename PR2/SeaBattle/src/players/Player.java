package players;

import board.Board;
import board.Element;

/**
 * Interface which all Players have to implement.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public interface Player {

    /**
     * Returns players Board with placed Ships.
     * @return players Board with placed Ships
     */
    public Board getPlacedTargetBoard();

    /**
     * Gives to Player a Board to fire on it.
     * @param targetBoard Board to fire on it
     */
    public void setTargetBoardForPlayer(Board targetBoard);

    /**
     * Instuct the Player to make next turn.
     */
    public void makeNextTurn();

    /**
     * Gives the Player opponent turn.
     * @param hit true if the Element contained a Ship
     * @param target shot Element
     */
    public void acceptHit(boolean hit, Element target);

    /**
     * To say the Player the game is terminated.
     */
    public void interrupt();
}
