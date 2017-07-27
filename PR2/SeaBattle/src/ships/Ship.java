package ships;

import board.Board;
import board.Element;
import exceptions.CannotSetParentShipException;

/**
 * Ship object which is placed on Boards.
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Ship {

    private final int size;
    private final Element[] elements;
    private int elementsAdded;

    /**
     * Default constructor requires only the Ship size.
     *
     * @param size
     */
    public Ship(int size) {
        this.size = size;
        this.elements = new Element[size];
        this.elementsAdded = 0;
    }

    /**
     * Add an Element to the Ship.
     *
     * @param e Element to add
     * @throws CannotSetParentShipException if the Ship is already fully
     * constructed
     */
    public void add(Element e) throws CannotSetParentShipException {
        if (isCompleted()) {
            throw new CannotSetParentShipException("The ship has been already"
                    + " completed!", e);
        }
        e.setParentShip(this);
        elements[elementsAdded++] = e;
    }

    /**
     * If the Ship is already fully constructed.
     * @return true if the Ship is already fully constructed
     */
    public boolean isCompleted() {
        return elementsAdded == size;
    }

    /**
     * Used to update the Ship and or Board after the Ship is hit
     */
    public void updateAfterShot() {
        if (isAlive()) {
            return;
        }
        for (Element e : elements) {
            Board parentBoard = e.getParentBoard();
            parentBoard.blockNeighbours(e);
        }
    }

    /**
     * If the Ship is alive.
     * @return true if the Ship is not destroyed
     */
    public boolean isAlive() {
        for (Element e : elements) {
            if (e.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the Ship.
     * @return size of the Ship
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (!isCompleted()) {
            return null;
        }
        String s = "";
        for (int i = 0; i < size; i++) {
            s += elements[i].toString();
            if (i < size - 1) {
                s += Board.ELEMENT_DIVIDER;
            }
        }
        return s;
    }
}
