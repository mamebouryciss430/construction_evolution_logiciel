package com.risk.view.impl.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultCaret;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.view.IReinforcementView;

/**
 * This View provides the reinforcementView of the Game Play. It also provides
 * the observer pattern when the data is modified
 *
 * @author Gursimran
 *
 */

public class SwingReinforcementView extends AbstractSwingView implements IReinforcementView {

    private GameMapModel gameMapModel;
    private PlayerModel playerModel;
    private GamePlayModel gamePlayModel;

    private JPanel welcomePanel;
    private JPanel graphicPanel;

    private JPanel consoleMainPanel;
    private JScrollPane consolePanel;
    private JTextArea consoleTextArea;

    private JLabel welcomeLabel;
    private JLabel noOfTroopsLabel;

    private JComboBox<Integer> numOfTroopsComboBox;
    private JButton addButton;
    private JButton addMoreButton;
    private JButton exitCardButton;
    private JButton saveButton;
    private JLabel listOfCountriesLabel;
    private JLabel reinnforcementCardLabel;
    private JTextField cardIdField;

    private JLabel countryListLabel;
    private JComboBox<Object> countryListComboBox;
    private Object[] countryListArray;

    /** The countries view renderer. */
    private CountryViewRenderer countriesViewRenderer;

    private JButton[] button;
    private ArrayList<Double> percentage;
    private ArrayList<Color> colors;

    /**
     * Instantiates a new reinforcement view.
     *
     * @param gamePlayModel the game play model
     */
    public SwingReinforcementView(GamePlayModel gamePlayModel) {
        this.gameMapModel = gamePlayModel.getGameMap();
        this.setTitle("Reinforcement Phase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(1600, 840);
        this.setResizable(false);
        this.setVisible(false);
        this.addMoreButton = new JButton("Add More Card");
        this.addMoreButton.addActionListener(e -> fireViewEvent(IReinforcementView.ACTION_ADD_MORE));

        this.exitCardButton = new JButton("Exit Card Addition");
        this.exitCardButton.addActionListener(e -> fireViewEvent(IReinforcementView.ACTION_EXIT_CARD));

        this.saveButton = new JButton("Save Game");
        this.saveButton.addActionListener(e -> fireViewEvent(IReinforcementView.ACTION_SAVE));

        welcomePanel = new JPanel();
        graphicPanel = new JPanel();
        this.add(graphicPanel);
        graphicPanel.setSize(1200, 650);
        graphicPanel.setBackground(Color.WHITE);

        this.consoleMainPanel = new JPanel();
        this.consoleMainPanel.setBorder(new BevelBorder(1));

        this.consoleTextArea = new JTextArea("Life is a risk, instead play risk !!!\n", 10, 500);
        this.consoleTextArea.setEditable(false);
        this.consoleTextArea.setFocusable(false);
        this.consoleTextArea.setVisible(true);
        this.consoleTextArea.setForeground(Color.WHITE);
        this.consoleTextArea.setBackground(Color.BLACK);
        DefaultCaret caret = (DefaultCaret) this.consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        this.consolePanel = new JScrollPane(this.consoleTextArea);
        this.consolePanel.setPreferredSize(new Dimension(1580, 130));

        this.consoleMainPanel.add(this.consolePanel, BorderLayout.WEST);

        this.getContentPane().add(this.consoleMainPanel, BorderLayout.SOUTH);

        this.addButton = new JButton("Add");
        this.addButton.addActionListener(e -> fireViewEvent(IReinforcementView.ACTION_ADD));
        this.add(welcomePanel);

        this.playerModel = this.gameMapModel.getPlayerTurn();
        this.welcomeLabel = new JLabel("It's " + playerModel.getName() + "'s turn");

        welcomePanel.setLayout(null);
        graphicPanel.setLayout(null);

        updateWindow(gamePlayModel, playerModel);
    }

    /**
     * This updateWindow method is called whenever the model is updated. It updates
     * the Screen for Reinforcement Phase
     *
     * @param gamePlayModel
     * @param playerModel
     */
    public void updateWindow(GamePlayModel gamePlayModel, PlayerModel playerModel) {

        Font largeFont = new Font("Serif", Font.BOLD, 18);
        Font mediumFont = new Font("Serif", Font.BOLD, 14);
        Font smallFont = new Font("Serif", Font.BOLD, 12);

        this.gamePlayModel = gamePlayModel;
        this.gameMapModel = gamePlayModel.getGameMap();
        this.playerModel = this.gameMapModel.getPlayerTurn();

        welcomeLabel.setBounds(1300, 80, 300, 25);
        welcomeLabel.setFont(largeFont);
        welcomePanel.add(welcomeLabel);

        if (this.gamePlayModel.getConsoleText() != null) {
            this.consoleTextArea.setText(this.gamePlayModel.getConsoleText().toString());
        }

        if (this.playerModel.getOwnedCards().size() > 0 && this.playerModel.getShowReinforcementCard() == true) {
            this.reinnforcementCardLabel = new JLabel("Enter the id of the card :");
            reinnforcementCardLabel.setBounds(1300, 120, 150, 25);
            welcomePanel.add(reinnforcementCardLabel);

            cardIdField = new JTextField();
            cardIdField.setBounds(1300, 150, 150, 25);
            welcomePanel.add(cardIdField);

            this.addMoreButton.setBounds(1300, 190, 150, 35);
            welcomePanel.add(this.addMoreButton);

            this.exitCardButton.setBounds(1300, 230, 150, 35);
            welcomePanel.add(this.exitCardButton);
        }
        if (this.playerModel.getOwnedCards().size() == 0 || this.playerModel.getShowReinforcementCard() == false) {
            this.noOfTroopsLabel = new JLabel("Number of Troops :");
            noOfTroopsLabel.setBounds(1300, 120, 150, 25);
            welcomePanel.add(noOfTroopsLabel);

            Integer[] troops = new Integer[this.gameMapModel.getPlayerTurn().getremainTroop()];
            for (int i = 0; i < this.gameMapModel.getPlayerTurn().getremainTroop(); i++) {
                troops[i] = i + 1;
            }

            numOfTroopsComboBox = new JComboBox(troops);
            numOfTroopsComboBox.setBounds(1300, 150, 150, 25);
            welcomePanel.add(numOfTroopsComboBox);

            this.countryListLabel = new JLabel("Select Country :");
            countryListLabel.setBounds(1300, 180, 150, 25);
            welcomePanel.add(this.countryListLabel);

            ArrayList<CountryModel> listOfCountries = new ArrayList<CountryModel>();
            for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
                if (playerModel.getName().equals(this.gameMapModel.getCountries().get(i).getRulerName())) {
                    listOfCountries.add(this.gameMapModel.getCountries().get(i));
                }
            }

            countriesViewRenderer = new CountryViewRenderer();
            countryListArray = listOfCountries.toArray();
            countryListComboBox = new JComboBox(countryListArray);
            welcomePanel.add(this.countryListComboBox);

            if (countryListArray.length > 0) {
                countryListComboBox.setRenderer(countriesViewRenderer);
            }
            countryListComboBox.setBounds(1300, 220, 150, 25);

            this.addButton.setBounds(1300, 260, 150, 25);
            welcomePanel.add(this.addButton);

            this.saveButton.setBounds(1300, 500, 150, 25);
            welcomePanel.add(this.saveButton);
        }

        int n = this.gameMapModel.getCountries().size();
        button = new JButton[n];

        PlayerModel pm = new PlayerModel();
        CountryModel cm = new CountryModel();

        for (int i = 0; i < gameMapModel.getCountries().size(); i++) {

            button[i] = new JButton();
            button[i].setText(gameMapModel.getCountries().get(i).getCountryName().substring(0, 3));
            button[i].setBackground(gameMapModel.getCountries().get(i).getBackgroundColor());
            button[i].setToolTipText("Troops: " + gameMapModel.getCountries().get(i).getArmies());
            cm = gameMapModel.getCountries().get(i);
            pm = gamePlayModel.getPlayer(cm);
            Color col = pm.getColor();
            Border border = BorderFactory.createLineBorder(col, 3);

            button[i].setBorder(border);
            button[i].setOpaque(true);
            if (this.gameMapModel.getContinents().get(0).getContinentName().equals("clifftop")
                    || this.gameMapModel.getContinents().get(0).getContinentName().equals("North America")) {
                button[i].setBounds(this.gameMapModel.getCountries().get(i).getXPosition(),
                        this.gameMapModel.getCountries().get(i).getYPosition(), 50, 50);
            } else {
                button[i].setBounds(this.gameMapModel.getCountries().get(i).getXPosition() * 2,
                        this.gameMapModel.getCountries().get(i).getYPosition() * 2, 50, 50);
            }

            graphicPanel.add(button[i]);
        }

    }

    /**
     * Countries are rendered as button and linked with Swing using Graphics.
     *
     * @see java.awt.Window#paint(java.awt.Graphics)
     */
    public void paint(final Graphics g) {

        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        Point[] connectorPoints = new Point[this.gameMapModel.getCountries().size()];

        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            connectorPoints[i] = SwingUtilities.convertPoint(this.gameMapModel.getCountries().get(i), 0, 0, this);

        }

        for (int k = 0; k < this.gameMapModel.getCountries().size(); k++) {
            if (this.gameMapModel.getCountries().get(k).getLinkedCountries() != null) {
                ArrayList<CountryModel> neighbourCountries = (ArrayList<CountryModel>) this.gameMapModel.getCountries()
                        .get(k).getLinkedCountries();

                for (int j = 0; j < neighbourCountries.size(); j++) {
                    for (int i = 0; i < this.gameMapModel.getCountries().size(); i++)
                        if (neighbourCountries.get(j).equals(this.gameMapModel.getCountries().get(i)))
                            g2.drawLine(connectorPoints[i].x + 25, connectorPoints[i].y + 25, connectorPoints[k].x + 25,
                                    connectorPoints[k].y + 25);

                }
            }
        }

    }

    @Override
    public int getNumOfTroops() {
        return numOfTroopsComboBox.getSelectedItem() != null ?
                (int) numOfTroopsComboBox.getSelectedItem() : -1;
    }

    @Override
    public CountryModel getCountryModel() {
        return (CountryModel) countryListComboBox.getSelectedItem();
    }

    @Override
    public int getCardId() {
        return Integer.parseInt(this.cardIdField.getText());
    }

    /**
     * Getter method that provides us a map model corresponding to a map name
     *
     */

    public class CountryViewRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            CountryModel countryModel = (CountryModel) value;
            if (countryModel != null)
                setText(countryModel.getCountryName());

            return this;
        }
    }

    /**
     * Update method is to Update the start up Phase. This is declared as
     * observable. so when the values are changed the view is updated automatically
     * by notifying the observer.
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable obs, Object arg) {
        welcomePanel.removeAll();
        graphicPanel.removeAll();
        if (obs instanceof GameMapModel) {
            this.gameMapModel = (GameMapModel) obs;
        } else if (obs instanceof PlayerModel) {
            this.playerModel = (PlayerModel) obs;
        } else if (obs instanceof GamePlayModel) {
            this.gamePlayModel = (GamePlayModel) obs;
            this.gameMapModel = this.gamePlayModel.getGameMap();
        }
        this.updateWindow(this.gamePlayModel, this.playerModel);
        this.revalidate();
        this.repaint();

    }

    /**
     * This method convert string to color
     *
     * @param value
     * @return
     */
    public static Color stringToColor(final String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            try {
                final Field f = Color.class.getField(value);

                return (Color) f.get(null);
            } catch (Exception ce) {
                return Color.black;
            }
        }
    }

}