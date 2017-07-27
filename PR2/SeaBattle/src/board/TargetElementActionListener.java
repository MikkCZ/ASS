package board;

import exceptions.AlreadyTakenShotException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GameContainer;

/**
 * Listener for shooting on opponents Board.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class TargetElementActionListener implements ActionListener {

    /**
     * Fire on the Element.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Element source = (Element) (e.getSource());
        try {
            boolean hit = source.getShot();
            GameContainer.getInstance().getParentMain().playerHitATarget(hit, source);
        } catch (AlreadyTakenShotException ex) {
            //unexpected exception - the element should be disabled after shoot
            Logger.getLogger(TargetElementActionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
