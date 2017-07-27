package exceptions;

/**
 * Signalize the game couldn't be saved to a file.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class CannotSaveTheGameException extends Exception {

    /**
     * Constructs an instance of
     * <code>CannotSaveTheGameException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public CannotSaveTheGameException(String msg) {
        super("The game couldn't be saved! " + msg);
    }
}
