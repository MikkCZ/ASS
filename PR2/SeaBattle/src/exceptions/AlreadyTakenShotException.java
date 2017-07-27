package exceptions;

import board.Element;

/**
 * Signalize the player has already fired on the Element.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class AlreadyTakenShotException extends Exception {

    private final Element target;

    /**
     * Constructs an instance of
     * <code>AlreadyTakenShotException</code> with the default message and a
     * targeted element.
     *
     * @param target targeted element
     */
    public AlreadyTakenShotException(Element target) {
        super("You have already shot here.");
        this.target = target;
    }

    /**
     * Returns targeted element.
     *
     * @return targeted element
     */
    public Element getTarget() {
        return target;
    }
}
