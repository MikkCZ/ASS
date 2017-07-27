package exceptions;

import board.Element;

/**
 * Signalize the Element couldn't be assigned to given Ship.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class CannotSetParentShipException extends Exception {

    private final Element element;

    /**
     * Constructs an instance of
     * <code>CannotSetParentShipException</code> with the specified detail
     * message and related element.
     *
     * @param msg the detail message.
     * @param element  related element
     */
    public CannotSetParentShipException(String msg, Element element) {
        super(msg);
        this.element = element;
    }

    /**
     * Returns related element.
     *
     * @return related element
     */
    public Element getElement() {
        return element;
    }
}
