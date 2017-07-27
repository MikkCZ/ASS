package players;

import exceptions.ConnectionFailedException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;

/**
 * Player to create a server for network game.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class NetworkServerPlayer extends NetworkPlayer implements Player {

    private ServerSocket serverSocket;

    /**
     * Opens the connection, wait for the client, obtains opponents Board.
     */
    @Override
    public void run() {
        serverSocket = null;
        clientSocket = null;
        try {
            serverSocket = openServerSocket();
            clientSocket = waitForClientConnection();
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
            openStreams();
            testConnection();
            waitForPlacedTargetBoard();
        } catch (ConnectionFailedException ex) {
            GameContainer.getInstance().getParentMain().connectionFailed(ex);
        }
    }

    private ServerSocket openServerSocket() throws ConnectionFailedException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new ConnectionFailedException("Port " + serverPort
                    + " is already used. Try it again or restart the game.");
        }
        return serverSocket;
    }

    private synchronized Socket waitForClientConnection() throws ConnectionFailedException {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new ConnectionFailedException("Stream I/O error.");
        }
        GameContainer.getInstance().getParentMain().clientConnected();
        return clientSocket;
    }

    @Override
    public void interrupt() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(NetworkServerPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt();
    }

    /**
     * Default constructor requires no parameters but starts the Players live.
     */
    public NetworkServerPlayer() {
        super("Network Server Player");
        start();
    }
}
