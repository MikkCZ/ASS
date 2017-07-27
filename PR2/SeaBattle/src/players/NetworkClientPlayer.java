package players;

import exceptions.ConnectionFailedException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;

/**
 * Player to connet to existing server for network game.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class NetworkClientPlayer extends NetworkPlayer {

    private final String serverIP;

    /**
     * Establish the connection to required server, obtains opponents Board.
     */
    @Override
    public void run() {
        clientSocket = null;
        try {
            clientSocket = openSocket();
            GameContainer.getInstance().getParentMain().clientConnected();
            openStreams();
            testConnection();
            waitForPlacedTargetBoard();
        } catch (ConnectionFailedException ex) {
            GameContainer.getInstance().getParentMain().connectionFailed(ex);
        }
    }

    private Socket openSocket() throws ConnectionFailedException {
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(serverIP, serverPort);
        } catch (Exception ex) {
            throw new ConnectionFailedException("Server not found.");
        }
        return clientSocket;
    }

    /**
     * Default constructor requires the target server IP address and starts
     * Players live.
     *
     * @param serverIP target server IP address
     */
    public NetworkClientPlayer(String serverIP) {
        super("Network Client Player");
        if (serverIP == null || "".equals(serverIP)) {
            serverIP = "localhost";
        }
        this.serverIP = serverIP;
        start();
    }
}
