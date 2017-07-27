package ships;

/**
 * Class to easy create a submarine.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Submarine extends Ship {

    private static final int SM_SIZE = 1;

    /**
     * Constructs submarine sized ship.
     */
    public Submarine() {
        super(SM_SIZE);
    }
}
