package com.risk.view.impl.swing;

import com.risk.view.IWelcomeScreenView;

import java.awt.Font;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the Welcome Screen of the game. It displays Welcome Message and the
 * options for user to start the game with default map or to create/edit any map
 * before starting the game
 *
 * @author Shreyans&KaranbirPannu
 */
public class SwingWelcomeScreenView extends AbstractSwingView implements IWelcomeScreenView {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5402570613272324763L;

    /**
     * Constructor.
     */
    SwingWelcomeScreenView() {
        // Sets up the view and adds the components
        JPanel welcomePanel = new JPanel();
        this.setName("RISK GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(800, 700);
        this.setResizable(false);
        this.setVisible(false);

        welcomePanel.setLayout(null);
        this.add(welcomePanel);

        Font largeFont = new Font("Serif", Font.BOLD, 30);
        Font mediumFont = new Font("Serif", Font.BOLD, 18);
        Font smallFont = new Font("Serif", Font.BOLD, 14);

        /* The welcome label. */

        JLabel welcomeLabel = new JLabel("<html>WELCOME TO ONLINE \"RISK\" GAME</html>");
        welcomeLabel.setFont(largeFont);
        welcomePanel.add(welcomeLabel);
        welcomeLabel.setBounds(100, 0, 600, 200);

        /* The question label. */
        JLabel questionLabel = new JLabel("<html>PLEASE SELECT AN OPTION</html>");
        questionLabel.setFont(mediumFont);
        welcomePanel.add(questionLabel);
        questionLabel.setBounds(100, 50, 500, 200);

        /* The create map button. */
        JButton createMapButton = new JButton("Create Map");
        createMapButton.setFont(smallFont);
        createMapButton.addActionListener(
                e -> fireViewEvent(IWelcomeScreenView.ACTION_CREATE_MAP));
        welcomePanel.add(createMapButton);
        createMapButton.setBounds(100, 200, 200, 40);

        /* The edit map button. */
        JButton editMapButton = new JButton("Edit Map");
        editMapButton.setFont(smallFont);
        editMapButton.addActionListener(
                e -> fireViewEvent(IWelcomeScreenView.ACTION_EDIT_MAP));
        welcomePanel.add(editMapButton);
        editMapButton.setBounds(100, 300, 200, 40);

        /* The play map button. */
        JButton playMapButton = new JButton("Play");
        playMapButton.setFont(smallFont);
        playMapButton.addActionListener(
                e -> fireViewEvent(IWelcomeScreenView.ACTION_PLAY_MAP));
        welcomePanel.add(playMapButton);
        playMapButton.setBounds(100, 400, 200, 40);

        /* The exit button. */
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(smallFont);
        exitButton.addActionListener(
                e -> fireViewEvent(IWelcomeScreenView.ACTION_EXIT));
        welcomePanel.add(exitButton);
        exitButton.setBounds(100, 500, 200, 40);

    }

    /**
     * Update the view based on changes.
     *
     * @param arg0 the arg 0
     * @param arg1 the arg 1
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1) {

    }
}
