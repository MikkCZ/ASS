package main.savegame;

import board.Board;
import exceptions.CannotSaveTheGameException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import ships.Ship;

/**
 * Class to save the game to a file.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public final class SaveGame {

    private final Board playersTarget, opponentsTarget;
    /**
     * String used to divide Ships in file output
     */
    public static final String SHIP_DIVIDER = "|";

    /**
     * Constructor requires both players and opponents Board.
     * @param playersTarget Board the player fires on
     * @param opponentsTarget Board the opponent fires on
     * @throws CannotSaveTheGameException if a problem occours
     */
    public SaveGame(Board playersTarget, Board opponentsTarget) throws CannotSaveTheGameException {
        this.playersTarget = playersTarget;
        this.opponentsTarget = opponentsTarget;
        try {
            saveGame();
        } catch (CannotSaveTheGameException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CannotSaveTheGameException("Unknown Error.");
        }
    }

    private void saveGame() throws CannotSaveTheGameException {
        String saveString = "";
        saveString += shipsToString(playersTarget.getPlacedShips());
        saveString += "\n\n";
        saveString += shipsToString(opponentsTarget.getPlacedShips());
        saveString += "\n\n";
        saveString += playersTarget.toString();
        saveString += "\n\n";
        saveString += opponentsTarget.toString();

        writeStringToFile(saveString, FILE_NAME + FILE_SUFFIX);
    }

    private String shipsToString(List<Ship> ships) {
        String s = "";
        for (int i = 0; i < ships.size(); i++) {
            s += ships.get(i).toString();
            if (i < ships.size() - 1) {
                s += SHIP_DIVIDER;
            }
        }
        return s;
    }

    private void writeStringToFile(String toWrite, String fileName) throws CannotSaveTheGameException {
        try {
            FileWriter fw;
            fw = new FileWriter(fileName);
            fw.write(toWrite);
            fw.close();
        } catch (IOException ex) {
            throw new CannotSaveTheGameException("");
        }
    }
    
    /**
     * file name
     */
    public static final String FILE_NAME = "saved_game";
    /**
     * file suffix
     */
    public static final String FILE_SUFFIX = ".seabattle";
}
