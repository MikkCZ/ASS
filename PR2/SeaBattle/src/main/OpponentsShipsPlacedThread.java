package main;

import board.Board;
import players.Player;

/**
 * Thread used to separate the waiting process before the opponent places all
 * the Ships.
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class OpponentsShipsPlacedThread extends Thread {

    private final Player opponent;

    /**
     * Constructor requiring the Player to wait for.
     * @param opponent Player to wait for
     */
    public OpponentsShipsPlacedThread(Player opponent) {
        super();
        this.opponent = opponent;
    }

    /**
     * Calls Main.opponentsShipsPlaced() after Ships are placed.
     */
    @Override
    public void run() {
        Board opponentsPlaced = opponent.getPlacedTargetBoard();
        GameContainer.getInstance().getParentMain().opponentsShipsPlaced(opponentsPlaced);
    }
}
