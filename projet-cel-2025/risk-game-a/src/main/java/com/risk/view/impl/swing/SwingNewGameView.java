package com.risk.view.impl.swing;

import com.risk.model.GameMapModel;
import com.risk.view.INewGameView;

import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * The Class SwingNewGameView
 *
 * @author KaranbirPannu
 */

public class SwingNewGameView extends AbstractSwingView implements INewGameView {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The choose map. */
    private JComboBox<Integer> numOfPlayers;

    private JFileChooser chooseMap;

    /** The final players. */
    private JTextField PlayerName5, PlayerName1, PlayerName2, PlayerName3, PlayerName4;

    private JComboBox<String> playerType5, playerType1, playerType2, playerType3, playerType4;

    /**
     * Create the application.
     */
    public SwingNewGameView() {
        getContentPane().setLayout(null);

        /* The label map file. */
        JLabel labelPlayers = new JLabel("Number of Players?");
        labelPlayers.setBounds(53, 47, 311, 27);
        getContentPane().add(labelPlayers);

        Integer[] items = { 2, 3, 4, 5 };
        numOfPlayers = new JComboBox<>(items);
        numOfPlayers.setBounds(202, 49, 116, 22);
        getContentPane().add(numOfPlayers);

        JLabel playerText2 = new JLabel("Fill the names as per the number selected");
        playerText2.setBounds(53, 83, 311, 27);
        getContentPane().add(playerText2);

        String[] typePlayer = new String[5];
        typePlayer[0] = "Human";
        typePlayer[1] = "Aggressive";
        typePlayer[2] = "Benevolent";
        typePlayer[3] = "Random";
        typePlayer[4] = "Cheater";

        JLabel playerText = new JLabel("Player 1 Name: ");
        playerText.setBounds(53, 110, 311, 27);
        getContentPane().add(playerText);

        playerType1 = new JComboBox<>(typePlayer);
        playerType1.setBounds(202, 116, 150, 27);
        getContentPane().add(playerType1);

        PlayerName1 = new JTextField();
        PlayerName1.setBounds(400, 116, 150, 27);
        getContentPane().add(PlayerName1);

        playerText = new JLabel("Player 2 Name: ");
        playerText.setBounds(53, 140, 311, 27);
        getContentPane().add(playerText);

        playerType2 = new JComboBox<>(typePlayer);
        playerType2.setBounds(202, 146, 150, 27);
        getContentPane().add(playerType2);

        PlayerName2 = new JTextField();
        PlayerName2.setBounds(400, 146, 150, 27);
        getContentPane().add(PlayerName2);

        playerText = new JLabel("Player 3 Name: ");
        playerText.setBounds(53, 170, 311, 27);
        getContentPane().add(playerText);

        playerType3 = new JComboBox<>(typePlayer);
        playerType3.setBounds(202, 176, 150, 27);
        getContentPane().add(playerType3);

        PlayerName3 = new JTextField();
        PlayerName3.setBounds(400, 176, 150, 27);
        getContentPane().add(PlayerName3);

        playerText = new JLabel("Player 4 Name: ");
        playerText.setBounds(53, 200, 311, 27);
        getContentPane().add(playerText);

        playerType4 = new JComboBox<>(typePlayer);
        playerType4.setBounds(202, 206, 150, 27);
        getContentPane().add(playerType4);

        PlayerName4 = new JTextField();
        PlayerName4.setBounds(400, 206, 150, 27);
        getContentPane().add(PlayerName4);

        playerText = new JLabel("Player 5 Name: ");
        playerText.setBounds(53, 230, 311, 27);
        getContentPane().add(playerText);

        playerType5 = new JComboBox<>(typePlayer);
        playerType5.setBounds(202, 236, 150, 27);
        getContentPane().add(playerType5);

        PlayerName5 = new JTextField();
        PlayerName5.setBounds(400, 236, 150, 27);
        getContentPane().add(PlayerName5);

        JLabel labelMapFile = new JLabel("Please Select Map File");
        labelMapFile.setBounds(53, 280, 157, 27);
        getContentPane().add(labelMapFile);

        /* The browse map. */
        JButton browseMapButton = new JButton("Browse");
        browseMapButton.addActionListener(e -> fireViewEvent(INewGameView.ACTION_BROWSE_MAP));
        browseMapButton.setBounds(202, 279, 116, 27);
        getContentPane().add(browseMapButton);

        /* The next button. */
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> fireViewEvent(INewGameView.ACTION_NEXT));
        nextButton.setBounds(202, 337, 116, 25);
        getContentPane().add(nextButton);

        /* The cancel button. */
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> fireViewEvent(INewGameView.ACTION_CANCEL));
        cancelButton.setBounds(53, 337, 97, 25);
        getContentPane().add(cancelButton);

        chooseMap = new JFileChooser();

        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setBounds(100, 100, 700, 700);
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
    public int getNumOfPlayers() {
        return (int) numOfPlayers.getSelectedItem();
    }

    @Override
    public GameMapModel loadGameMapModel() {
        if (chooseMap.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return new GameMapModel(chooseMap.getSelectedFile());
        }

        return null;
    }

    @Override
    public String getPlayer1Type() {
        return getPlayerType(playerType1);
    }

    @Override
    public String getPlayer1Name() {
        return getPlayer1Name(PlayerName1);
    }

    @Override
    public String getPlayer2Type() {
        return getPlayerType(playerType2);
    }

    @Override
    public String getPlayer2Name() {
        return getPlayer1Name(PlayerName2);
    }

    @Override
    public String getPlayer3Type() {
        return getPlayerType(playerType3);
    }

    @Override
    public String getPlayer3Name() {
        return getPlayer1Name(PlayerName3);
    }

    @Override
    public String getPlayer4Type() {
        return getPlayerType(playerType4);
    }

    @Override
    public String getPlayer4Name() {
        return getPlayer1Name(PlayerName4);
    }

    @Override
    public String getPlayer5Type() {
        return getPlayerType(playerType5);
    }

    @Override
    public String getPlayer5Name() {
        return getPlayer1Name(PlayerName5);
    }

    private String getPlayerType(final JComboBox<String> playerType) {
        return playerType.getSelectedItem() != null ?
                playerType.getSelectedItem().toString() : null;
    }

    private String getPlayer1Name(final JTextField playerName) {
        return playerName.getText();
    }
}