package ass.stankmic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class CloneableSomething implements CloneableObject<CloneableSomething> {

    @Override
    public CloneableSomething clone() {
        try {
            return (CloneableSomething) super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CloneableSomething.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
