package com.risk.view.impl.swing;

import com.risk.view.ITournamentDetailView;

import java.awt.Font;
import java.io.File;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The Class SwingGameModeView
 *
 * @author Shreyans&KaranbirPannu
 */

public class SwingTournamentDetailView extends AbstractSwingView implements ITournamentDetailView {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private JFileChooser chooseMap1, chooseMap2, chooseMap3, chooseMap4, chooseMap5;

    /** The final players. */
    private JComboBox<String> playerName1, playerName2, playerName3, playerName4;

    private JComboBox<Integer> noOfMaps;

    private JComboBox<Integer> noOfPlayers;

    private JComboBox<Integer> noOfGames;

    private JComboBox<Integer> noOfTurnsText;

    /**
     * Create the application.
     */
    SwingTournamentDetailView() {
        Font mediumFont = new Font("Serif", Font.BOLD, 25);
        Font smallFont = new Font("Serif", Font.BOLD, 18);
        this.setTitle("Tournament Mode");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(100, 100);
        this.setSize(1000, 840);
        getContentPane().setLayout(null);

        /* The heading label. */
        JLabel headerLabel = new JLabel("Tournament Mode ");
        headerLabel.setBounds(25, 47, 311, 27);
        headerLabel.setFont(mediumFont);
        getContentPane().add(headerLabel);

        /* the no of Maps */
        JLabel noOfMapsLabel = new JLabel("Select the number of Maps ");
        noOfMapsLabel.setBounds(25, 100, 250, 27);
        noOfMapsLabel.setFont(smallFont);
        getContentPane().add(noOfMapsLabel);

        Integer[] itemsMap = { 1, 2, 3, 4, 5 };
        noOfMaps = new JComboBox<>(itemsMap);
        noOfMaps.setBounds(25, 150, 150, 22);
        getContentPane().add(noOfMaps);

        JLabel headingMap = new JLabel("Select the maps to play");
        headingMap.setBounds(25, 180, 250, 22);
        getContentPane().add(headingMap);

        /* The label map file. */
        JLabel labelMapFile1 = new JLabel("Map 1");
        labelMapFile1.setBounds(25, 220, 75, 22);
        getContentPane().add(labelMapFile1);

        /* The browse map. */
        JButton browseMap1Button = new JButton("Browse Map 1");
        browseMap1Button.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_BROWSE_MAP_1));
        browseMap1Button.setBounds(100, 220, 150, 22);
        getContentPane().add(browseMap1Button);

        JLabel labelMapFile2 = new JLabel("Map 2");
        labelMapFile2.setBounds(25, 260, 75, 22);
        getContentPane().add(labelMapFile2);

        JButton browseMap2Button = new JButton("Browse Map 2");
        browseMap2Button.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_BROWSE_MAP_2));
        browseMap2Button.setBounds(100, 260, 150, 22);
        getContentPane().add(browseMap2Button);

        JLabel labelMapFile3 = new JLabel("Map 3");
        labelMapFile3.setBounds(25, 300, 75, 22);
        getContentPane().add(labelMapFile3);

        JButton browseMap3Button = new JButton("Browse Map 3");
        browseMap3Button.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_BROWSE_MAP_3));
        browseMap3Button.setBounds(100, 300, 150, 22);
        getContentPane().add(browseMap3Button);

        JLabel labelMapFile4 = new JLabel("Map 4");
        labelMapFile4.setBounds(25, 340, 75, 22);
        getContentPane().add(labelMapFile4);

        JButton browseMap4Button = new JButton("Browse Map 4");
        browseMap4Button.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_BROWSE_MAP_4));
        browseMap4Button.setBounds(100, 340, 150, 22);
        getContentPane().add(browseMap4Button);

        JLabel labelMapFile5 = new JLabel("Map 5");
        labelMapFile5.setBounds(25, 380, 75, 22);
        getContentPane().add(labelMapFile5);

        JButton browseMap5Button = new JButton("Browse Map 5");
        browseMap5Button.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_BROWSE_MAP_5));
        browseMap5Button.setBounds(100, 380, 150, 22);
        getContentPane().add(browseMap5Button);

        JButton validateMapButton = new JButton("Validate Map");
        validateMapButton.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_VALIDATE_MAP));
        validateMapButton.setBounds(30, 450, 150, 30);
        getContentPane().add(validateMapButton);

        /* the no of Players */
        JLabel noOfPlayersLabel = new JLabel("Select the number of Players ");
        noOfPlayersLabel.setBounds(300, 100, 250, 27);
        noOfPlayersLabel.setFont(smallFont);
        getContentPane().add(noOfPlayersLabel);

        Integer[] itemsPlayer = { 2, 3, 4 };
        noOfPlayers = new JComboBox<>(itemsPlayer);
        noOfPlayers.setBounds(300, 150, 150, 22);
        getContentPane().add(noOfPlayers);

        JLabel headPlayerText = new JLabel("Select the type of Player as per the number selected");
        headPlayerText.setBounds(300, 190, 300, 27);
        getContentPane().add(headPlayerText);

        JLabel playerText = new JLabel("Select Player 1 ");
        playerText.setBounds(300, 240, 100, 27);
        getContentPane().add(playerText);

        String[] typePlayer = new String[4];
        typePlayer[0] = "Aggressive";
        typePlayer[1] = "Benevolent";
        typePlayer[2] = "Random";
        typePlayer[3] = "Cheater";

        playerName1 = new JComboBox<>(typePlayer);
        playerName1.setBounds(410, 240, 150, 27);
        getContentPane().add(playerName1);

        playerText = new JLabel("Select Player 2 ");
        playerText.setBounds(300, 300, 100, 27);
        getContentPane().add(playerText);

        playerName2 = new JComboBox<>(typePlayer);
        playerName2.setBounds(410, 300, 150, 27);
        getContentPane().add(playerName2);

        playerText = new JLabel("Select Player 3 ");
        playerText.setBounds(300, 360, 100, 27);
        getContentPane().add(playerText);

        playerName3 = new JComboBox<>(typePlayer);
        playerName3.setBounds(410, 360, 150, 27);
        getContentPane().add(playerName3);

        playerText = new JLabel("Select Player 4 ");
        playerText.setBounds(300, 430, 100, 27);
        getContentPane().add(playerText);

        playerName4 = new JComboBox<>(typePlayer);
        playerName4.setBounds(410, 430, 150, 27);
        getContentPane().add(playerName4);

        /* the no of Games */
        JLabel noOfGamesLabel = new JLabel("Select the number of Games ");
        noOfGamesLabel.setBounds(600, 100, 250, 27);
        noOfGamesLabel.setFont(smallFont);
        getContentPane().add(noOfGamesLabel);

        Integer[] itemsGames = { 1, 2, 3, 4, 5 };
        noOfGames = new JComboBox<>(itemsGames);
        noOfGames.setBounds(600, 150, 150, 22);
        getContentPane().add(noOfGames);

        /* the no of Turns */
        JLabel noOfTurnsLabel = new JLabel("Select the number of Turns ");
        noOfTurnsLabel.setBounds(600, 250, 250, 27);
        noOfTurnsLabel.setFont(smallFont);
        getContentPane().add(noOfTurnsLabel);

        Integer[] turns = { 10, 15, 20, 25, 30, 35, 40, 45, 50 };
        noOfTurnsText = new JComboBox<>(turns);
        noOfTurnsText.setBounds(600, 300, 150, 22);
        getContentPane().add(noOfTurnsText);

        /* The save and play button */
        JButton saveAndPlayButton = new JButton("Play Game");
        saveAndPlayButton.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_SAVE_AND_PLAY));
        saveAndPlayButton.setBounds(650, 550, 116, 25);
        getContentPane().add(saveAndPlayButton);

        /* The exit button. */
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> fireViewEvent(ITournamentDetailView.ACTION_EXIT));
        exitButton.setBounds(650, 650, 116, 25);
        getContentPane().add(exitButton);

        chooseMap1 = new JFileChooser();
        chooseMap2 = new JFileChooser();
        chooseMap3 = new JFileChooser();
        chooseMap4 = new JFileChooser();
        chooseMap5 = new JFileChooser();

        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Update the view based on observer
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public int getNoOfGames() {
        return (int) noOfGames.getSelectedItem();
    }

    @Override
    public int getNoOfPlayers() {
        return (int) noOfPlayers.getSelectedItem();
    }

    @Override
    public int getNoOfTurnsText() {
        return (int) noOfTurnsText.getSelectedItem();
    }

    @Override
    public int getNoOfMaps() {
        return (int) noOfMaps.getSelectedItem();
    }

    @Override
    public File getMap1() {
        return getMap(chooseMap1);
    }

    @Override
    public File getMap2() {
        return getMap(chooseMap2);
    }

    @Override
    public File getMap3() {
        return getMap(chooseMap3);
    }

    @Override
    public File getMap4() {
        return getMap(chooseMap4);
    }

    @Override
    public File getMap5() {
        return getMap(chooseMap5);
    }

    private File getMap(final JFileChooser chooseMap) {
        if (chooseMap.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooseMap.getSelectedFile();
        }

        return null;
    }

    @Override
    public String getPlayer1Name() {
        return playerName1.getSelectedItem() != null ? playerName1.getSelectedItem().toString() : null;
    }

    @Override
    public String getPlayer2Name() {
        return playerName2.getSelectedItem() != null ? playerName2.getSelectedItem().toString() : null;
    }

    @Override
    public String getPlayer3Name() {
        return playerName3.getSelectedItem() != null ? playerName3.getSelectedItem().toString() : null;
    }

    @Override
    public String getPlayer4Name() {
        return playerName4.getSelectedItem() != null ? playerName4.getSelectedItem().toString() : null;
    }
}