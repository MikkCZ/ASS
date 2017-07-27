package main;

import players.Player;

/**
 * Thread used to separate the waiting process before the opponent fire.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class OpponentsTurnThread extends Thread {

    private final Player opponent;

    /**
     * Constructor requiring the Player to wait for.
     * @param opponent Player to wait for
     */
    public OpponentsTurnThread(Player opponent) {
        super();
        this.opponent = opponent;
    }

    /**
     * Calls Player.makeAMove().
     */
    @Override
    public void run() {
        opponent.makeNextTurn();
    }
}
