package com.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.risk.view.impl.swing.SwingReinforcementView.CountryViewRenderer;

public class PlayConsoleView extends JFrame {

    public JPanel consoleMainPanel;
    public JScrollPane consolePanel;
    public JTextArea consoleTextArea;

    public JPanel welcomePanel;
    public JPanel graphicPanel;

    /*
     * public JPanel consoleMainPanel; public JScrollPane consolePanel; public
     * JTextArea consoleTextArea;
     */

    public JLabel welcomeLabel;
    public JLabel noOfTroopsLabel;

    public JComboBox<Integer> numOfTroopsComboBox;
    public JButton addButton;
    public JButton addMoreButton;
    public JButton exitCardButton;
    public JLabel listOfCountriesLabel;
    public JLabel reinnforcementCardLabel;
    public JTextField cardIdField;

    public JLabel countryListLabel;
    public JComboBox<Object> countryListComboBox;
    public Object[] countryListArray;

    /** The countries view renderer. */
    private CountryViewRenderer countriesViewRenderer;

    public JButton[] button;
    public ArrayList<Double> percentage;
    public ArrayList<Color> colors;

    public PlayConsoleView() {
        this.setTitle("Action Console");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(50, 50);
        this.setSize(750, 750);
        this.setResizable(true);
        this.setVisible(false);
        this.consoleMainPanel = new JPanel();
        this.consoleTextArea = new JTextArea("Life is a risk, instead play risk !!!\n", 700, 700);
        this.consoleTextArea.setEditable(false);
        this.consoleTextArea.setFocusable(false);
        this.consoleTextArea.setVisible(true);
        this.consoleTextArea.setForeground(Color.WHITE);
        this.consoleTextArea.setBackground(Color.BLACK);
        DefaultCaret caret = (DefaultCaret) this.consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        this.consolePanel = new JScrollPane(this.consoleTextArea);
        this.consolePanel.setPreferredSize(new Dimension(700, 700));
        this.consoleMainPanel.add(this.consolePanel, BorderLayout.WEST);
        this.getContentPane().add(this.consoleMainPanel, BorderLayout.SOUTH);
    }

    public void append(String message) {
        this.consoleTextArea.append("\n");
        this.consoleTextArea.append(message);
    }
}
