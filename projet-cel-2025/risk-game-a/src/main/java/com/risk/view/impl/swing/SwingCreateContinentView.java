package com.risk.view.impl.swing;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.view.ICreateContinentView;

/**
 * "SwingCreateContinentView" class represents a view object for creating continent
 * Properties are containing labels, text fields, buttons, a pane, and a panel
 *
 * @author KaranbirPannu
 */

public class SwingCreateContinentView extends AbstractSwingView implements ICreateContinentView {

    /**
     * Properties of view
     */
    private JLabel welcomeLabel;
    private JTextField continentValue;
    private JTextField controlValue;
    private JLabel continentListText;
    private JLabel controlValueText;
    private JLabel controlValueInfoText;
    private JTextArea observerList;
    private JButton nextButton;
    private JButton addButton;
    private JTextArea consoleTextArea;
    private JTextArea consoleMainPanel;
    private JScrollPane consolePanel;
    private JPanel mainPanel;
    private JTextArea textArea;

    /**
     * Construction of SwingCreateContinentView
     */
    public SwingCreateContinentView() {
        this.setTitle("Create Continent");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(800, 700);
        this.setResizable(false);
        this.setVisible(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        textArea = new JTextArea("Default text", 5, 5);

        welcomeLabel = new JLabel("Please name the Continents you want in the map and their control values");
        welcomeLabel.setBounds(100, 0, 600, 100);

        continentListText = new JLabel("Continent Name: ");
        continentListText.setBounds(100, 100, 200, 40);

        continentValue = new JTextField();
        continentValue.setBounds(200, 100, 200, 40);

        controlValueText = new JLabel("Control Value: ");
        controlValueText.setBounds(100, 200, 200, 40);

        controlValue = new JTextField();
        controlValue.setBounds(200, 200, 200, 40);

        controlValueInfoText = new JLabel("(0 to 10)");
        controlValueInfoText.setBounds(411, 200, 100, 40);

        addButton = new JButton("Add");
        this.addButton.addActionListener(e -> fireViewEvent(ICreateContinentView.ACTION_ADD));
        addButton.setBounds(100, 300, 100, 40);

        nextButton = new JButton("Next");
        this.nextButton.addActionListener(e -> fireViewEvent(ICreateContinentView.ACTION_NEXT));
        nextButton.setBounds(200, 300, 100, 40);

        updateScreen(null);
    }

    /**
     * Updates view regarding continents belong to
     *
     * @param listOfContinentModel
     */
    private void updateScreen(List<ContinentsModel> listOfContinentModel) {

        StringBuilder textAreaText = new StringBuilder("------------------------------------------------");

        if (listOfContinentModel == null) {
            textArea.setText(textAreaText.toString());
        } else {
            textAreaText.setLength(0);
            for (int i = 0; i < listOfContinentModel.size(); i++) {
                textAreaText.append("Continent name : " + listOfContinentModel.get(i).getContinentName()
                        + " ,Control Value : " + listOfContinentModel.get(i).getValueControl() + "\n");
            }
        }

        textArea.setText(textAreaText.toString());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        mainPanel.add(textArea);
        textArea.setBorder(new TitledBorder(new LineBorder(Color.black, 5), "Continents added list:"));
        textArea.setBounds(520, 0, 260, 650);

        Color main = new Color(230, 230, 255);
        Color secondary = new Color(0, 0, 26);
        textArea.setBackground(main);
        textArea.setForeground(secondary);

        this.add(mainPanel);
        mainPanel.add(welcomeLabel);
        mainPanel.add(addButton);
        mainPanel.add(nextButton);
        mainPanel.add(continentListText);
        mainPanel.add(continentValue);
        mainPanel.add(controlValue);
        mainPanel.add(controlValueText);
        mainPanel.add(controlValueInfoText);

    }

    /**
     * Update the view based on observer
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable obs, Object arg) {
        List<ContinentsModel> listOfContinentModel = ((GameMapModel) obs).getContinents();
        this.updateScreen(listOfContinentModel);
        this.revalidate();
        this.repaint();
    }

    @Override
    public String getControlValue() {
        return this.controlValue.getText();
    }

    @Override
    public String getContinentValue() {
        return this.continentValue.getText();
    }


}