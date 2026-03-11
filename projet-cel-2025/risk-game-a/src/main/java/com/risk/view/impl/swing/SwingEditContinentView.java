package com.risk.view.impl.swing;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.view.IEditContinentView;
import com.risk.view.View;

/**
 * "SwingEditContinentView" represents a view object for editing a continent It
 * contains Labels, text fields, and buttons
 *
 * @author Shreyans
 *
 */
public class SwingEditContinentView extends AbstractSwingView implements IEditContinentView {

    private JLabel welcomeLabel;
    private JLabel continentListText;
    private JComboBox continentListCombobox;
    private Object[] continentListArray;
    private ContinentViewRenderer continentViewRenderer;
    private JLabel controlValueText;
    private JTextField controlValue;
    private JButton saveButton;
    private JButton addButton;
    private JPanel welcomePanel;

    /**
     * Constructor of SwingEditContinentView
     *
     * @param listOfContinents
     */
    public SwingEditContinentView(List<ContinentsModel> listOfContinents) {

        welcomeLabel = new JLabel("Please select the Continents you want in the map and the control value");

        continentListText = new JLabel("Continent");
        controlValueText = new JLabel("Control Value");
        controlValue = new JTextField();

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> fireViewEvent(IEditContinentView.ACTION_SAVE));

        addButton = new JButton("Add");
        addButton.addActionListener(e -> fireViewEvent(IEditContinentView.ACTION_ADD));

        welcomePanel = new JPanel();

        this.setName("RISK GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(800, 700);
        this.setResizable(false);
        this.setVisible(false);
        welcomePanel.setLayout(null);
        this.add(welcomePanel);
        this.updateWindow(listOfContinents);

    }

    /**
     * Inner Class Continent View Renderer class that is used to generate a dynamic
     * combobox
     */
    public class ContinentViewRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            ContinentsModel map_model = (ContinentsModel) value;
            if (map_model != null)
                setText(map_model.getContinentName());

            return this;
        }
    }

    /**
     *
     */
    /**
     * Update the view based on observer
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable obs, Object obj) {
        List<ContinentsModel> listOfContinentModel = ((GameMapModel) obs).getContinents();
        this.updateWindow(listOfContinentModel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Updating window components
     *
     * @param listOfContinentModel
     */
    private void updateWindow(List<ContinentsModel> listOfContinentModel) {
        welcomePanel.removeAll();
        Font largeFont = new Font("Serif", Font.BOLD, 18);
        Font mediumFont = new Font("Serif", Font.BOLD, 14);
        Font smallFont = new Font("Serif", Font.BOLD, 12);

        welcomeLabel.setFont(largeFont);
        welcomePanel.add(welcomeLabel);
        welcomeLabel.setBounds(100, 0, 600, 200);

        continentListText.setFont(mediumFont);
        welcomePanel.add(continentListText);
        continentListText.setBounds(100, 50, 500, 200);

        controlValueText.setFont(mediumFont);
        welcomePanel.add(controlValueText);
        controlValueText.setBounds(100, 150, 200, 100);

        continentViewRenderer = new ContinentViewRenderer();
        continentListArray = listOfContinentModel.toArray();
        continentListCombobox = new JComboBox(continentListArray);

        if (continentListArray.length > 0) {
            continentListCombobox.setRenderer(continentViewRenderer);
        }
        continentListCombobox.setBounds(200, 140, 100, 20);
        welcomePanel.add(continentListCombobox);

        controlValue.setFont(mediumFont);
        welcomePanel.add(controlValue);
        controlValue.setBounds(200, 195, 100, 20);

        addButton.setFont(smallFont);
        welcomePanel.add(addButton);
        addButton.setBounds(100, 250, 100, 20);

        saveButton.setFont(smallFont);
        welcomePanel.add(saveButton);
        saveButton.setBounds(200, 250, 100, 20);
    }

    @Override
    public String getControlValue() {
        return this.controlValue.getText();
    }

    @Override
    public ContinentsModel getContinentsModel() {
        return (ContinentsModel) this.continentListCombobox.getSelectedItem();
    }
}