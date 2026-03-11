package com.risk.controller;

import com.risk.model.Country;
import com.risk.model.Dice;
import com.risk.strategy.Human;
import com.risk.strategy.PlayerBehaviour;
import com.risk.services.Util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * DiceController class contains the methods used in dice view,
 * load attack screen, move armies, cancel move, cancel throw,
 * continue Dice Roll, attack dice value, defence dice value
 * and start dice roll.
 *
 * @author Farhan Shaheen
 */
public class DiceController extends Observable implements Initializable {

    /**
     * Label variable for attacking player
     */
    @FXML
    private Label attackingPlayer;

    /**
     * Label variable for attacking Country
     */
    @FXML
    private Label attackingCountry;

    /**
     * Label variable for attacking armies
     */
    @FXML
    private Label attackingArmies;

    /**
     * Label variable for defending player
     */
    @FXML
    private Label defendingPlayer;

    /**
     * Label variable for defending country
     */
    @FXML
    private Label defendingCountry;

    /**
     * Label variable for defending armies
     */
    @FXML
    private Label defendingArmies;

    /**
     * Label variable for winner name
     */
    @FXML
    private Label winnerName;

    /**
     * CheckBox variable for dice 1 attacker
     */
    @FXML
    private CheckBox dice1_Attacker;

    /**
     * CheckBox variable for dice 2 attacker
     */
    @FXML
    private CheckBox dice2_Attacker;

    /**
     * CheckBox variable for dice 3 attacker
     */
    @FXML
    private CheckBox dice3_Attacker;

    /**
     * CheckBox variable for dice 1 defender
     */
    @FXML
    private CheckBox dice1_Defender;

    /**
     * CheckBox variable for dice 2 defender
     */
    @FXML
    private CheckBox dice2_Defender;

    /**
     * Button variable for start roll
     */
    @FXML
    private Button startRoll;

    /**
     * Button variable for cancel throw
     */
    @FXML
    private Button cancelThrow;

    /**
     * Button variable for continue roll
     */
    @FXML
    private Button continueRoll;

    /**
     * Pane variable for after attack view
     */
    @FXML
    private Pane afterAttackView;

    /**
     * Button variable for move armies
     */
    @FXML
    private Button moveArmies;

    /**
     * Button variable for cancel move
     */
    @FXML
    private Button cancelMove;

    /**
     * The @moveAllArmies button.
     */
    @FXML
    private Button moveAllArmies;

    /**
     * TextField variable for number of armies to move
     */
    @FXML
    private TextField numberOfArmiesToMove;

    /**
     * Object for Dice class
     */
    private Dice dice;

    /**
     * Object for PlayerBehaviour class
     */
    private PlayerBehaviour playerBehaviour;

    /**
     * DiceController Constructor
     *
     * @param dice               Dice object
     * @param playerBehaviour    PlayerBehaviour Object
     * @param gamePlayController GamePlayController object
     */
    public DiceController(Dice dice, PlayerBehaviour playerBehaviour, GamePlayController gamePlayController) {
        this.dice = dice;
        this.playerBehaviour = playerBehaviour;
        this.addObserver(gamePlayController);
    }

    /**
     * DiceController Constructor
     *
     * @param dice            Dice object
     * @param playerBehaviour PlayerBehaviour object
     */
    public DiceController(Dice dice, PlayerBehaviour playerBehaviour) {
        this.dice = dice;
        this.playerBehaviour = playerBehaviour;
    }

    /**
     * Method to call load attack screen and dice view
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadAttackScreen();
        diceView();

    }

    /**
     * Method to automate dice roll
     */
    public void automateDiceRoll() {
        automaticInitialization();
        loadAttackScreen();
        diceView();
        if (!(playerBehaviour instanceof Human)) {
            autoRollDice();
        }

    }

    /**
     * Method to auto role dice
     */
    private void autoRollDice() {
        WindowUtil.selectVisibleDice(dice1_Attacker, dice2_Attacker, dice3_Attacker, dice1_Defender, dice2_Defender);

        startRoll(null);
        if (!continueRoll.isDisabled() && !cancelThrow.isDisabled()) {
            continueDiceRoll(null);
        } else if (continueRoll.isDisabled() && !cancelThrow.isDisabled()) {
            dice.cancelDiceThrow();
        } else if (afterAttackView.isVisible()) {
            dice.moveAllArmies();
        }
    }

    /**
     * Method to load Attack screen
     */
    public void loadAttackScreen() {
        // TODO Auto-generated method stub

        Country countryAttacking = dice.getAttackingCountry();
        attackingPlayer.setText(countryAttacking.getPlayer().getName());
        attackingCountry.setText(countryAttacking.getName());
        attackingArmies.setText("Armies: " + countryAttacking.getNoOfArmies());

        Country countryDefending = dice.getDefendingCountry();
        defendingPlayer.setText(countryDefending.getPlayer().getName());
        defendingCountry.setText(countryDefending.getName());
        defendingArmies.setText("Armies: " + countryDefending.getNoOfArmies());

        winnerName.setVisible(false);
        winnerName.setText("");

        WindowUtil.unCheckBoxes(dice1_Attacker, dice2_Attacker, dice3_Attacker, dice1_Defender, dice2_Defender);
        WindowUtil.enableButtonControl(startRoll, continueRoll);
        WindowUtil.disableButtonControl(winnerName);
        WindowUtil.disablePane(afterAttackView);
    }

    /**
     * Method for dice view
     */
    public void diceView() {
        if (dice.getAttackingCountry().getNoOfArmies() >= 4) {
            WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker, dice3_Attacker);
        } else if (dice.getAttackingCountry().getNoOfArmies() == 3) {
            WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker);
            WindowUtil.hideButtonControl(dice3_Attacker);
        } else if (dice.getAttackingCountry().getNoOfArmies() == 2) {
            WindowUtil.showCheckBox(dice1_Attacker);
            WindowUtil.hideButtonControl(dice2_Attacker, dice3_Attacker);
        }
        if (dice.getDefendingCountry().getNoOfArmies() >= 2) {
            WindowUtil.showCheckBox(dice1_Defender, dice2_Defender);
        } else if (dice.getDefendingCountry().getNoOfArmies() == 1) {
            WindowUtil.showCheckBox(dice1_Defender);
            WindowUtil.hideButtonControl(dice2_Defender);
        }
    }

    /**
     * Method to move armies
     *
     * @param event ActionEvent
     */
    @FXML
    @SuppressWarnings("unused")
    private void moveArmies(@SuppressWarnings("unused") ActionEvent event) {

        String getText = numberOfArmiesToMove.getText();

        if (getText.length() == 0) {
            WindowUtil.popUpWindow("Armies Alert", " Title", "Please enter a valid number to move armies.");
            return;
        } else {
            int numberOfArmies = Integer.valueOf(getText.trim());
            dice.moveArmies(numberOfArmies, winnerName, moveArmies);
        }
    }

    /**
     * Move all armies
     *
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void moveAllArmies() {
        dice.moveAllArmies();
        WindowUtil.exitWindow(moveAllArmies);
    }

    /**
     * Method to cancel move
     *
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void cancelMove() {
        dice.skipMoveArmy();
        WindowUtil.exitWindow(cancelMove);
    }

    /**
     * Method to cancel throw
     *
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void cancelThrow() {
        dice.cancelDiceThrow();
        WindowUtil.exitWindow(cancelThrow);
    }

    /**
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void allOut() {
        dice.setAttackerDiceList(new ArrayList<>());
        dice.setDefenderDiceList(new ArrayList<>());

        loadAttackScreen();

        Country attacker = dice.getAttackingCountry();
        Country defender = dice.getDefendingCountry();

        int bufferAttacker = attacker.getNoOfArmies();
        int bufferDefender = defender.getNoOfArmies();

        boolean flagAttack;
        boolean flagDefender;

        while (attacker.getNoOfArmies() > 1 && defender.getNoOfArmies() > 0) {
            dice.getAttackerDiceList().clear();
            dice.getDefenderDiceList().clear();

            refreshAttackerDice(attacker);
            refreshDefenderDice(defender);

            flagAttack = attacker.getNoOfArmies() != bufferAttacker;
            flagDefender = defender.getNoOfArmies() != bufferDefender;

            notifyLosses(attacker, bufferAttacker, flagAttack);
            notifyLosses(defender, bufferDefender, flagDefender);

            updateArmiesLabels(attacker, defender);
        }

        handleBattleOutcome(attacker, defender);

        diceView();
        if (!(playerBehaviour instanceof Human)) {
            autoRollDice();
        }
    }

// ---------- Méthodes auxiliaires pour simplifier allOut ----------

    private void refreshAttackerDice(Country attacker) {
        if (attacker.getNoOfArmies() >= 4) {
            WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker, dice3_Attacker);
            WindowUtil.checkCheckBoxes(dice1_Attacker, dice2_Attacker, dice3_Attacker);
            attackDiceValue(dice1_Attacker, dice2_Attacker, dice3_Attacker);
        } else if (attacker.getNoOfArmies() == 3) {
            WindowUtil.showCheckBox(dice1_Attacker, dice2_Attacker);
            WindowUtil.hideButtonControl(dice3_Attacker);
            WindowUtil.checkCheckBoxes(dice1_Attacker, dice2_Attacker);
            attackDiceValue(dice1_Attacker, dice2_Attacker);
        } else if (attacker.getNoOfArmies() == 2) {
            WindowUtil.showCheckBox(dice1_Attacker);
            WindowUtil.checkCheckBoxes(dice1_Attacker);
            WindowUtil.hideButtonControl(dice2_Attacker, dice3_Attacker);
            attackDiceValue(dice1_Attacker);
        }
    }

    private void refreshDefenderDice(Country defender) {
        if (defender.getNoOfArmies() >= 2) {
            WindowUtil.showCheckBox(dice1_Defender, dice2_Defender);
            WindowUtil.checkCheckBoxes(dice1_Defender, dice2_Defender);
            defenceDiceValue(dice1_Defender, dice2_Defender);
        } else if (defender.getNoOfArmies() == 1) {
            WindowUtil.showCheckBox(dice1_Defender);
            WindowUtil.checkCheckBoxes(dice1_Defender);
            WindowUtil.hideButtonControl(dice2_Defender);
            defenceDiceValue(dice1_Defender);
        }
    }

    private void notifyLosses(Country country, int buffer, boolean flag) {
        if (flag) {
            String message = country.getPlayer().getName() + " lost: " + (buffer - country.getNoOfArmies()) + " armies\n";
            System.out.println(message);
            setChanged();
            notifyObservers(message);
            winnerName.setText(message);
        }
    }

    private void updateArmiesLabels(Country attacker, Country defender) {
        attackingArmies.setText("Armies: " + attacker.getNoOfArmies());
        defendingArmies.setText("Armies: " + defender.getNoOfArmies());
    }


    /**
     * Method to continue dice roll
     *
     * @param event ActionEvent
     */
    @FXML
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void continueDiceRoll(ActionEvent event) {

        dice.setAttackerDiceList(new ArrayList<>());
        dice.setDefenderDiceList(new ArrayList<>());
        loadAttackScreen();

        diceView();
        if (!(playerBehaviour instanceof Human)) {
            autoRollDice();
        }
    }

    /**
     * Method to attack dice value
     *
     * @param allCheckBoxes CheckBox
     */
    public void attackDiceValue(CheckBox... allCheckBoxes) {
        for (CheckBox checkBox : allCheckBoxes) {
            if (checkBox.isSelected()) {
                int diceValue = dice.generateRandomNumber();
                checkBox.setText(String.valueOf(diceValue));
                dice.getAttackerDiceList().add(diceValue);
            }
        }
    }

    /**
     * Method to defence dice value
     *
     * @param allCheckBoxes CheckBox
     */
    public void defenceDiceValue(CheckBox... allCheckBoxes) {
        for (CheckBox checkBox : allCheckBoxes) {
            if (checkBox.isSelected()) {
                int diceValue = dice.generateRandomNumber();
                checkBox.setText(String.valueOf(diceValue));
                dice.getDefenderDiceList().add(diceValue);
            }
        }
    }

    /**
     * Method to start roll
     *
     * @param event ActionEvent
     */
    @FXML
    public void startRoll(ActionEvent event) {
            if (!validateDiceSelection()) return;

            // Lancer les dés
            attackDiceValue(dice1_Attacker, dice2_Attacker, dice3_Attacker);
            defenceDiceValue(dice1_Defender, dice2_Defender);

            Country attacker = dice.getAttackingCountry();
            Country defender = dice.getDefendingCountry();

            handleBattleOutcome(attacker, defender);
        }

/**
 * Vérifie que l'utilisateur a sélectionné au moins un dé attaquant et un dé défenseur
 * @return true si valide, false sinon
 */
        private boolean validateDiceSelection() {
            if (!dice1_Attacker.isSelected() && !dice2_Attacker.isSelected() && !dice3_Attacker.isSelected()) {
                WindowUtil.popUpWindow("Head", "Message", "At least one attacking dice should be selected");
                return false;
            }
            if (!dice1_Defender.isSelected() && !dice2_Defender.isSelected()) {
                WindowUtil.popUpWindow("Head", "Message", "At least one defender dice should be selected");
                return false;
            }
            return true;
        }

/**
 * Gère le résultat de la bataille, mise à jour des armées, affichage du gagnant et notifications
 */
        private void handleBattleOutcome(Country attacker, Country defender) {
            int bufferAttacking = attacker.getNoOfArmies();
            int bufferDefending = defender.getNoOfArmies();

            boolean attackerLost = attacker.getNoOfArmies() != bufferAttacking;
            boolean defenderLost = defender.getNoOfArmies() != bufferDefending;

            if (attackerLost) notifyPlayerLoss(attacker, bufferAttacking);
            if (defenderLost) notifyPlayerLoss(defender, bufferDefending);

            // Gérer fin de bataille et mise à jour UI
            if (defender.getNoOfArmies() <= 0) {
                dice.setCountriesWonCount(dice.getCountriesWonCount() + 1);
                WindowUtil.enablePane(afterAttackView);
                WindowUtil.disableButtonControl(startRoll, continueRoll, cancelThrow);
                WindowUtil.hideButtonControl(startRoll, continueRoll, cancelThrow);
                winnerName.setText(attacker.getPlayer().getName() + " won " + defender.getName() + " Country");
            } else if (attacker.getNoOfArmies() < 2) {
                WindowUtil.disableButtonControl(startRoll, continueRoll);
                WindowUtil.enableButtonControl(cancelThrow);
                WindowUtil.disablePane(afterAttackView);
                winnerName.setText(attacker.getPlayer().getName() + " lost the match");
            } else {
                WindowUtil.disablePane(afterAttackView);
                WindowUtil.disableButtonControl(startRoll);
                WindowUtil.enableButtonControl(continueRoll, cancelThrow);
            }

            defendingArmies.setText("Armies: " + defender.getNoOfArmies());
            attackingArmies.setText("Armies: " + attacker.getNoOfArmies());
            winnerName.setVisible(true);
        }

/**
 * Notifie la perte d'armées d'un joueur
 */
        private void notifyPlayerLoss(Country country, int buffer) {
            int lost = buffer - country.getNoOfArmies();
            String message = country.getPlayer().getName() + " lost: " + lost + " armies\n";
            System.out.println(message);
            setChanged();
            notifyObservers(message);
        }

    /**
     * Method to automate setup of DiceController
     */
    public void automaticInitialization() {

        attackingPlayer = new Label();

        attackingCountry = new Label();

        attackingArmies = new Label();

        defendingPlayer = new Label();

        defendingCountry = new Label();

        defendingArmies = new Label();

        winnerName = new Label();

        dice1_Attacker = new CheckBox();

        dice2_Attacker = new CheckBox();

        dice3_Attacker = new CheckBox();

        dice1_Defender = new CheckBox();

        dice2_Defender = new CheckBox();

        startRoll = new Button();

        cancelThrow = new Button();

        continueRoll = new Button();

        afterAttackView = new Pane();

        moveArmies = new Button();

        cancelMove = new Button();

        numberOfArmiesToMove = new TextField();
    }

}