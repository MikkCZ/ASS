package exceptions;

import board.Board;

/**
 * Signalize that all ships has been placed.
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class AllShipsPlacedSuccesfully extends Exception {

    private final Board board;

    /**
     * Constructs an instance of
     * <code>AllShipsPlacedSuccesfully</code> with the default message and
     * completed Board.
     *
     * @param Board completed Board
     */
    public AllShipsPlacedSuccesfully(Board board) {
        super("All ships were placed succesfully.");
        this.board = board;
    }

    /**
     * Return completed Board.
     *
     * @return completed Board
     */
    public Board getBoard() {
        return board;
    }
}
