package players;

import board.Board;
import board.Element;
import exceptions.AllShipsPlacedSuccesfully;
import exceptions.AlreadyTakenShotException;
import exceptions.CannotSetParentShipException;
import exceptions.ConnectionFailedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;
import main.savegame.SaveGame;
import ships.Ship;

/**
 * Class covering common methods for Client and Server.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public abstract class NetworkPlayer extends Thread implements Player {

    /**
     * server port
     */
    public final int serverPort = 50001;
    /**
     * Hello message
     */
    public final String HELLO = "Hello.";
    /**
     * message to interrupt the game
     */
    public final String INTERRUPT = "interrupt";
    /**
     * message to ask for next opponents turn
     */
    public final String PLAY = "play";
    protected Socket clientSocket;
    protected PrintWriter outStream;
    protected BufferedReader inStream;
    protected Board placedBoard, targetBoard;

    /**
     * Run method has to be overrided by children class.
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException();
    }

    protected synchronized void openStreams() throws ConnectionFailedException {
        outStream = null;
        inStream = null;
        try {
            outStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inStream = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            throw new ConnectionFailedException("Stream I/O error.");
        }
    }

    protected synchronized void testConnection() throws ConnectionFailedException {
        outStream.println(HELLO);

        String response;
        try {
            response = inStream.readLine();
        } catch (IOException ex) {
            throw new ConnectionFailedException("Stream I/O error.");
        }
        if (INTERRUPT.equals(response)) {
            GameContainer.getInstance().getParentMain().endGame();
            return;
        }
        if (HELLO == null ? response != null : !HELLO.equals(response)) {
            throw new ConnectionFailedException("Stream I/O error.");
        }
    }

    protected synchronized void waitForPlacedTargetBoard() throws ConnectionFailedException {
        GameContainer.getInstance().getParentMain().setStatusText("Waiting for"
                + " opponent getting ready.");
        String shipsString = null;
        try {
            shipsString = inStream.readLine();
        } catch (IOException ex) {
            GameContainer.getInstance().getParentMain().setStatusText("");
            throw new ConnectionFailedException("Stream I/O error.");
        }
        if (INTERRUPT.equals(shipsString)) {
            GameContainer.getInstance().getParentMain().endGame();
            return;
        }

        placedBoard = new Board();
        try {
            placeShipsFromStringToBoard(shipsString, placedBoard);
        } catch (CannotSetParentShipException ex) {
            GameContainer.getInstance().getParentMain().setStatusText("");
            throw new ConnectionFailedException("The client sent corrupted data.");
        }
        GameContainer.getInstance().getParentMain().setStatusText("Opponent ready.");
    }

    protected void placeShipsFromStringToBoard(String shipsString, Board target) throws CannotSetParentShipException {
        String[] ships = shipsString.split("\\" + SaveGame.SHIP_DIVIDER);
        for (String shipString : ships) {
            String[] elements = shipString.split(Board.ELEMENT_DIVIDER);
            Ship toPlace = new Ship(elements.length);
            for (String element : elements) {
                int xCoord = Integer.parseInt(element.split(Element.COORD_DIVIDER)[0]);
                int yCoord = Integer.parseInt(element.split(Element.COORD_DIVIDER)[1]);
                toPlace.add(target.getElement(xCoord, yCoord));
            }
            try {
                target.shipPlaced(toPlace);
            } catch (AllShipsPlacedSuccesfully ex) {
                break;
            }
        }
    }

    @Override
    public synchronized Board getPlacedTargetBoard() {
        return placedBoard;
    }

    @Override
    public void setTargetBoardForPlayer(Board targetBoard) {
        this.targetBoard = targetBoard;
        String shipsString = shipsToString(targetBoard.getPlacedShips());
        outStream.println(shipsString);
    }

    protected String shipsToString(List<Ship> ships) {
        String s = "";
        for (int i = 0; i < ships.size(); i++) {
            s += ships.get(i).toString();
            if (i < ships.size() - 1) {
                s += SaveGame.SHIP_DIVIDER;
            }
        }
        return s;
    }

    @Override
    public void makeNextTurn() {
        outStream.println(PLAY);
        String moveString = null;
        try {
            moveString = inStream.readLine();
        } catch (IOException ex) {
            GameContainer.getInstance().getParentMain().connectionFailed(
                    new ConnectionFailedException("Stream I/O error."));
            return;
        }
        if (INTERRUPT.equals(moveString)) {
            GameContainer.getInstance().getParentMain().endGame();
            return;
        }

        String[] coord = moveString.split(Element.COORD_DIVIDER);
        int xCoord = Integer.parseInt(coord[0]);
        int yCoord = Integer.parseInt(coord[1]);

        boolean hit;
        Element target = targetBoard.getElement(xCoord, yCoord);
        try {
            hit = target.getShot();
        } catch (NullPointerException | AlreadyTakenShotException ex) {
            hit = false;
        }
        GameContainer.getInstance().getParentMain().opponentHitATarget(hit, target);
    }

    @Override
    public synchronized void acceptHit(boolean hit, Element target) {
        String canPlay;
        try {
            canPlay = inStream.readLine();
        } catch (IOException ex) {
            GameContainer.getInstance().getParentMain().connectionFailed(
                    new ConnectionFailedException("Stream I/O error."));
            return;
        }
        if (INTERRUPT.equals(canPlay)) {
            GameContainer.getInstance().getParentMain().endGame();
            return;
        }
        if (PLAY.equals(canPlay)) {
            outStream.println(target.toString());
        }
    }

    @Override
    public void interrupt() {
        if (outStream != null) {
            outStream.println(INTERRUPT);
        }
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (outStream != null) {
            outStream.close();
        }
        try {
            if (inStream != null) {
                inStream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(NetworkPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt();

    }

    /**
     * Constructor requires the Players name.
     * @param name
     */
    public NetworkPlayer(String name) {
        super(name);
    }
}
