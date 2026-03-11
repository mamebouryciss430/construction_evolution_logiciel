package com.risk.view.impl.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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
import com.risk.view.IStartUpView;

/**
 * This class file is the view (observer) for Start Up Phase of Game Play
 *
 * @author Gursimran
 */

public class SwingStartUpView extends AbstractSwingView implements IStartUpView {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    public GameMapModel gameMapModel;
    public PlayerModel playerModel;
    public GamePlayModel gamePlayModel;

    public JPanel welcomePanel;
    public JPanel graphicPanel;

    public JPanel consoleMainPanel;
    public JScrollPane consolePanel;
    public JTextArea consoleTextArea;

    public JLabel welcomeLabel;
    public JLabel welcomeLabel1;
    public JLabel noOfTroopsLabel;

    public JComboBox<Integer> numOfTroopsComboBox;
    public JButton addButton;
    public JLabel listOfCountriesLabel;

    public JLabel countryListLabel;
    public JComboBox<Object> countryListComboBox;
    public Object[] countryListArray;

    /** The countries view renderer. */
    private CountryViewRenderer countriesViewRenderer;

    public JButton[] button;
    public JButton nextButton;
    public JButton button2;
    public JButton button3;

    /**
     * constructor for Start Up Phase View where the variables are initialized
     *
     * @param gamePlayModel
     * @param playerModel
     */
    public SwingStartUpView(GamePlayModel gamePlayModel, PlayerModel playerModel) {

        this.setTitle("Startup Phase");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(1600, 840);
        this.setResizable(false);
        this.setVisible(false);
        this.addButton = new JButton("Add");
        this.nextButton = new JButton("Next");
        welcomePanel = new JPanel();
        graphicPanel = new JPanel();
        this.add(graphicPanel);
        graphicPanel.setSize(1200, 650);
        graphicPanel.setBackground(Color.WHITE);
        graphicPanel.setLayout(null);

        this.consoleMainPanel = new JPanel();
        this.consoleMainPanel.setBorder(new BevelBorder(1));
        this.consoleTextArea = new JTextArea("Start up Phase !!!\n", 10, 500);
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
        this.add(welcomePanel);
        this.playerModel = playerModel;
        this.gamePlayModel = gamePlayModel;
        this.welcomeLabel = new JLabel("It's " + playerModel.getName() + "'s turn");
        this.welcomeLabel1 = new JLabel("Remaining Armies: " + playerModel.getremainTroop());
        updateWindow(gamePlayModel, playerModel);
        welcomePanel.setLayout(null);
        graphicPanel.setLayout(null);

        this.addButton.addActionListener(e -> fireViewEvent(IStartUpView.ACTION_ADD));
        this.nextButton.addActionListener(e -> fireViewEvent(IStartUpView.ACTION_NEXT));
    }

    /**
     * This updateWindow method is called whenever the model is updated. It updates
     * the Screen for Start Up Phase
     *
     * @param gamePlayModel
     * @param playerModel
     */
    public void updateWindow(GamePlayModel gamePlayModel, PlayerModel playerModel) {

        welcomePanel.removeAll();
        graphicPanel.removeAll();
        Font largeFont = new Font("Serif", Font.BOLD, 18);
        Font mediumFont = new Font("Serif", Font.BOLD, 14);
        Font smallFont = new Font("Serif", Font.BOLD, 8);

        this.gameMapModel = gamePlayModel.getGameMap();
        this.gamePlayModel = gamePlayModel;
        this.playerModel = playerModel;

        welcomeLabel.setBounds(1300, 80, 300, 25);
        welcomeLabel.setFont(largeFont);
        welcomePanel.add(welcomeLabel);

        if (this.gamePlayModel.getConsoleText() != null) {
            this.consoleTextArea.setText(this.gamePlayModel.getConsoleText().toString());
        }

        this.noOfTroopsLabel = new JLabel("Number of Troops :");
        noOfTroopsLabel.setBounds(1300, 140, 150, 25);
        welcomePanel.add(noOfTroopsLabel);

        Integer[] troops = new Integer[playerModel.getremainTroop()];
        for (int i = 0; i < playerModel.getremainTroop(); i++) {
            troops[i] = i + 1;
        }

        numOfTroopsComboBox = new JComboBox(troops);
        numOfTroopsComboBox.setBounds(1300, 170, 150, 25);
        numOfTroopsComboBox.setEnabled(false);
        welcomePanel.add(numOfTroopsComboBox);

        welcomeLabel1 = new JLabel("Remaining Armies: " + playerModel.getremainTroop());
        welcomeLabel1.setBounds(1450, 170, 300, 25);
        welcomeLabel1.setFont(smallFont);
        welcomePanel.add(welcomeLabel1);

        this.countryListLabel = new JLabel("Select Country :");
        countryListLabel.setBounds(1300, 230, 150, 25);
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
        countryListComboBox.setBounds(1300, 260, 150, 25);
        welcomePanel.add(countryListComboBox);

        this.addButton.setBounds(1300, 300, 150, 25);
        welcomePanel.add(this.addButton);

        this.nextButton.setBounds(1400, 600, 150, 25);
        welcomePanel.add(this.nextButton);

        int n = this.gameMapModel.getCountries().size();
        button = new JButton[n];
        for (int i = 0; i < n; i++) {
            CountryModel country = this.gameMapModel.getCountries().get(i);

            country.setBackground(this.gameMapModel.getCountries().get(i).getBackgroundColor());
            country.setText(this.gameMapModel.getCountries().get(i).getCountryName().substring(0, 3));
            country.setToolTipText("Troops: " + this.gameMapModel.getCountries().get(i).getArmies());
            country.setFont(smallFont);
            PlayerModel pm = this.gamePlayModel.getPlayer(country);
            Border border = BorderFactory.createLineBorder(pm.getColor(), 3);

            country.setBorder(border);

            country.setOpaque(true);

            if (this.gameMapModel.getContinents().get(0).getContinentName().equals("clifftop")
                    || this.gameMapModel.getContinents().get(0).getContinentName().equals("North America")) {
                country.setBounds(this.gameMapModel.getCountries().get(i).getXPosition(),
                        this.gameMapModel.getCountries().get(i).getYPosition(), 50, 50);
            } else {
                country.setBounds(this.gameMapModel.getCountries().get(i).getXPosition() * 2,
                        this.gameMapModel.getCountries().get(i).getYPosition() * 2, 50, 50);
            }

            country.setMargin(new Insets(0, 0, 0, 0));

            graphicPanel.add(country);
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
    public void setWelcomeLabel(String label) {
        welcomeLabel.setText(label);
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

        if (obs instanceof GameMapModel) {
            this.gameMapModel = (GameMapModel) obs;
        } else if (obs instanceof PlayerModel) {
            this.playerModel = (PlayerModel) obs;
        }
        this.updateWindow(this.gamePlayModel, this.playerModel);
        this.revalidate();
        this.repaint();

    }

    /**
     * This method convert string to color
     *
     * @param value
     * @return color
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