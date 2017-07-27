package ships;

/**
 * Class to easy create a destroyer.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class Destroyer extends Ship {

    private static final int DS_SIZE = 2;

    /**
     * Constructs destroyer sized ship.
     */
    public Destroyer() {
        super(DS_SIZE);
    }
}
