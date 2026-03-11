package com.risk.view;

import com.risk.controller.MapController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GeneralComponents {

    // general map components
    public Label phaseLabel;
    public Button nextPhaseButton;
    public Label currentPlayerNameLabel;
    public Label currentPlayerTypeLabel;
    public Label armiesInHandLabel;
    public Label invalidMovedLabel;
    public Button saveGameButton;
    public Button loadGameButton;

    // From-To country relative components
    public Label countryALabel;
    public Label countryANameLabel;
    public Label countryBLabel;
    public Label countryBNameLabel;

    // fortification relative components
    public Label numArmiesMovedLabel;
    public TextField numArmiesMovedTextField;
    public Button skipFortificationPhaseButton;

    public MapController mapController;



    // Constructeur minimal
    public GeneralComponents(MapController mapController) {
        this.mapController = mapController;
    }

    // Setters fluents
    public GeneralComponents addPhaseComponents(Label phaseLabel, Button nextPhaseButton) {
        this.phaseLabel = phaseLabel;
        this.nextPhaseButton = nextPhaseButton;
        return this;
    }

    public GeneralComponents addPlayerComponents(Label playerName, Label playerType, Label armiesInHand) {
        this.currentPlayerNameLabel = playerName;
        this.currentPlayerTypeLabel = playerType;
        this.armiesInHandLabel = armiesInHand;
        return this;
    }

    public GeneralComponents addCountryComponents(Label aLabel, Label aName, Label bLabel, Label bName) {
        this.countryALabel = aLabel;
        this.countryANameLabel = aName;
        this.countryBLabel = bLabel;
        this.countryBNameLabel = bName;
        return this;
    }

    public GeneralComponents addFortificationComponents(Label numArmiesLabel, TextField numArmiesText, Button skipButton) {
        this.numArmiesMovedLabel = numArmiesLabel;
        this.numArmiesMovedTextField = numArmiesText;
        this.skipFortificationPhaseButton = skipButton;
        return this;
    }

    public GeneralComponents addSaveLoadButtons(Button save, Button load, Label invalid) {
        this.saveGameButton = save;
        this.loadGameButton = load;
        this.invalidMovedLabel = invalid;
        return this;
    }
}
