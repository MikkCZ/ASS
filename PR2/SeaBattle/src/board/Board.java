package board;

import exceptions.AllShipsPlacedSuccesfully;
import exceptions.AlreadyTakenShotException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ships.*;

/**
 * Class containing playing board data.
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Board {

    private final Element[][] elements = new Element[BOARD_SIZE][BOARD_SIZE];
    private final List<Ship> placedShips = new ArrayList<Ship>();
    //place section
    private final PlaceShipElementListener placeActionListener = new PlaceShipElementListener();
    private final List<Ship> shipsToPlace = new ArrayList<Ship>();
    private boolean horizontal;
    //target section
    private boolean isTarget;
    private final TargetElementActionListener targetActionListener = new TargetElementActionListener();

    /**
     * Constructor initializing Board Element objects and setting the Board for
     * placing Ships.
     */
    public Board() {
        initElements();
        this.setAsPlaceShipsBoard();
    }

    private void initElements() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                elements[x][y] = new Element(x, y, this);
            }
        }
    }

    private void removeActionListeners(Element e) {
        e.removeActionListener(placeActionListener);
        e.removeMouseListener(placeActionListener);
        e.removeActionListener(targetActionListener);
    }

    private void setAsPlaceShipsBoard() {
        isTarget = false;
        resetShipsToPlace();
        for (Element[] row : elements) {
            for (Element e : row) {
                removeActionListeners(e);
                e.addActionListener(placeActionListener);
                e.addMouseListener(placeActionListener);
            }
        }
    }

    private void resetShipsToPlace() {
        placedShips.clear();
        shipsToPlace.clear();
        for (int i = 0; i < BATTLESHIPS; i++) {
            shipsToPlace.add(new Battleship());
        }
        for (int i = 0; i < CRUISERS; i++) {
            shipsToPlace.add(new Cruiser());
        }
        for (int i = 0; i < DESTROYERS; i++) {
            shipsToPlace.add(new Destroyer());
        }
        for (int i = 0; i < SUBMARINES; i++) {
            shipsToPlace.add(new Submarine());
        }
    }

    /**
     * Returns number of Ship still left to be placed.
     *
     * @return number of not placed ships
     */
    public int shipsLeftToPlace() {
        return shipsToPlace.size();
    }

    /**
     * Returns next Ship to ba placed.
     *
     * @return next Ship to place
     * @throws AllShipsPlacedSuccesfully when there are no more Ships left to be
     * placed
     */
    public Ship getNextShipToPlace() throws AllShipsPlacedSuccesfully {
        if (shipsToPlace.isEmpty()) {
            throw new AllShipsPlacedSuccesfully(this);
        }
        return shipsToPlace.get(0);
    }

    /**
     * Use to confirm the Ship placed.
     * @param placedShip Shipalready placed
     * @throws AllShipsPlacedSuccesfully when there are no more Ships left to be
     * placed
     */
    public void shipPlaced(Ship placedShip) throws AllShipsPlacedSuccesfully {
        placedShips.add(placedShip);
        shipsToPlace.remove(placedShip);
        if (shipsToPlace.isEmpty()) {
            throw new AllShipsPlacedSuccesfully(this);
        }
    }

    /**
     * Returns list of all placed Ships
     * @return list of all placed Ships
     */
    public List<Ship> getPlacedShips() {
        return placedShips;
    }

    /**
     * Switch next Ship orientation.
     */
    public void switchHorizontal() {
        horizontal = !horizontal;
    }

    /**
     * Get Ship orientation.
     * @return true if horizontal, false if vertical
     */
    public boolean horizontal() {
        return horizontal;
    }

    /**
     * Converts the Board for fire on it.
     */
    public void setAsTargetBoard() {
        isTarget = true;
        resetEnabled();
        for (Element[] row : elements) {
            for (Element e : row) {
                removeActionListeners(e);
                if (e.isAlive()) {
                    e.setText("");
                    e.setBackground(Element.FREE_ELEMT);
                } else {
                    if (e.isFree()) {
                        e.setText("O");
                        e.setBackground(Element.FREE_ELEMT);
                    } else {
                        e.setText("X");
                        e.setBackground(Element.DEAD_ELEMT);
                    }
                }
                e.addActionListener(targetActionListener);
            }
        }
    }

    /**
     * Convert the Board to by fired by opponent.
     * @param opponentsTarget true for oponent, false for revert back to player
     */
    public void setAsOpponentsTarget(boolean opponentsTarget) {
        if (opponentsTarget) {
            setAllEnabled(false);
        } else {
            resetEnabled();
        }
        for (Element[] row : elements) {
            for (Element e : row) {
                if (e.isAlive()) {
                    e.setText("");
                    if (e.isFree()) {
                        e.setBackground(Element.FREE_ELEMT);
                    } else {
                        e.setBackground(Element.SHIP_ELEMT);
                    }
                } else {
                    if (e.isFree()) {
                        e.setText("O");
                        e.setBackground(Element.FREE_ELEMT);
                    } else {
                        e.setText("X");
                        e.setBackground(Element.DEAD_ELEMT);
                    }
                }
            }
        }
    }

    /**
     * Are there still any undestried Ships?
     * @return true if at least one Ship is still alive
     */
    public boolean isAnyShipAlive() {
        for (Ship s : placedShips) {
            if (s.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reveals all Ship and blocks all Elements to be shot.
     */
    public void reveal() {
        for (Element[] row : elements) {
            for (Element e : row) {
                removeActionListeners(e);
                if (!e.isFree() && e.isAlive()) {
                    e.setBackground(Element.SHIP_ELEMT);
                }
            }
        }
        setAllEnabled(false);
    }

    /**
     * Returns Element on given coordinates (null if IndexOutOfBounds).
     * @param x horizontal index
     * @param y vertical index
     * @return Element on given coordinates (null if IndexOutOfBounds)
     */
    public Element getElement(int x, int y) {
        if (x >= BOARD_SIZE || y >= BOARD_SIZE || x < 0 || y < 0) {
            return null;
        }
        return elements[x][y];
    }

    /**
     * Blocks Element neighbours after Ship destroyed.
     * @param e Element to block its neighbours
     */
    public void blockNeighbours(Element e) {
        int xCoord = e.getXCoord();
        int yCoord = e.getYCoord();
        Element neighbour;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0 && y == 0)
                        || xCoord + x >= BOARD_SIZE || yCoord + y >= BOARD_SIZE
                        || xCoord + x < 0 || yCoord + y < 0) {
                    continue;
                }
                neighbour = elements[xCoord + x][yCoord + y];
                if (neighbour.isAlive()) {
                    try {
                        neighbour.getShot();
                    } catch (AlreadyTakenShotException ex) {
                        //unexpected exception - should be avoided by previous condition
                        Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Set all Elements enabled.
     * @param enabled true for enabled, false for disabled
     */
    public void setAllEnabled(boolean enabled) {
        for (Element[] row : elements) {
            for (Element e : row) {
                e.setEnabled(enabled);
            }
        }
    }

    /**
     * Resets all Elements enabled according to their state (alive/dead).
     */
    public void resetEnabled() {
        for (Element[] row : elements) {
            for (Element e : row) {
                e.setEnabled(e.isAlive());
            }
        }
    }

    @Override
    public String toString() throws UnsupportedOperationException {
        if (!isTarget) {
            throw new UnsupportedOperationException("Board which is not a target"
                    + " couldn't be converted to a string.");
        }
        return targetBoardToString();
    }

    private String targetBoardToString() {
        String s = "";
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (elements[i][j].isAlive()) {
                    s += ELEMENT_ALIVE;
                } else {
                    s += ELEMENT_DEAD;
                }
                if (j < BOARD_SIZE - 1) {
                    s += ELEMENT_DIVIDER;
                }
            }
            if (i < BOARD_SIZE - 1) {
                s += '\n';
            }
        }
        return s;
    }
    //CONSTANTS
    /**
     * Board width and height.
     */
    public static final int BOARD_SIZE = 10;
    private final int BATTLESHIPS = 1;
    private final int CRUISERS = 2;
    private final int DESTROYERS = 3;
    private final int SUBMARINES = 4;
    /**
     * toString Element divider
     */
    public static final String ELEMENT_DIVIDER = ";";
    /**
     * toString alive Element
     */
    public static final String ELEMENT_ALIVE = "0";
    /**
     * toString dead Element
     */
    public static final String ELEMENT_DEAD = "1";
    //CONSTANTS END
}
