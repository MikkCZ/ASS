package main;

import board.*;
import javax.swing.JPanel;

/**
 * JPanel to display playing Board.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class BoardPanel extends JPanel {

    private final Board board;

    /**
     * Constructor requires Board to display.
     * @param board Board to display
     */
    public BoardPanel(Board board) {
        this.board = board;
        initComponents();
    }

    private void initComponents() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                this.add(board.getElement(i, j));
            }
        }
    }

    /**
     * Return displayed Board.
     * @return displayed Board
     */
    public Board getBoard() {
        return board;
    }
}
