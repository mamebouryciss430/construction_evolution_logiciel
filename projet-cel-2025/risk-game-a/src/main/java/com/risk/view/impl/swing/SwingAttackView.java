package com.risk.view.impl.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultCaret;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.view.IAttackView;

/**
 * This View provides the attackView of the Game Play. It also provides the
 * observer pattern when the data is modified
 *
 * @author Karanbir Pannu
 *
 */

public class SwingAttackView extends AbstractSwingView implements IAttackView, ItemListener {

    /** The game map model. */
    private GameMapModel gameMapModel;

    /** The player model. */
    private PlayerModel playerModel;

    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /** The welcome panel. */
    private JPanel welcomePanel;

    /** The graphic panel. */
    private JPanel graphicPanel;

    /** The console main panel. */
    private JPanel consoleMainPanel;

    /** The console panel. */
    private JScrollPane consolePanel;

    /** The console text area. */
    private JTextArea consoleTextArea;

    /** The welcome label. */
    private JLabel welcomeLabel;

    /** The attack country list label. */
    private JLabel attackCountryListLabel;

    /** The defend country list label. */
    private JLabel defendCountryListLabel;

    /** The no of troops label. */
    private JLabel noOfTroopsLabel;

    /** The no of dice attack label. */
    private JLabel noOfDiceAttackLabel;

    /** The no of dice defend label. */
    private JLabel noOfDiceDefendLabel;

    /** The defected country label. */
    private JLabel defectedCountryLabel;

    /** The next button. */
    private JButton nextButton;

    /** The save button. */
    private JButton saveButton;

    /** The move button. */
    private JButton moveButton;

    /** The Single button. */
    private JButton SingleButton;

    /** The allout button. */
    private JButton alloutButton;

    /** The attack country list combo box. */
    private JComboBox<Object> attackCountryListComboBox;

    /** The defend country list combo box. */
    private JComboBox<Object> defendCountryListComboBox;

    /** The attack country list array. */
    private Object[] attackCountryListArray;

    /** The defend country list array. */
    private Object[] defendCountryListArray;

    /** The countries view renderer. */
    private CountryViewRenderer countriesViewRenderer;

    /** The button. */
    private JButton[] button;

    /** The num of dice attack combo box. */
    private JComboBox<Integer> numOfDiceAttackComboBox;

    /** The num of dice defend combo box. */
    private JComboBox<Integer> numOfDiceDefendComboBox;

    /** The num of armies to be moved combo box. */
    private JComboBox<Integer> numOfArmiesToBeMovedComboBox;

    /**
     * Instantiates a new attack view.
     *
     * @param gamePlayModel the game play model
     */
    public SwingAttackView(GamePlayModel gamePlayModel) {
        this.gameMapModel = gamePlayModel.getGameMap();
        this.setTitle("Attack Phase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(1600, 840);
        this.setResizable(false);
        this.setVisible(false);
        welcomePanel = new JPanel();
        graphicPanel = new JPanel();
        this.add(graphicPanel);
        graphicPanel.setSize(1200, 650);
        graphicPanel.setBackground(Color.WHITE);

        this.consoleMainPanel = new JPanel();
        this.consoleMainPanel.setBorder(new BevelBorder(1));
        this.consoleTextArea = new JTextArea("Attack Phase !!!\n", 10, 500);
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

        this.nextButton = new JButton("Next");
        this.nextButton.addActionListener(e -> fireViewEvent(IAttackView.ACTION_NEXT));

        this.saveButton = new JButton("Save Game");
        this.saveButton.addActionListener(e -> fireViewEvent(IAttackView.ACTION_SAVE));

        this.moveButton = new JButton("Move");
        this.moveButton.addActionListener(e -> fireViewEvent(IAttackView.ACTION_MOVE));

        this.SingleButton = new JButton("Single attack");
        this.SingleButton.addActionListener(e -> fireViewEvent(IAttackView.ACTION_SINGLE));

        this.alloutButton = new JButton("All Out");
        this.alloutButton.addActionListener(e -> fireViewEvent(IAttackView.ACTION_ALLOUT));

        this.add(welcomePanel);

        this.playerModel = this.gameMapModel.getPlayerTurn();
        this.welcomeLabel = new JLabel("It's " + playerModel.getName() + "'s turn");

        welcomePanel.setLayout(null);
        graphicPanel.setLayout(null);

        updateWindow(gamePlayModel, playerModel);
    }

    /**
     * This updateWindow method is called whenever the model is updated. It updates
     * the Screen for attack Phase
     *
     * @param gamePlayModel the game play model
     * @param playerModel   the player model
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

        this.attackCountryListLabel = new JLabel("Select attacker country:");
        attackCountryListLabel.setBounds(1300, 140, 150, 25);
        welcomePanel.add(attackCountryListLabel);

        // attack country comboBox
        ArrayList<CountryModel> attackListOfCountries = new ArrayList<CountryModel>();
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (playerModel.getName().equals(this.gameMapModel.getCountries().get(i).getRulerName())) {
                if (this.gameMapModel.getCountries().get(i).getArmies() > 1) {
                    attackListOfCountries.add(this.gameMapModel.getCountries().get(i));
                }
            }
        }

        countriesViewRenderer = new CountryViewRenderer();
        attackCountryListArray = attackListOfCountries.toArray();

        if (attackListOfCountries != null && !(attackListOfCountries.isEmpty())) {
            attackCountryListComboBox = new JComboBox(attackCountryListArray);
            if (attackListOfCountries.size() > gamePlayModel.getSelectedAttackComboBoxIndex()) {
                attackCountryListComboBox.setSelectedIndex(gamePlayModel.getSelectedAttackComboBoxIndex());
            } else {
                attackCountryListComboBox.setSelectedIndex(0);
            }
        }

        welcomePanel.add(this.attackCountryListComboBox);

        if (attackCountryListArray.length > 0) {
            attackCountryListComboBox.setRenderer(countriesViewRenderer);
        }
        attackCountryListComboBox.setBounds(1300, 180, 150, 25);

        this.noOfDiceAttackLabel = new JLabel("Number of Dice to Roll(for attack) :");
        noOfDiceAttackLabel.setBounds(1300, 215, 200, 35);
        welcomePanel.add(noOfDiceAttackLabel);

        CountryModel attackCountry = (CountryModel) this.attackCountryListComboBox.getSelectedItem();
        System.out.print("country name " + attackCountry.getCountryName());
        Integer[] attackTroops = new Integer[3];
        if (attackListOfCountries != null && !(attackListOfCountries.isEmpty())) {
            if (attackCountry.getArmies() > 3) {
                for (int i = 0; i < 3; i++) {
                    attackTroops[i] = i + 1;
                    System.out.println("i " + i);
                }
            } else {
                for (int i = 0; i < (attackCountry.getArmies() - 1); i++) {
                    attackTroops[i] = i + 1;
                    System.out.println("i " + i);
                }
            }
        }

        numOfDiceAttackComboBox = new JComboBox(attackTroops);
        numOfDiceAttackComboBox.setBounds(1300, 250, 150, 25);
        welcomePanel.add(numOfDiceAttackComboBox);

        this.defendCountryListLabel = new JLabel("Select defend Country :");
        defendCountryListLabel.setBounds(1300, 280, 150, 25);
        welcomePanel.add(this.defendCountryListLabel);

        // defend country comboBox
        ArrayList<CountryModel> defendListOfCountries = new ArrayList<CountryModel>();
        defendListOfCountries = gamePlayModel.getDefendCountryList(attackCountry);

        countriesViewRenderer = new CountryViewRenderer();
        defendCountryListArray = defendListOfCountries.toArray();

        defendCountryListComboBox = new JComboBox(defendCountryListArray);
        System.out.println("defendListOfCountries " + defendListOfCountries.size());
        if (defendListOfCountries != null && !(defendListOfCountries.isEmpty())) {
            if (defendListOfCountries.size() > gamePlayModel.getSelectedDefendComboBoxIndex()) {
                defendCountryListComboBox.setSelectedIndex(gamePlayModel.getSelectedDefendComboBoxIndex());
            } else {
                defendCountryListComboBox.setSelectedIndex(0);
            }
        }

        welcomePanel.add(this.defendCountryListComboBox);

        if (defendCountryListArray.length > 0) {
            defendCountryListComboBox.setRenderer(countriesViewRenderer);
        }
        defendCountryListComboBox.setBounds(1300, 310, 150, 25);

        this.noOfDiceDefendLabel = new JLabel("Number of Dice to Roll(for defend) :");
        noOfDiceDefendLabel.setBounds(1300, 350, 200, 45);
        welcomePanel.add(noOfDiceDefendLabel);

        Integer[] defendTroops = new Integer[2];

        if (defendListOfCountries != null && !(defendListOfCountries.isEmpty())) {
            CountryModel defendCountry = (CountryModel) this.defendCountryListComboBox.getSelectedItem();
            if (defendCountry.getArmies() > 2) {
                for (int i = 0; i < 2; i++) {
                    defendTroops[i] = i + 1;
                    System.out.println("i " + i);
                }
            } else {
                for (int i = 0; i < defendCountry.getArmies(); i++) {
                    defendTroops[i] = i + 1;
                    System.out.println("i " + i);
                }
            }
        }

        numOfDiceDefendComboBox = new JComboBox(defendTroops);
        numOfDiceDefendComboBox.setBounds(1300, 380, 150, 25);
        welcomePanel.add(numOfDiceDefendComboBox);

        this.SingleButton.setBounds(1210, 450, 150, 25);
        welcomePanel.add(this.SingleButton);

        this.alloutButton.setBounds(1375, 450, 150, 25);
        welcomePanel.add(this.alloutButton);

        if (gamePlayModel.getArmyToMoveText() == false) {
            this.SingleButton.setEnabled(true);
            this.alloutButton.setEnabled(true);
            this.attackCountryListComboBox.setEnabled(true);
        }

        // move troop after conquer
        if (gamePlayModel.getArmyToMoveText() == true) {
            this.attackCountryListComboBox.setEnabled(false);
            this.SingleButton.setEnabled(false);
            this.alloutButton.setEnabled(false);
            this.defectedCountryLabel = new JLabel("Move troops to defeated country :");
            defectedCountryLabel.setBounds(1300, 480, 200, 45);
            welcomePanel.add(defectedCountryLabel);

            Integer[] moveTroops = new Integer[attackCountry.getArmies()];
            for (int i = 0; i < (attackCountry.getArmies() - 1); i++) {
                moveTroops[i] = i + 1;
            }

            numOfArmiesToBeMovedComboBox = new JComboBox(moveTroops);
            numOfArmiesToBeMovedComboBox.setBounds(1300, 510, 150, 25);
            welcomePanel.add(numOfArmiesToBeMovedComboBox);

            this.moveButton.setBounds(1300, 540, 150, 25);
            welcomePanel.add(this.moveButton);
        }

        this.saveButton.setBounds(1300, 575, 150, 25);
        welcomePanel.add(this.saveButton);

        this.nextButton.setBounds(1300, 610, 150, 25);
        welcomePanel.add(this.nextButton);

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
            Border border = BorderFactory.createLineBorder(pm.getColor(), 3);

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

        if (attackCountryListComboBox != null) {
            this.attackCountryListComboBox.addActionListener(e -> fireViewEvent(IAttackView.ACTION_ATTACK_COUNTRY_CHANGED));
        }

        if (defendCountryListComboBox != null) {
            this.defendCountryListComboBox.addActionListener(e -> fireViewEvent(IAttackView.ACTION_DEFEND_COUNTRY_CHANGED));
        }

        this.setItemListener(this);
    }

    /**
     * Countries are rendered as button and linked with Swing using Graphics.
     *
     * @param g the g
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

    /**
     * Getter method that provides us a map model corresponding to a map name.
     */

    public class CountryViewRenderer extends BasicComboBoxRenderer {

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.swing.plaf.basic.BasicComboBoxRenderer#getListCellRendererComponent(
         * javax.swing.JList, java.lang.Object, int, boolean, boolean)
         */
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
     * @param obs the obs
     * @param arg the arg
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
     * This method convert string to color.
     *
     * @param value the value
     * @return the color
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

    /**
     * Sets the item listener.
     *
     * @param itemListener the new item listener
     */
    private void setItemListener(ItemListener itemListener) {
        this.attackCountryListComboBox.addItemListener(itemListener);
        this.defendCountryListComboBox.addItemListener(itemListener);
    }

    /**
     * Item Listener.
     *
     * @param itemEvent the item event
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getSource().equals(this.attackCountryListComboBox)) {
            this.gamePlayModel.setSelectedAttackComboBoxIndex(this.attackCountryListComboBox.getSelectedIndex());
        } else if (itemEvent.getSource().equals(this.defendCountryListComboBox)) {
            this.gamePlayModel.setSelectedDefendComboBoxIndex(this.defendCountryListComboBox.getSelectedIndex());
        }

    }

    @Override
    public int getAttackCountryIndex() {
        return attackCountryListComboBox.getSelectedIndex();
    }

    @Override
    public int getDefendCountryIndex() {
        return this.defendCountryListComboBox.getSelectedIndex();
    }

    @Override
    public int getNumOfDiceAttack() {
        return (int) numOfDiceAttackComboBox.getSelectedItem();
    }

    @Override
    public int getNumOfDiceDefend() {
        return (int) numOfDiceDefendComboBox.getSelectedItem();
    }

    @Override
    public CountryModel getAttackCountryModel() {
        return (CountryModel) attackCountryListComboBox.getSelectedItem();
    }

    @Override
    public CountryModel getDefendCountryModel() {
        return (CountryModel) defendCountryListComboBox.getSelectedItem();
    }

    @Override
    public int getNumOfArmiesToBeMoved() {
        return (int) numOfArmiesToBeMovedComboBox.getSelectedItem();
    }
}