package exceptions;

/**
 * Signalize the game couldn't be loaded from a file.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class CannotLoadTheGameException extends Exception {

    /**
     * Constructs an instance of
     * <code>CannotLoadTheGameException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public CannotLoadTheGameException(String msg) {
        super("The game couldn't be loaded! " + msg);
    }
}
