package com.risk.model;

import com.risk.exception.InvalidMapException;
import com.risk.validate.MapValidator;
import com.risk.view.FileInfoMenuView;
import com.risk.view.NumPlayerMenuView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;

public class MenuManager  extends Observable implements Serializable {

    private FileInfoMenu fileInfoMenu;
    private NumPlayerMenu numPlayerMenu;
    private final Model gameModel;
    private boolean validFile = true;


    public MenuManager(Model gameModel) {
        this.gameModel = gameModel;
    }
    /**
     * load file and initiate model
     * @param filePath The path of the map file
     * @throws IOException the exception that throw
     */
    public void loadFromMapFile(String filePath) throws IOException{
        gameModel.reset();
        String content = "";
        String line = "";
        String bodies[];
        FileReader fileReader = new FileReader(filePath);
        BufferedReader in = new BufferedReader(fileReader);
        while (line != null){
            content = content + line + "\n";
            line = in.readLine();
        }
        //validate map file
        try {
            bodies = content.split("\n\n");
            gameModel.initiateContinents(bodies[1]);
            for(int i = 2; i < bodies.length; i ++){
                gameModel.initiateCountries(bodies[i]);
            }
        } catch (Exception ex){
            validFile = false;
        }
    }

    /**
     * load file path for map editor
     * @param filePath The path of the map file
     * @throws IOException exception
     * @throws InvalidMapException Exception when the map layout is invalid
     */
    public void editorReadFile(String filePath) throws IOException, InvalidMapException {
        loadFromMapFile(filePath);
        MapValidator.validateMap(gameModel);
    }

    /**
     * load the map from a player chose map file
     * validate map file
     * initiate continents,countries and players
     * notify View
     * @param filePath The path of the map file
     * @throws IOException io exceptions
     */
    public void readFile(String filePath) throws IOException {

        loadFromMapFile(filePath);
        if(!validFile){
            fileInfoMenu.setValidationResult(false,"invalid file format!");
            fileInfoMenu.update();
            numPlayerMenu.setVisible(false);
            numPlayerMenu.update();
            return;
        }

        try {
            MapValidator.validateMap(gameModel);
        }
        catch (InvalidMapException ex){

            fileInfoMenu.setValidationResult(false,ex.getMessage());
            fileInfoMenu.update();
            numPlayerMenu.setVisible(false);
            numPlayerMenu.update();

            System.out.println(ex.toString());
            return;
        }
        fileInfoMenu.setValidationResult(true,"valid map");
        fileInfoMenu.update();

        numPlayerMenu.setVisible(true);
        numPlayerMenu.setMaxNumPlayer(gameModel.getCountries().size());
        numPlayerMenu.setValidationResult(false,"Total Player: NONE");
        numPlayerMenu.update();
    }
    /**
     * Receive two menu observer references, bind them with corresponding observable subjects
     * @param fileInfoMenuView displays the general selected file info
     * @param numPlayerMenuView displays the num of players info
     */
    public void setMenuViews(FileInfoMenuView fileInfoMenuView, NumPlayerMenuView numPlayerMenuView) {
        fileInfoMenu = new FileInfoMenu();
        fileInfoMenu.addObserver(fileInfoMenuView);
        numPlayerMenu = new NumPlayerMenu();
        numPlayerMenu.addObserver(numPlayerMenuView);
    }



    public FileInfoMenu getFileInfoMenu() {
        return fileInfoMenu;
    }

    public void setFileInfoMenu(FileInfoMenu fileInfoMenu) {
        this.fileInfoMenu = fileInfoMenu;
    }

    public NumPlayerMenu getNumPlayerMenu() {
        return numPlayerMenu;
    }

    public void setNumPlayerMenu(NumPlayerMenu numPlayerMenu) {
        this.numPlayerMenu = numPlayerMenu;
    }

    public boolean isValidFile() {
        return validFile;
    }

    public void setValidFile(boolean validFile) {
        this.validFile = validFile;
    }
}
