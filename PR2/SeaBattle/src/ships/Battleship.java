package ships;

/**
 * Class to easy create a battlesip.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Battleship extends Ship {

    private static final int BS_SIZE = 4;

    /**
     * Constructs battleship sized ship.
     */
    public Battleship() {
        super(BS_SIZE);
    }
}
