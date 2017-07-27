package exceptions;

/**
 * Signalizes that the connection during network game failed.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class ConnectionFailedException extends Exception {

    /**
     * Constructs an instance of
     * <code>ConnectionFailedException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConnectionFailedException(String msg) {
        super(msg);
    }
}
