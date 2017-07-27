package board;

import exceptions.AlreadyTakenShotException;
import exceptions.CannotSetParentShipException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JButton;
import ships.Ship;

/**
 * Class representing one sqaure of the playing Board.
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Element extends JButton {

    private final int xCoord, yCoord;
    private final Board parentBoard;
    private Ship parentShip;
    private boolean alive;

    /**
     * Contructor.
     *
     * @param xCoord Elements vertical coordinate
     * @param yCoord Elements horizontal coordinate
     * @param parentBoard Board where the Element is placed
     */
    public Element(int xCoord, int yCoord, Board parentBoard) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.parentBoard = parentBoard;
        this.parentShip = null;
        alive = true;
        initElement();
    }

    private void initElement() {
        this.setPreferredSize(ELEMENT_DIM);
        this.setBackground(FREE_ELEMT);
        this.setMargin(ELEMENT_MARGIN);
    }

    /**
     * If there is any Ship on this Element.
     *
     * @return true if so, false if there's nothing
     */
    public boolean isFree() {
        return parentShip == null;
    }

    /**
     * If the Element is free to place a Ship on it.
     *
     * @param ship Ship to be placed
     * @return false if the Element is blocked by a neighbouring Ship
     */
    public boolean isBlocked(Ship ship) {
        Element neighbour;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0 && y == 0)
                        || xCoord + x >= BOARD_SIZE || yCoord + y >= BOARD_SIZE
                        || xCoord + x < 0 || yCoord + y < 0) {
                    continue;
                }
                neighbour = parentBoard.getElement(xCoord + x, yCoord + y);
                if (!neighbour.isFree() && neighbour.parentShip != ship) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Assign s Ship on this Element.
     *
     * @param ship Ship to assign
     * @throws CannotSetParentShipException if it's not possible to assign the
     * Element to a Ship
     */
    public void setParentShip(Ship ship) throws CannotSetParentShipException {
        if (!isFree()) {
            throw new CannotSetParentShipException("This element is already"
                    + " assigned to an existing ship!", this);
        } else if (isBlocked(ship)) {
            throw new CannotSetParentShipException("This element is blocked"
                    + " by another existing ship!", this);
        }
        this.parentShip = ship;
        this.setBackground(SHIP_ELEMT);
    }

    /**
     * Fire on the Element.
     * @return true if the shot was succesfull
     * @throws AlreadyTakenShotException if the player has already fired here
     */
    public boolean getShot() throws AlreadyTakenShotException {
        if (!isAlive()) {
            throw new AlreadyTakenShotException(this);
        }
        alive = false;
        updateAfterShot();
        return (!isFree());
    }

    private void updateAfterShot() {
        setEnabled(false);
        if (isFree()) {
            this.setText("O");
        } else {
            this.setText("X");
            this.setBackground(DEAD_ELEMT);
            this.parentShip.updateAfterShot();
        }
    }

    /**
     * Elements vertical coordinate.
     * @return Elements vertical coordinate
     */
    public int getXCoord() {
        return xCoord;
    }

    /**
     * Elements horizontal coordinate.
     * @return Elements horizontal coordinate
     */
    public int getYCoord() {
        return yCoord;
    }

    /**
     * If the Element is still alive.
     * @return true if the Element is still alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Return Ship placed on this Element.
     * @return
     */
    public Board getParentBoard() {
        return parentBoard;
    }

    @Override
    public String toString() {
        return "" + xCoord + COORD_DIVIDER + yCoord;
    }
    //CONSTANTS
    /**
     * Elements JButton width and height
     */
    public static final int ELEMENT_SIZE = 30;
    private final Dimension ELEMENT_DIM = new Dimension(ELEMENT_SIZE, ELEMENT_SIZE);
    private final Insets ELEMENT_MARGIN = new Insets(0, 0, 0, 0);
    /**
     * free Element colour
     */
    public static final Color FREE_ELEMT = Color.GRAY;
    /**
     * colour of an Element with a Ship
     */
    public static final Color SHIP_ELEMT = Color.YELLOW;
    /**
     * colour of destroyed Element with a Ship
     */
    public static final Color DEAD_ELEMT = Color.BLACK;
    private final int BOARD_SIZE = Board.BOARD_SIZE;
    /**
     * toString coordinates divider
     */
    public static final String COORD_DIVIDER = ",";
    //CONSTANTS End
}
