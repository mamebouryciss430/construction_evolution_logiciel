package com.risk.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import javax.swing.filechooser.FileSystemView;

import com.risk.Environment;
import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.utilities.ReadFile;
import com.risk.view.IEditContinentView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;

/**
 * In EditContinentController, the data flow into model object and updates the
 * view whenever data changes.
 *
 * @version 1.0.0
 *
 */

public class EditContinentController implements ViewActionListener {

    /**
     * The edit continent view.
     */
    private IEditContinentView editContinentView;

    /**
     * The map model.
     */
    private GameMapModel mapModel;

    /**
     * The continent list.
     */
    private List<ContinentsModel> continentList;

    /**
     * The temp read.
     */
    private ReadFile tempRead = new ReadFile();

    /**
     * The file.
     */
    private File file;

    /**
     * The new continent model.
     */
    private ContinentsModel newContinentModel;

    /**
     * The new continent list.
     */
    private List<ContinentsModel> newContinentList;

    private static  final String INVALID = "Invalid";

    /**
     * Constructor initializes values and sets the screen too visible.
     */
    public EditContinentController() {

        file = this.selectFile();
        tempRead.setFile(file);
        continentList = tempRead.getMapContinentDetails();
        this.mapModel = new GameMapModel();
        this.mapModel.setCountries(tempRead.getMapCountryDetails());
        mapModel.setContinents(continentList);
        mapModel.callObservers();
        this.newContinentList = new ArrayList<ContinentsModel>();
        continentList = this.mapModel.getContinents();
        this.editContinentView =
                Environment.getInstance().getViewManager().newEditContinentView(continentList);
        this.editContinentView.addActionListener(this);
        this.editContinentView.showView();
        this.mapModel.addObserver(this.editContinentView);
    }

    /**
     * Browsing a file.
     *
     * @return File
     */
    public File selectFile() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        File file;
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            file = new File(selectedFile.getAbsolutePath());
            return file;
        } else
            return null;

    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {

        if (IEditContinentView.ACTION_ADD.equals(event.getSource())) {
            handleActionAdd();
        } else if (IEditContinentView.ACTION_SAVE.equals(event.getSource())) {
            handleActionSave();
        }
    }


    private void handleActionAdd() {
        String controlValue = editContinentView.getControlValue();

        if (controlValue == null || controlValue.isEmpty()) {
            editContinentView.showOptionDialog("Please enter at least one control value", INVALID);
            return;

        }
        if (0 > Integer.parseInt(controlValue) || Integer.parseInt(controlValue) >10) {
            editContinentView.showOptionDialog("Please enter a control value between 0 and 10", INVALID);
            return;
        }
        this.newContinentModel = this.editContinentView.getContinentsModel();
        this.mapModel.removeContinent(this.newContinentModel);
        this.newContinentModel.setValueControl(Integer.parseInt(this.editContinentView.getControlValue()));
        this.newContinentList.add(this.newContinentModel);
        System.out.println(this.newContinentList);

    }



    private void handleActionSave(){
        String controlValue = editContinentView.getControlValue();
        if (controlValue == null || controlValue.isEmpty()) {
            editContinentView.showOptionDialog("Please enter at least one control value", INVALID);
            return;
        }
        if (0 > Integer.parseInt(controlValue) || Integer.parseInt(controlValue) >10) {
            editContinentView.showOptionDialog("Please enter a control value between 0 and 10",INVALID);
            return;
        }
        if (this.newContinentList.isEmpty()) {
            editContinentView.showOptionDialog("Please add atleast one continent first.",INVALID);
            return;
        }
        this.mapModel.setContinents(newContinentList);
        this.mapModel = this.mapModel.updateCountries(this.mapModel);
        new ConnectCountryController(this.mapModel);
        this.editContinentView.hideView();


    }

}
