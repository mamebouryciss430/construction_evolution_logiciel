package com.risk.view.impl.swing;

import com.risk.model.GamePlayModel;
import com.risk.utilities.SaveGame;
import com.risk.view.IGameModeView;

import java.awt.Font;
import java.util.Observable;

import javax.swing.*;

/**
 * The Class SwingGameModeView
 *
 * @author Namita
 */

public class SwingGameModeView extends AbstractSwingView implements IGameModeView {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private JFileChooser chooseGame;

    /**
     * Create the application.
     */
    SwingGameModeView() {
        Font mediumFont = new Font("Serif", Font.BOLD, 25);
        this.setTitle("Select Game Mode");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 100);
        this.setSize(1000, 840);
        getContentPane().setLayout(null);

        /* The single mode. */
        JLabel gameModeLabel = new JLabel("Select the Mode to play Game");
        gameModeLabel.setBounds(150, 150, 400, 100);
        gameModeLabel.setFont(mediumFont);
        getContentPane().add(gameModeLabel);

        /* The single mode. */
        JButton singleMode = new JButton("Single game mode");
        singleMode.addActionListener(e -> fireViewEvent(IGameModeView.ACTION_SINGLE_MODE));
        singleMode.setBounds(153, 300, 150, 40);
        getContentPane().add(singleMode);

        /* The tournament mode. */
        JButton tournamentMode = new JButton("Tournament Mode");
        tournamentMode.addActionListener(e -> fireViewEvent(IGameModeView.ACTION_TOURNAMENT_MODE));
        tournamentMode.setBounds(350, 300, 150, 40);
        getContentPane().add(tournamentMode);

        /* The Load game . */
        JButton loadGame = new JButton("Load Previous Game");
        loadGame.addActionListener(e -> fireViewEvent(IGameModeView.ACTION_LOAD_GAME));
        loadGame.setBounds(225, 375, 175, 40);
        getContentPane().add(loadGame);

        chooseGame = new JFileChooser();

        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public GamePlayModel loadGamePlayModel() {
        if (chooseGame.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                GamePlayModel gamePlayModel =
                        new SaveGame().readFROMJSONFile(chooseGame.getSelectedFile());
                JOptionPane.showMessageDialog(
                        this, "Gane Loaded Successfully!", "Game Loaded",
                        JOptionPane.INFORMATION_MESSAGE);

                return gamePlayModel;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Update the view based on observer
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}