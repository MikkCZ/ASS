package players;

import board.Board;
import board.Element;
import exceptions.AllShipsPlacedSuccesfully;
import exceptions.AlreadyTakenShotException;
import exceptions.CannotSetParentShipException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;
import ships.Ship;

/**
 * Basic AI placing Ship and playing randomly.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public final class AIPlayer extends Thread implements Player {

    private final int BOARD_SIZE = Board.BOARD_SIZE;
    private Board targetBoard;
    private Board placedBoard;

    /**
     * Constructor with no parametres run a Board preparation as a Thread.
     */
    public AIPlayer() {
        super("AIPlayer");
        start();
    }

    /**
     * Prepares Ships on Board.
     */
    @Override
    public void run() {
        try {
            preparePlacedBoard();
        } catch (AllShipsPlacedSuccesfully ex) {
            placedBoard.setAsTargetBoard();
        }
    }

    private synchronized void preparePlacedBoard() throws AllShipsPlacedSuccesfully {
        placedBoard = new Board();
        Ship toPlace;
        do {
            toPlace = placedBoard.getNextShipToPlace();
            placeShipRandomly(toPlace, placedBoard);
        } while (toPlace != null);
    }

    private void placeShipRandomly(Ship toPlace, Board board) throws AllShipsPlacedSuccesfully {
        boolean placed = false, horizontal, valid;
        int xCoord, yCoord;
        for (int attempt = 0; attempt < 1000 && !placed; attempt++) {
            xCoord = getRandomCoord();
            yCoord = getRandomCoord();
            horizontal = getRandomBoolean();
            valid = validate(toPlace, horizontal, board, xCoord, yCoord);
            if (valid) {
                placeShipOnBoard(toPlace, horizontal, board, xCoord, yCoord);
                placed = true;
            }
        }
        for (int x = 0; x < BOARD_SIZE && !placed; x++) {
            for (int y = 0; y < BOARD_SIZE && !placed; y++) {
                horizontal = getRandomBoolean();
                valid = validate(toPlace, horizontal, board, x, y);
                if (valid) {
                    placeShipOnBoard(toPlace, horizontal, board, x, y);
                    placed = true;
                } else {
                    valid = validate(toPlace, !horizontal, board, x, y);
                    if (valid) {
                        placeShipOnBoard(toPlace, !horizontal, board, x, y);
                        placed = true;
                    }
                }
            }
        }
        board.shipPlaced(toPlace);
    }

    private void placeShipOnBoard(Ship toPlace, boolean horizontal, Board board, int xCoord, int yCoord) {
        for (int i = 0; i < toPlace.size(); i++) {
            try {
                if (horizontal) {
                    toPlace.add(board.getElement(xCoord, yCoord + i));
                } else {
                    toPlace.add(board.getElement(xCoord + i, yCoord));
                }
            } catch (CannotSetParentShipException ex) {
                //unexpected exception - should be avoided by previous validation
                Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private int getRandomCoord() {
        return getRandomInt(0, BOARD_SIZE - 1);
    }

    private int getRandomInt(int min, int max) {
        return (min + (int) (Math.random() * ((max - min) + 1)));
    }

    private boolean getRandomBoolean() {
        return (Math.random() < 0.5);
    }

    private boolean validate(Ship toPlace, boolean horizontal, Board board, int xCoord, int yCoord) {
        Element temp;
        for (int i = 0; i < toPlace.size(); i++) {
            if (horizontal) {
                temp = board.getElement(xCoord, yCoord + i);
            } else {
                temp = board.getElement(xCoord + i, yCoord);
            }
            if (temp == null) {
                return false;
            }
            if (!temp.isFree() || temp.isBlocked(toPlace)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public synchronized Board getPlacedTargetBoard() {
        return placedBoard;
    }

    @Override
    public void setTargetBoardForPlayer(Board targetBoard) {
        this.targetBoard = targetBoard;
    }

    @Override
    public void makeNextTurn() {
        boolean made = false, hit = false;
        int xCoord, yCoord;
        Element target = null;
        while (!made) {
            xCoord = getRandomCoord();
            yCoord = getRandomCoord();
            target = targetBoard.getElement(xCoord, yCoord);
            if (target == null) {
                continue;
            }
            try {
                hit = target.getShot();
                made = true;
            } catch (AlreadyTakenShotException ex) {
                made = false;
            }
        }
        GameContainer.getInstance().getParentMain().opponentHitATarget(hit, target);
    }

    @Override
    public void acceptHit(boolean hit, Element target) {
        //not needed for this very simple AI
    }
}
