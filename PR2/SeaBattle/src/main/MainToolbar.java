package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Button toolbar.
 * 
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class MainToolbar extends JPanel {

    /**
     * Default contructor with no parameters.
     */
    public MainToolbar() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        newGameBtn = new JButton();
        saveGameBtn = new JButton();
        loadGameBtn = new JButton();
        startNetwBtn = new JButton();
        endGameBtn = new JButton();
        initComponents();
    }

    private void initComponents() {
        newGameBtn.setText("New game");
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameContainer.getInstance().getParentMain().newGame();
                saveGameBtn.setEnabled(true);
            }
        });
        saveGameBtn.setText("Save game");
        saveGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameContainer.getInstance().getParentMain().saveGame();
            }
        });
        loadGameBtn.setText("Load game");
        loadGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameContainer.getInstance().getParentMain().loadGame();
                saveGameBtn.setEnabled(true);
            }
        });
        startNetwBtn.setText("Network game");
        startNetwBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int asServer = JOptionPane.showConfirmDialog(null,
                        "Start game as server?", "Network game",
                        JOptionPane.YES_NO_OPTION);
                GameContainer.getInstance().getParentMain().newNetworkGame(asServer == JOptionPane.YES_OPTION);
                saveGameBtn.setEnabled(false);
            }
        });
        endGameBtn.setText("End game");
        endGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameContainer.getInstance().getParentMain().endGame();
                saveGameBtn.setEnabled(false);
            }
        });

        this.add(newGameBtn);
        this.add(saveGameBtn);
        this.add(loadGameBtn);
        this.add(startNetwBtn);
        this.add(endGameBtn);
    }
    private final JButton newGameBtn, saveGameBtn, loadGameBtn, startNetwBtn, endGameBtn;
}
