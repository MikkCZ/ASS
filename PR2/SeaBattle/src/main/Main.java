package main;

import board.*;
import exceptions.AllShipsPlacedSuccesfully;
import exceptions.CannotLoadTheGameException;
import exceptions.CannotSaveTheGameException;
import exceptions.ConnectionFailedException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.loadgame.*;
import main.savegame.*;
import players.*;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Main extends javax.swing.JFrame implements Runnable {

    private JPanel leftPanel, rightPanel;
    private StatusBar status;
    private BoardPanel playersTarget, opponentsTarget;
    private Player opponent;

    /**
     * Called by PlacedShipElementListener when all players Ships are placed.
     *
     * @param ex AllShipsPlacedSuccesfully containing players Board
     */
    public void playerShipsPlaced(AllShipsPlacedSuccesfully ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "OK",
                JOptionPane.INFORMATION_MESSAGE);
        Board opponents = ex.getBoard();
        opponents.setAsTargetBoard();
        opponents.setAsOpponentsTarget(true);
        opponentsTarget = new BoardPanel(opponents);
        opponent.setTargetBoardForPlayer(opponents);
        Thread oppoPlaced = new OpponentsShipsPlacedThread(opponent);
        oppoPlaced.start();
    }

    /**
     * Called by OpponentsShipsPlacedThread when all opponents Ships are placed.
     *
     * @param players opponents Board
     */
    public void opponentsShipsPlaced(Board players) {
        removePanels();
        players.setAsTargetBoard();
        playersTarget = new BoardPanel(players);
        leftPanel = playersTarget;
        rightPanel = opponentsTarget;
        pack();
        if (!(opponent instanceof NetworkServerPlayer)) {
            opponentsTurn();
        }
    }

    /**
     * Called by any class when connection fails during the network game.
     *
     * @param ex ConnextionFailedException with cause message
     */
    public void connectionFailed(ConnectionFailedException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "OK",
                JOptionPane.ERROR_MESSAGE);
        endGame();
    }

    /**
     * To start new network game.
     *
     * @param asServer true if the player wants to be a server
     */
    public void newNetworkGame(boolean asServer) {
        newGame();
        opponentsTarget.getBoard().setAllEnabled(false);

        if (asServer) {
            opponent = new NetworkServerPlayer();
            String playerIP;
            try {
                playerIP = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException ex) {
                playerIP = "unknown";
            }
            setStatusText("Waiting for opponent connection. Your IP is " + playerIP);
        } else {
            String serverIP = JOptionPane.showInputDialog(null, "Server IP:",
                    "Server IP", JOptionPane.QUESTION_MESSAGE);
            opponent = new NetworkClientPlayer(serverIP);
        }
    }

    /**
     * Called by NetworkPlayer or it's children when the opponent is connected.
     */
    public void clientConnected() {
        opponentsTarget.getBoard().setAllEnabled(true);
        GameContainer.getInstance().getParentMain().setStatusText("Opponent connected.");
    }

    /**
     * To start new offline game with AI.
     */
    public void newGame() {
        endGame();
        opponent = new AIPlayer();
        removePanels();
        playersTarget = new BoardPanel(new Board());
        playersTarget.getBoard().setAllEnabled(false);
        opponentsTarget = new BoardPanel(new Board());
        leftPanel = playersTarget;
        rightPanel = opponentsTarget;
        pack();
    }

    /**
     * To end any type of game.
     */
    public void endGame() {
        setStatusText("");
        if (opponent instanceof NetworkPlayer) {
            opponent.interrupt();
        }
        opponent = null;
        removePanels();
        playersTarget = null;
        opponentsTarget = null;
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        pack();
    }

    private void opponentsTurn() {
        playersTarget.getBoard().setAllEnabled(false);
        Thread oppoMove = new OpponentsTurnThread(opponent);
        oppoMove.start();
    }

    /**
     * After player fires.
     *
     * @param hit true if the fire was succesfull
     * @param target targeted Element
     */
    public void playerHitATarget(boolean hit, Element target) {
        opponent.acceptHit(hit, target);
        if (hit) {
            if (!opponentsTarget.getBoard().isAnyShipAlive()) {
                opponentWon();
            } else if (!playersTarget.getBoard().isAnyShipAlive()) {
                playerWon();
            }
        } else {
            opponentsTurn();
        }
    }

    /**
     * After opponent fires.
     *
     * @param hit true if the fire was succesfull
     * @param target targeted Element
     */
    public void opponentHitATarget(boolean hit, Element target) {
        if (hit) {
            if (!opponentsTarget.getBoard().isAnyShipAlive()) {
                opponentWon();
            } else if (!playersTarget.getBoard().isAnyShipAlive()) {
                playerWon();
            } else {
                opponentsTurn();
            }
        } else {
            playersTarget.getBoard().resetEnabled();
        }
    }

    private void opponentWon() {
        revealTargets();
        JOptionPane.showMessageDialog(null, "Opponent won!", "OK",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void playerWon() {
        revealTargets();
        JOptionPane.showMessageDialog(null, "You won", "OK",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void revealTargets() {
        opponentsTarget.getBoard().reveal();
        playersTarget.getBoard().reveal();
    }

    /**
     * Loads game from file using LoadGame class.
     */
    public void loadGame() {
        LoadedGame loaded;
        try {
            loaded = new LoadGame().getLoadedGame();
        } catch (CannotLoadTheGameException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "OK",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        newGame();
        removePanels();
        Board players = loaded.getPlayersTarget();
        Board opponents = loaded.getOpponentsTarget();
        players.setAsTargetBoard();
        opponents.setAsTargetBoard();
        opponent.setTargetBoardForPlayer(opponents);
        opponents.setAsOpponentsTarget(true);
        playersTarget = new BoardPanel(players);
        opponentsTarget = new BoardPanel(opponents);
        leftPanel = playersTarget;
        rightPanel = opponentsTarget;
        pack();
        JOptionPane.showMessageDialog(null, "Game loaded", "OK",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Save the current offline game to file using SaveGame class.
     */
    public void saveGame() {
        try {
            new SaveGame(playersTarget.getBoard(), opponentsTarget.getBoard());
            JOptionPane.showMessageDialog(null, "Game saved", "OK",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (CannotSaveTheGameException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "OK",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * JFrame.pack() adjustment for to automate some needs of Mains every pack()
     * call.
     */
    @Override
    public void pack() {
        removePanels();
        this.add(leftPanel, BorderLayout.LINE_START);
        this.add(rightPanel, BorderLayout.LINE_END);
        leftPanel.setPreferredSize(PANEL_DIMENSION);
        rightPanel.setPreferredSize(PANEL_DIMENSION);
        leftPanel.setLayout(PANEL_LAYOUT);
        rightPanel.setLayout(PANEL_LAYOUT);
        super.pack();
    }

    private void removePanels() {
        if(leftPanel != null) {
            this.remove(leftPanel);
        }
        if(rightPanel != null) {
            this.remove(rightPanel);
        }
    }

    private void initComponents() {
        this.setTitle("SeaBattle");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
        this.add(new MainToolbar(), BorderLayout.PAGE_START);
        status = new StatusBar();
        status.setPreferredSize(new Dimension(0, 20));
        this.add(status, BorderLayout.PAGE_END);
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        pack();
    }

    /**
     * Sets status text.
     * @param newText new status text
     */
    public void setStatusText(String newText) {
        status.setText(newText);
    }

    /**
     * Runs the Main frame - creates a GameContainer, initiates frame elements and sets it visible.
     */
    @Override
    public synchronized void run() {
        GameContainer gc = GameContainer.getInstance();
        gc.setParentMain(this);
        initComponents();
        this.setVisible(true);
        newGame();
    }

    public static void main(String[] args) {
        Thread main = new Thread(new Main());
        main.start();
    }
    //CONSTANTS
    private final int PANEL_SIZE = Board.BOARD_SIZE * Element.ELEMENT_SIZE;
    private final Dimension PANEL_DIMENSION = new Dimension(PANEL_SIZE, PANEL_SIZE);
    private final java.awt.LayoutManager PANEL_LAYOUT =
            new java.awt.GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE);
    //CONSTANTS End    
}
