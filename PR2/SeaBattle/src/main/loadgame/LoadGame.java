package main.loadgame;

import board.Board;
import board.Element;
import exceptions.AllShipsPlacedSuccesfully;
import exceptions.AlreadyTakenShotException;
import exceptions.CannotLoadTheGameException;
import exceptions.CannotSetParentShipException;
import java.io.*;
import main.savegame.SaveGame;
import ships.Ship;

/**
 * Class to load the game from a file.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public final class LoadGame {

    private final int BOARD_SIZE = Board.BOARD_SIZE;
    private Board playersTarget, opponentsTarget;

    /**
     * Loads the game and return the Boards inside LoadedGame class instance.
     * @return loaded Boards inside LoadedGame class instance
     * @throws CannotLoadTheGameException if a problem occours
     */
    public LoadedGame getLoadedGame() throws CannotLoadTheGameException {
        try {
            loadGame();
        } catch (CannotLoadTheGameException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CannotLoadTheGameException("Unknown Error.");
        }
        return new LoadedGame(playersTarget, opponentsTarget);
    }

    private synchronized void loadGame() throws CannotLoadTheGameException {
        String fileString = readFile(SaveGame.FILE_NAME + SaveGame.FILE_SUFFIX);
        String[] rows = fileString.split("\n");
        playersTarget = new Board();
        opponentsTarget = new Board();
        try {
            placeShipsFromStringToBoard(rows[0], playersTarget);
            placeShipsFromStringToBoard(rows[2], opponentsTarget);
        } catch (CannotSetParentShipException ex) {
            throw new CannotLoadTheGameException("File in wrong format.");
        }
        int firstBoardLine = 4;
        fireOnBoardAccordingToStrings(playersTarget, rows, firstBoardLine);
        firstBoardLine += (BOARD_SIZE + 1);
        fireOnBoardAccordingToStrings(opponentsTarget, rows, firstBoardLine);
    }

    private String readFile(String fileName) throws CannotLoadTheGameException {
        DataInputStream dataInput;
        try {
            dataInput = new DataInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException ex) {
            throw new CannotLoadTheGameException("Are you sure you have"
                    + " previous saved game?");
        }
        InputStreamReader isr = new InputStreamReader(dataInput);
        BufferedReader buffered_file = new BufferedReader(isr);
        String fileString = "", line;
        try {
            line = buffered_file.readLine();
            while (line != null) {
                fileString += line;
                fileString += '\n';
                line = buffered_file.readLine();
            }
        } catch (IOException ex) {
            throw new CannotLoadTheGameException("File corrupted.");
        }
        return fileString;
    }

    private void placeShipsFromStringToBoard(String shipsString, Board target) throws CannotSetParentShipException {
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

    private void fireOnBoardAccordingToStrings(Board board, String[] rows, int first) throws CannotLoadTheGameException {
        for (int i = first; i < first + BOARD_SIZE; i++) {
            String[] row = rows[i].split(Board.ELEMENT_DIVIDER);
            if (row.length != BOARD_SIZE) {
                throw new CannotLoadTheGameException("File in wrong format.");
            }
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((Board.ELEMENT_DEAD).equals(row[j])) {
                    try {
                        board.getElement(i - first, j).getShot();
                    } catch (AlreadyTakenShotException ex) {
                        continue;
                    }
                }
            }
        }
    }
}
