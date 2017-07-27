package board;

import exceptions.AllShipsPlacedSuccesfully;
import exceptions.CannotSetParentShipException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;
import ships.Ship;

/**
 * Listener for placing Ships on a Board.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class PlaceShipElementListener extends MouseAdapter implements ActionListener {

    private final Color HOVER_ELEMT = Color.GREEN;

    /**
     * Tryes to place next Ship on the Elements Board.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Element element = (Element) (e.getSource());
        Board board = element.getParentBoard();
        Ship toPlace;
        try {
            toPlace = board.getNextShipToPlace();
        } catch (AllShipsPlacedSuccesfully ex) {
            GameContainer.getInstance().getParentMain().playerShipsPlaced(ex);
            return;
        }

        boolean horizontal = board.horizontal();
        int xCoord = element.getXCoord();
        int yCoord = element.getYCoord();
        boolean valid = validate(toPlace, horizontal, board, xCoord, yCoord);
        if (valid) {
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
            try {
                board.shipPlaced(toPlace);
            } catch (AllShipsPlacedSuccesfully ex) {
                GameContainer.getInstance().getParentMain().playerShipsPlaced(ex);
            }
        }
    }

    /**
     * Highlights next Ship to place if it's possible to place it.
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        Element element = (Element) (e.getSource());
        Board board = element.getParentBoard();
        Ship toPlace;
        try {
            toPlace = board.getNextShipToPlace();
        } catch (AllShipsPlacedSuccesfully ex) {
            GameContainer.getInstance().getParentMain().playerShipsPlaced(ex);
            return;
        }

        boolean horizontal = board.horizontal();
        int xCoord = element.getXCoord();
        int yCoord = element.getYCoord();
        boolean valid = validate(toPlace, horizontal, board, xCoord, yCoord);
        if (valid) {
            for (int i = 0; i < toPlace.size(); i++) {
                if (horizontal) {
                    board.getElement(xCoord, yCoord + i).setBackground(HOVER_ELEMT);
                } else {
                    board.getElement(xCoord + i, yCoord).setBackground(HOVER_ELEMT);
                }
            }
        }
    }

    /**
     * Removes highlighting previously made by mouseEntered().
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        Element element = (Element) (e.getSource());
        Board board = element.getParentBoard();
        Ship toPlace;
        try {
            toPlace = board.getNextShipToPlace();
        } catch (AllShipsPlacedSuccesfully ex) {
            GameContainer.getInstance().getParentMain().playerShipsPlaced(ex);
            return;
        }

        boolean horizontal = board.horizontal();
        int xCoord = element.getXCoord();
        int yCoord = element.getYCoord();
        boolean valid = validate(toPlace, horizontal, board, xCoord, yCoord);
        if (valid) {
            Element temp;
            for (int i = 0; i < toPlace.size(); i++) {
                if (horizontal) {
                    temp = board.getElement(xCoord, yCoord + i);
                } else {
                    temp = board.getElement(xCoord + i, yCoord);
                }
                if (temp == null) {
                    continue;
                }
                temp.setBackground(Element.FREE_ELEMT);
            }
        }
    }

    /**
     * Switches the Ship orientation on left mouse click.
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        boolean isRightButtonPressed =
                (e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK;
        if (isRightButtonPressed) {
            Element element = (Element) (e.getSource());
            Board board = element.getParentBoard();
            mouseExited(e);
            board.switchHorizontal();
            mouseEntered(e);
        }
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
}
