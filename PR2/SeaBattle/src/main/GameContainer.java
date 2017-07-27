package main;

/**
 * Contains the Main game thread.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class GameContainer {

    private static GameContainer instance;
    private Main parentMain;

    private GameContainer() {
    }

    /**
     * Singletons getInstance().
     * @return GameContainer instance
     */
    public static GameContainer getInstance() {
        if (instance == null) {
            instance = new GameContainer();
        }
        return instance;
    }

    /**
     * Sets Main to contain.
     * @param parentMain Main to contain
     */
    public void setParentMain(Main parentMain) {
        this.parentMain = parentMain;
    }

    /**
     * Returns contained Main.
     * @return contained Main
     */
    public Main getParentMain() {
        return parentMain;
    }
}
