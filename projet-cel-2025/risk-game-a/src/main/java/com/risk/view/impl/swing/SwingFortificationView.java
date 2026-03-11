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
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.text.DefaultCaret;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.view.IFortificationView;

/**
 * "SwingFortificationView" class represents a view object of fortification phase.
 *
 * @author Namita
 *
 */
public class SwingFortificationView extends AbstractSwingView implements IFortificationView {

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
    private JButton moveButton;
    private JButton saveButton;
    private JButton nextButton;

    private JLabel fromCountryListLabel;
    private JLabel toCountryListLabel;
    private JComboBox<Object> fromCountryListComboBox;
    private JComboBox<Object> toCountryListComboBox;
    private Object[] fromCountryListArray;
    private CountryViewRenderer fromCountriesViewRenderer;
    private Object[] toCountryListArray;

    /** The to countries view renderer. */
    private CountryViewRenderer toCountriesViewRenderer;

    private JButton[] button;


    /**
     * Constructor of SwingFortificationView
     *
     * @param gamePlayModel
     */
    SwingFortificationView(GamePlayModel gamePlayModel) {
        this.gameMapModel = gamePlayModel.getGameMap();
        this.setTitle("Fortification Phase");
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
        graphicPanel.setLayout(null);

        this.consoleMainPanel = new JPanel();
        this.consoleMainPanel.setBorder(new BevelBorder(1));
        this.consoleTextArea = new JTextArea("Fortification Phase!!!\n", 10, 500);
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

        this.add(welcomePanel);
        this.playerModel = this.gameMapModel.getPlayerTurn();
        this.moveButton = new JButton("Move");
        this.moveButton.addActionListener(e -> fireViewEvent(IFortificationView.ACTION_MOVE));

        this.saveButton = new JButton("Save Game");
        this.saveButton.addActionListener(e -> fireViewEvent(IFortificationView.ACTION_SAVE));

        this.nextButton = new JButton("Next");
        this.nextButton.addActionListener(e -> fireViewEvent(IFortificationView.ACTION_NEXT));

        updateWindow(gamePlayModel, this.playerModel);
        welcomePanel.setLayout(null);
        graphicPanel.setLayout(null);
    }

    /**
     * Updates the window based on new data in fortification phase
     *
     * @param gamePlayModel
     * @param playerModel
     */
    public void updateWindow(GamePlayModel gamePlayModel, PlayerModel playerModel) {

        welcomePanel.removeAll();
        graphicPanel.removeAll();
        Font largeFont = new Font("Serif", Font.BOLD, 18);
        Font mediumFont = new Font("Serif", Font.BOLD, 14);
        Font smallFont = new Font("Serif", Font.BOLD, 12);

        this.gamePlayModel = gamePlayModel;
        this.gameMapModel = gamePlayModel.getGameMap();
        this.playerModel = playerModel;

        // from country comboBox
        ArrayList<CountryModel> fromListOfCountries = new ArrayList<CountryModel>();
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (playerModel.getName().equals(this.gameMapModel.getCountries().get(i).getRulerName())
                    && this.gameMapModel.getCountries().get(i).getArmies() >= 2) {
                fromListOfCountries.add(this.gameMapModel.getCountries().get(i));
            }
        }
        fromCountriesViewRenderer = new CountryViewRenderer();
        fromCountryListArray = fromListOfCountries.toArray();

        fromCountryListComboBox = new JComboBox<>(fromCountryListArray);
        if (fromListOfCountries.size() > 0) {

            this.welcomeLabel = new JLabel("It's " + playerModel.getName() + "'s turn");
            welcomeLabel.setBounds(1300, 80, 300, 25);
            welcomeLabel.setFont(largeFont);
            welcomePanel.add(welcomeLabel);

            if (this.gamePlayModel.getConsoleText() != null) {
                this.consoleTextArea.setText(this.gamePlayModel.getConsoleText().toString());
            }

            this.fromCountryListLabel = new JLabel("From Country :");
            fromCountryListLabel.setBounds(1300, 120, 150, 25);
            welcomePanel.add(this.fromCountryListLabel);

            fromCountryListComboBox.setSelectedIndex(gamePlayModel.getSelectedComboBoxIndex());
            welcomePanel.add(this.fromCountryListComboBox);

            this.toCountryListLabel = new JLabel("To Country :");
            toCountryListLabel.setBounds(1300, 240, 150, 25);
            welcomePanel.add(this.toCountryListLabel);

            // to country comboBox
            ArrayList<CountryModel> toListOfCountries = new ArrayList<CountryModel>();
            for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
                if (playerModel.getName().equals(this.gameMapModel.getCountries().get(i).getRulerName())) {
                    toListOfCountries.add(this.gameMapModel.getCountries().get(i));
                }
            }
            toCountriesViewRenderer = new CountryViewRenderer();
            toCountryListArray = toListOfCountries.toArray();
            toCountryListComboBox = new JComboBox(toCountryListArray);
            welcomePanel.add(this.toCountryListComboBox);

            if (toCountryListArray.length > 0) {
                toCountryListComboBox.setRenderer(toCountriesViewRenderer);
            }
            toCountryListComboBox.setBounds(1300, 270, 150, 25);
            welcomePanel.add(toCountryListComboBox);

            this.noOfTroopsLabel = new JLabel("Number of Troops :");
            noOfTroopsLabel.setBounds(1300, 180, 150, 25);
            welcomePanel.add(noOfTroopsLabel);

            CountryModel countryName = (CountryModel) this.fromCountryListComboBox.getSelectedItem();
            System.out.println("country name " + countryName.getArmies());
            Integer[] troops = new Integer[countryName.getArmies() - 1];
            for (int i = 0; i < (countryName.getArmies() - 1); i++) {
                troops[i] = i + 1;
            }

            numOfTroopsComboBox = new JComboBox(troops);
            numOfTroopsComboBox.setBounds(1300, 210, 150, 25);
            welcomePanel.add(numOfTroopsComboBox);

            if (fromCountryListArray.length > 0) {
                fromCountryListComboBox.setRenderer(fromCountriesViewRenderer);
            }
            fromCountryListComboBox.setBounds(1300, 150, 150, 25);
            welcomePanel.add(fromCountryListComboBox);

            this.moveButton.setBounds(1300, 330, 150, 25);
            welcomePanel.add(this.moveButton);
        } else {

            this.welcomeLabel = new JLabel("There is no valid armies to move");
            welcomeLabel.setBounds(1300, 80, 300, 25);
            welcomeLabel.setFont(mediumFont);
            welcomePanel.add(welcomeLabel);

            this.nextButton.setBounds(1300, 400, 150, 25);
            welcomePanel.add(this.nextButton);
        }

        this.saveButton.setBounds(1300, 500, 150, 25);
        welcomePanel.add(this.saveButton);

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

        this.fromCountryListComboBox.addActionListener(e -> fireViewEvent(IFortificationView.ACTION_FROM_COUNTRY_CHANGED));
        this.fromCountryListComboBox.addItemListener(e -> fireViewEvent(IFortificationView.ACTION_ITEM_FROM_COUNTRY_CHANGED));
    }

    /**
     * Paint the button and links in the panel
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
    public CountryModel getFromCountryModel() {
        return (CountryModel) this.fromCountryListComboBox.getSelectedItem();
    }

    @Override
    public CountryModel getToCountryModel() {
        return (CountryModel) this.toCountryListComboBox.getSelectedItem();
    }

    @Override
    public int getNumOfTroops() {
        return (Integer) this.numOfTroopsComboBox.getSelectedItem();
    }

    @Override
    public int getFromCountryIndex() {
        return this.fromCountryListComboBox.getSelectedIndex();
    }

    /**
     * Provides a dynamic comboBox of country names
     *
     * @author GROUPE-35
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
     *
     */
    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof GamePlayModel) {
            this.gamePlayModel = (GamePlayModel) obs;
        } else if (obs instanceof GamePlayModel) {
            this.gameMapModel = (GameMapModel) obs;
        } else if (obs instanceof GamePlayModel) {
            this.playerModel = (PlayerModel) obs;
        }
        this.updateWindow(this.gamePlayModel, this.playerModel);
        this.revalidate();
        this.repaint();

    }

    public static Color stringToColor(final String value) {
        if (value == null) {
            return Color.black;
        }
        try {
            // get color by hex or octal value
            return Color.decode(value);
        } catch (NumberFormatException nfe) {
            // if we can't decode lets try to get it by name
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(value);

                return (Color) f.get(null);
            } catch (Exception ce) {
                // if we can't get any color return black
                return Color.black;
            }
        }
    }
}