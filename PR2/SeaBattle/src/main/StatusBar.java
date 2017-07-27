package main;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Status bar.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class StatusBar extends JPanel {

    /**
     * Default constructor with no paramaters.
     */
    public StatusBar() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusLabel = new JLabel();
        initComponents();
    }

    /**
     * Set the status text.
     * @param newText new status text
     */
    public void setText(String newText) {
        statusLabel.setText(newText);
    }

    private void initComponents() {
        this.add(statusLabel);
    }
    private JLabel statusLabel;
}
