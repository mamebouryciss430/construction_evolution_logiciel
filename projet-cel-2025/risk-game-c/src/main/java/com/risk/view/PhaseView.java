package com.risk.view;

import com.risk.controller.MapController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.risk.model.Phase;
import com.risk.model.Player;

import java.util.Observable;
import java.util.Observer;


/**
 * Observer Phase class, store
 * 1) the name of the game phase currently being played
 * 2) the current player's name
 * 3) information about actions that are taking place during this phase
 */
public class PhaseView implements Observer {

    private static PhaseView instance;

    // general map components
    private Label phaseLabel;
    private Button nextPhaseButton;
    private Label currentPlayerNameLabel;
    private Label currentPlayerTypeLabel;
    private Label armiesInHandLabel;
    private Label invalidMovedLabel;
    private Button saveGameButton;
    private Button loadGameButton;

    // From-To country relative components
    private Label countryALabel;
    private Label countryANameLabel;
    private Label countryBLabel;
    private Label countryBNameLabel;

    // attack phase relative components
    private Label attackerDiceLabel;
    private Button attackerDiceOneButton;
    private Button attackerDiceTwoButton;
    private Button attackerDiceThreeButton;
    private Label defenderDiceLabel;
    private Button defenderDiceOneButton;
    private Button defenderDiceTwoButton;
    private Label allOutLabel;
    private Button allOutEnableButton;
    private Button allOutDisableButton;
    private Button attackButton;

    // fortification relative components
    private Label numArmiesMovedLabel;
    private TextField numArmiesMovedTextField;
    private Button skipFortificationPhaseButton;

    private String currentPhase;
    private Player currentPlayer;
    private MapController mapController;


    public static final String SUCCESS = "-fx-border-color: #00ff00; -fx-border-width: 3";
    public static final String INVALID = "-fx-border-color: red; -fx-border-width: 3";
    public static final String RESET = "-fx-border-color: #ff0000; -fx-border-width: 3";


    /**
     * Ctor
     */
    private PhaseView() { currentPhase = "Init"; }


    /**
     * Singleton standard getter method, get the instance
     * @return the instance
     */
    public static PhaseView getInstance() {
        if (null == instance) instance = new PhaseView();
        return instance;
    }


    /**
     *
     * @param gc
     */
    public void init(GeneralComponents gc) {
        this.phaseLabel = gc.phaseLabel;
        this.nextPhaseButton = gc.nextPhaseButton;
        this.currentPlayerNameLabel = gc.currentPlayerNameLabel;
        this.currentPlayerTypeLabel = gc.currentPlayerTypeLabel;
        this.armiesInHandLabel = gc.armiesInHandLabel;
        this.countryALabel = gc.countryALabel;
        this.countryANameLabel = gc.countryANameLabel;
        this.countryBLabel = gc.countryBLabel;
        this.countryBNameLabel = gc.countryBNameLabel;
        this.numArmiesMovedLabel = gc.numArmiesMovedLabel;
        this.numArmiesMovedTextField = gc.numArmiesMovedTextField;
        this.invalidMovedLabel = gc.invalidMovedLabel;
        this.skipFortificationPhaseButton = gc.skipFortificationPhaseButton;
        this.saveGameButton = gc.saveGameButton;
        this.mapController = gc.mapController;

        this.loadGameButton = gc.loadGameButton;
    }


    /**
     *
     * @param ac
     */
    public void initAttackComponents(AttackComponents ac) {
        this.attackerDiceLabel = ac.attackerDiceLabel;
        this.attackerDiceOneButton = ac.attackerDiceOneButton;
        this.attackerDiceTwoButton = ac.attackerDiceTwoButton;
        this.attackerDiceThreeButton = ac.attackerDiceThreeButton;
        this.defenderDiceLabel = ac.defenderDiceLabel;
        this.defenderDiceOneButton = ac.defenderDiceOneButton;
        this.defenderDiceTwoButton = ac.defenderDiceTwoButton;
        this.allOutLabel = ac.allOutLabel;
        this.allOutEnableButton = ac.allOutEnableButton;
        this.allOutDisableButton = ac.allOutDisableButton;
        this.attackButton = ac.attackButton;
    }


    /**
     * Observer standard update method
     * @param obs is the Observable subject, which ic the Phase
     * @param obj is the additional update info
     */
    @Override
    public void update(Observable obs, Object obj) {
        Phase phase = (Phase) obs;
        Player nextPlayer = phase.getCurrentPlayer();

        // check current Player update
        if (isNextPlayer(nextPlayer)) {
            setCurrentPlayer(phase);
        }

        // check current Phase update, or current action update
        if (!currentPhase.equals(phase.getCurrentPhase())) {
            setCurrentPhase(phase);
            phaseChange();
        } else {
            actionChange(phase);
        }
    }

    private void actionChange(Phase phase) {
        switch (phase.getActionResult()) {
            case INVALID_CARD_EXCHANGE:
                phase.clearActionResult();
                break;
            case ALLOCATE_ARMY:
                armiesInHandLabel.setText(Long.toString(currentPlayer.getArmies()));
                phase.clearActionResult();
                break;
            case INVALID_MOVE:
                invalidMove(phase);
                break;
            case MOVE_AFTER_CONQUER:
                moveAfterConquer(phase);
                break;
            case SHOW_NEXT_PHASE_BUTTON:
                showNextPhaseButton(phase);
                break;
            case WIN:
                winPhase(phase);
                break;
            case ATTACK_IMPOSSIBLE:
                attackImpossible(phase);
                break;
            default:
                break;
        }
    }

    private void attackImpossible(Phase phase) {
        displayAttackPhaseMapComponent(false);
        numArmiesMovedLabel.setVisible(false);
        numArmiesMovedTextField.clear();
        numArmiesMovedTextField.setVisible(false);
        mapController.setCountryClick(false);
        countryALabel.setVisible(false);
        countryANameLabel.setVisible(false);
        countryBLabel.setVisible(false);
        countryBNameLabel.setVisible(false);
        invalidMovedLabel.setText(phase.getInvalidInfo());
        invalidMovedLabel.setStyle(INVALID);
        invalidMovedLabel.setVisible(true);
        nextPhaseButton.setVisible(true);
        phase.clearActionResult();
    }

    private void winPhase(Phase phase) {
        loadGameButton.setVisible(false);
        phaseLabel.setVisible(false);
        nextPhaseButton.setVisible(false);
        hide();
        invalidMovedLabel.setText(phase.getInvalidInfo());
        invalidMovedLabel.setStyle(SUCCESS);
        invalidMovedLabel.setVisible(true);
        mapController.setWin();
    }

    private void showNextPhaseButton(Phase phase) {
        invalidMovedLabel.setVisible(false);
        switch (currentPhase) {
            case "Start Up Phase":
                saveGameButton.setVisible(true);
                break;
            case "Attack Phase":
                showNextAttack(phase);
                break;
            case "Fortification Phase":
                mapController.disableFortification();
                saveGameButton.setVisible(true);
                break;
            default:
                break;
        }
        nextPhaseButton.setVisible(true);
        phase.clearActionResult();
    }

    private void showNextAttack(Phase phase) {
        displayAttackPhaseMapComponent(true);
        numArmiesMovedLabel.setVisible(false);
        numArmiesMovedTextField.clear();
        numArmiesMovedTextField.setVisible(false);
        mapController.setCountryClick(true);
        invalidMovedLabel.setText(phase.getInvalidInfo());
        invalidMovedLabel.setStyle(SUCCESS);
        invalidMovedLabel.setVisible(true);
    }

    private void moveAfterConquer(Phase phase) {
        numArmiesMovedLabel.setVisible(true);
        numArmiesMovedTextField.setVisible(true);
        nextPhaseButton.setVisible(false);
        displayAttackPhaseMapComponent(false);
        phase.clearActionResult();
        mapController.setCountryClick(false);
        reset();
        invalidMovedLabel.setText(phase.getInvalidInfo());
        invalidMovedLabel.setStyle(SUCCESS);
        invalidMovedLabel.setVisible(true);
    }

    private void invalidMove(Phase phase) {
        invalidMovedLabel.setText(phase.getInvalidInfo());
        invalidMovedLabel.setStyle(INVALID);
        invalidMovedLabel.setVisible(true);
        phase.clearActionResult();
    }

    private void phaseChange() {
        switch (currentPhase) {
            case "Start Up Phase":
                startUpPhase();
                break;
            case "Reinforcement Phase":
                reinforcementPhase();
                break;
            case "Attack Phase":
                attackPhase();
                break;
            case "Fortification Phase":
                fortificationPhase();
                break;
            default:
                break;
        }
    }

    private void fortificationPhase() {
        // set Fortification Phase UI
        hide();
        reset();
        countryALabel.setVisible(true);
        countryANameLabel.setVisible(true);
        countryBLabel.setVisible(true);
        countryBNameLabel.setVisible(true);
        numArmiesMovedLabel.setVisible(true);
        numArmiesMovedTextField.setVisible(true);
        skipFortificationPhaseButton.setVisible(true);
        nextPhaseButton.setText("Click To Enter Reinforcement Phase");
    }

    private void attackPhase() {
        // set Attack Phase UI
        hide();
        reset();
        countryALabel.setVisible(true);
        countryANameLabel.setVisible(true);
        countryBLabel.setVisible(true);
        countryBNameLabel.setVisible(true);
        displayAttackPhaseMapComponent(true);
        nextPhaseButton.setText("Click To Enter Fortification Phase");
    }

    private void reinforcementPhase() {
        // set Reinforcement Phase UI
        hide();
        // update current Player UI
        armiesInHandLabel.setText(Long.toString(currentPlayer.getArmies()));
        nextPhaseButton.setText("Click To Enter Attack Phase");
    }

    private void startUpPhase() {
        hide();
        reset();
        // update current Player UI
        armiesInHandLabel.setText(Long.toString(currentPlayer.getArmies()));
        nextPhaseButton.setText("Click To Enter Reinforcement Phase");
    }

    private void setCurrentPhase(Phase phase) {
        currentPhase = phase.getCurrentPhase();
        mapController.setCurrentPhase(currentPhase);
        phaseLabel.setText(phase.getCurrentPhase());
        phaseLabel.setVisible(true);
    }

    private void setCurrentPlayer(Phase phase) {
        currentPlayer = phase.getCurrentPlayer();
        currentPlayerNameLabel.setText(currentPlayer.getName());
        currentPlayerNameLabel.setStyle("-fx-background-color: " + currentPlayer.getColor());
        currentPlayerTypeLabel.setText(currentPlayer.getStrategy().getName());
        armiesInHandLabel.setText(Long.toString(phase.getCurrentPlayer().getArmies()));
    }

    private boolean isNextPlayer(Player nextPlayer) {
        return null == currentPlayer || currentPlayer.getId() != nextPlayer.getId();
    }


    /**
     * Hide all map component for attack/fortification phase
     */
    public void hide() {
        countryALabel.setVisible(false);
        countryANameLabel.setVisible(false);
        countryBLabel.setVisible(false);
        countryBNameLabel.setVisible(false);
        attackerDiceLabel.setVisible(false);
        attackerDiceOneButton.setVisible(false);
        attackerDiceTwoButton.setVisible(false);
        attackerDiceThreeButton.setVisible(false);
        defenderDiceLabel.setVisible(false);
        defenderDiceOneButton.setVisible(false);
        defenderDiceTwoButton.setVisible(false);
        allOutLabel.setVisible(false);
        allOutEnableButton.setVisible(false);
        allOutDisableButton.setVisible(false);
        attackButton.setVisible(false);
        numArmiesMovedLabel.setVisible(false);
        numArmiesMovedTextField.setVisible(false);
        numArmiesMovedTextField.clear();
        invalidMovedLabel.setVisible(false);
        skipFortificationPhaseButton.setVisible(false);
    }


    /**
     * Display attack phase relative map component
     * @param display indicates whether the components should be show or hide
     */
    private void displayAttackPhaseMapComponent(boolean display) {
        attackerDiceLabel.setVisible(display);
        attackerDiceOneButton.setVisible(display);
        attackerDiceTwoButton.setVisible(display);
        attackerDiceThreeButton.setVisible(display);
        defenderDiceLabel.setVisible(display);
        defenderDiceOneButton.setVisible(display);
        defenderDiceTwoButton.setVisible(display);
        allOutLabel.setVisible(display);
        allOutEnableButton.setVisible(display);
        allOutDisableButton.setVisible(display);
        attackButton.setVisible(display);
    }


    /**
     * Reset from-to country Labels
     * Called when phase changes
     */
    private void reset() {
        countryANameLabel.setText("NONE");
        countryANameLabel.setStyle(RESET);
        countryBNameLabel.setText("NONE");
        countryBNameLabel.setStyle(RESET);
    }
}
