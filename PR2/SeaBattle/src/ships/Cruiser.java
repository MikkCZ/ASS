package ships;

/**
 * Class to easy create a cruiser.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Cruiser extends Ship {

    private static final int CS_SIZE = 3;

    /**
     * Constructs cruiser sized ship.
     */
    public Cruiser() {
        super(CS_SIZE);
    }
}
