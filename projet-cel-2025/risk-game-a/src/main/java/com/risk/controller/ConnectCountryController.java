package com.risk.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import com.risk.Environment;
import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.utilities.Validation;
import com.risk.utilities.WriteMap;
import com.risk.view.IConnectCountryView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;
import com.risk.view.events.ViewListSelectionEvent;
import com.risk.view.events.ViewListSelectionListener;

/**
 * In ConnectCountryController, the data flow into model object and updates the
 * view whenever data changes.
 *
 * @version 1.0.0
 *
 */

public class ConnectCountryController implements ViewActionListener, ViewListSelectionListener {

    /** The game map model. */
    private GameMapModel gameMapModel;

    /** The connect country view. */
    private IConnectCountryView connectCountryView;



    /**to solve duplication problem*/
    private static final String Invalid = "Invalid";

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param mapModel the map model
     */
    public ConnectCountryController(GameMapModel mapModel) {

        this.gameMapModel = mapModel;

        this.connectCountryView =
                Environment.getInstance().getViewManager().newConnectCountryView(this.gameMapModel);

        this.connectCountryView.addActionListener(this);
        this.connectCountryView.addListSelectionListener(this);
        this.connectCountryView.showView();
        this.gameMapModel.addObserver(this.connectCountryView);
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {
        Object source = event.getSource();
        if (IConnectCountryView.ACTION_ADD.equals(source)){
            handleActionAdd();
        } else if (IConnectCountryView.ACTION_SAVE.equals(source)) {
            handleActionSave();
        } else if (IConnectCountryView.ACTION_REMOVE.equals(source)) {
            handleActionRemove();
        } else {
            System.out.println("All continents are not linked");
            showError("One of the continent is invalid");
        }
    }

    private void handleActionAdd(){
        CountryModel left = connectCountryView.getCountryParentLeft();
        CountryModel right = connectCountryView.getCountryParentRight();
        if (left.equals(right)){
            showError("Cannot create a self link");
        }
        this.gameMapModel.setNeighbouringCountry(left,right);

    }
    private void handleActionSave(){
        Validation mapValidation = new Validation();
        boolean hasEmptyLink = mapValidation.emptyLinkCountryValidation(this.gameMapModel);

        boolean hasEmptyContinent = mapValidation.emptyContinentValidation(this.gameMapModel);
        boolean continentsInterlinked = mapValidation.checkInterlinkedContinent(this.gameMapModel);
        System.out.println(hasEmptyLink + " " + continentsInterlinked + " " +hasEmptyContinent );

        if (hasEmptyLink){
            showError("Empty link country validation failed");
            return ;
        }
        if (continentsInterlinked){
            showError("Check interlinked continent validation failed");
        }
        if(hasEmptyContinent){
            showError("Empty continent validation failed");
        }
        System.out.println("All the map validations are correct");
        saveMapToFile();
    }
    private void saveMapToFile(){
        String filename = JOptionPane.showInputDialog("File Name");
        try {
            System.out.println(filename);
            WriteMap tempWrite = new WriteMap();
            tempWrite.writeMapToFile(filename, this.gameMapModel);
            JOptionPane.showMessageDialog(null, "Map has been created select it before you play");
            new WelcomeScreenController();
            this.connectCountryView.hideView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private  void handleActionRemove(){
        CountryModel left = connectCountryView.getCountryParentLeft();
        CountryModel right = connectCountryView.getCountryParentRight();
        this.gameMapModel.removeNeighbouringCountry(left,right);
    }
    private void showError (String message){
        System.out.println(message);
        JOptionPane.showOptionDialog(null, message, Invalid, JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null);
    }


    /**
     * Check for the List is changed.
     *
     * @param e the e
     * @see ViewListSelectionListener
     */
    @Override
    public void valueChanged(ViewListSelectionEvent e) {
        ListSelectionModel lsm = e.getListSelectionModel();

        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting();
        System.out.println("Event for indexes " + firstIndex + " - " + lastIndex + "; isAdjusting is " + isAdjusting
                + "; selected indexes:");

        if (lsm.isSelectionEmpty()) {
            System.out.println(" <none>");
        } else {
            // Find out which indexes are selected.
            int minRightIndex = lsm.getMinSelectionIndex();
            int maxRightIndex = lsm.getMaxSelectionIndex();
            int finalRightModelIndex = 0;
            for (int i = minRightIndex; i <= maxRightIndex; i++) {
                if (this.connectCountryView.isSelectedLeftModelIndex(i)) {
                    finalRightModelIndex = i;
                }
            }
            System.out.println(finalRightModelIndex);
        }

        if (IConnectCountryView.SELECTED_CHANGED_LEFT.equals(e.getSource())) {
            this.gameMapModel.setColorToCountry(
                    this.connectCountryView.getCountryParentLeft(), Color.GREEN);
        } else if (IConnectCountryView.SELECTED_CHANGED_RIGHT.equals(e.getSource())) {
            this.gameMapModel.setColorToCountry(
                    this.connectCountryView.getCountryParentRight(), Color.YELLOW);
        }
    }
}
