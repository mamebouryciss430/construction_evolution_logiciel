package com.risk.controller;

import com.risk.Environment;
import com.risk.model.CardModel;
import com.risk.model.CountryModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.model.strategy.*;
import com.risk.utilities.SaveGame;
import com.risk.utilities.Validation;
import com.risk.view.IAttackView;
import com.risk.view.IFortificationView;
import com.risk.view.IReinforcementView;
import com.risk.view.IView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * In PlayerController, the data flow into model object and updates the view
 * whenever data changes.
 *
 * @version 1.0.0
 *
 */
public class PlayerController implements ViewActionListener {

    /**
     * The game play model.
     */
    private GamePlayModel gamePlayModel;

    /**
     * The val.
     */
    private Validation val = new Validation();

    /**
     * The reinforcement view.
     */
    private IReinforcementView theReinforcementView;

    /**
     * The fortification view.
     */
    private IFortificationView theFortificationView;

    /**
     * The attack view.
     */
    private IAttackView theAttackView;

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gamePlayModel the game play model
     */
    public PlayerController(@NotNull GamePlayModel gamePlayModel) {
        this.gamePlayModel = gamePlayModel;

        if (gamePlayModel.getGamePhase() == null) {
            if (!val.endOfGame(gamePlayModel)) {
                String playerType = gamePlayModel.getGameMap().getPlayerTurn().getTypePlayer();
                handleInitialTurn(playerType);
            } else {
                showWinner();
            }
        } else {
            handlePhase(gamePlayModel.getGamePhase());
            gamePlayModel.setGamePhase(null);
        }
    }

    private void handleInitialTurn(String playerType) {
        if ("Human".equals(playerType)) {
            setupHumanTurn();
        } else if ("Aggressive".equals(playerType)) {
            applyAggressiveTurn();
        } else if ("Benevolent".equals(playerType)) {
            applyBenevolentTurn();
        } else if ("Random".equals(playerType)) {
            applyRandomTurn();
        } else if ("Cheater".equals(playerType)) {
            applyCheaterTurn();
        }

        if (!"Human".equals(playerType)) {
            advanceToNextPlayer();
            new GamePlayController(gamePlayModel);
        }
    }

    private void handlePhase(String phase) {
        String playerType = gamePlayModel.getGameMap().getPlayerTurn().getTypePlayer();
        if (!"Human".equals(playerType)) return;

        if ("Reinforcement".equals(phase)) {
            setupHumanTurn();
        } else if ("Attack".equals(phase)) {
            setupAttackPhase();
        } else if ("Fortification".equals(phase)) {
            setupFortificationPhase();
        }
    }

    private void setupHumanTurn() {
        gamePlayModel.getGameMap().getPlayerTurn()
                .setStrategy(new HumanPlayerStrategy(gamePlayModel));
        gamePlayModel.getGameMap().getPlayerTurn().executeReinforcement();

        theReinforcementView = Environment.getInstance().getViewManager().newReinforcementView(gamePlayModel);
        theReinforcementView.addActionListener(this);
        theReinforcementView.showView();

        gamePlayModel.getGameMap().addObserver(theReinforcementView);
        gamePlayModel.addObserver(theReinforcementView);
    }

    private void applyAggressiveTurn() {
        gamePlayModel.getGameMap().getPlayerTurn()
                .setStrategy(new AgressivePlayerStrategy(gamePlayModel));
        executeAllPhases();
    }

    private void applyBenevolentTurn() {
        gamePlayModel.getGameMap().getPlayerTurn()
                .setStrategy(new BenevolentPlayerStrategy(gamePlayModel));
        executeAllPhases();
    }

    private void applyRandomTurn() {
        gamePlayModel.getGameMap().getPlayerTurn()
                .setStrategy(new RandomPlayerStrategy(gamePlayModel));
        executeAllPhases();
    }

    private void applyCheaterTurn() {
        gamePlayModel.getGameMap().getPlayerTurn()
                .setStrategy(new CheaterPlayerStrategy(gamePlayModel));
        executeAllPhases();
    }

    private void executeAllPhases() {
        gamePlayModel.getGameMap().getPlayerTurn().executeReinforcement();
        gamePlayModel.getGameMap().getPlayerTurn().executeAttack();
        gamePlayModel.getGameMap().getPlayerTurn().executeFortification();
    }

    private void advanceToNextPlayer() {
        int index = gamePlayModel.getGameMap().getPlayerIndex() + 1;
        if (index >= gamePlayModel.getPlayers().size()) {
            index = 0;
        }
        gamePlayModel.getGameMap().setPlayerIndex(index);
        gamePlayModel.getPlayers().get(index).callObservers();
    }

    private void showWinner() {
        String nameOfWinner = val.determineWinner(gamePlayModel);
        if ("draw".equals(nameOfWinner)) {
            JOptionPane.showOptionDialog(null, "The game is draw", "Valid",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{}, null);
        } else {
            JOptionPane.showOptionDialog(null,
                    "Bravo! You have won! Game is over! " + nameOfWinner + " is the winner", "Valid",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{}, null);
        }
    }

    private void setupAttackPhase() {
        theReinforcementView = Environment.getInstance().getViewManager().newReinforcementView(gamePlayModel);
        theReinforcementView.hideView();

        theAttackView = Environment.getInstance().getViewManager().newAttackView(gamePlayModel);
        theAttackView.addActionListener(this);
        theAttackView.showView();

        gamePlayModel.deleteObservers();
        gamePlayModel.addObserver(theAttackView);
        gamePlayModel.setArmyToMoveText(false);
        gamePlayModel.setCardToBeAssigned(false);
    }

    private void setupFortificationPhase() {
        theReinforcementView = Environment.getInstance().getViewManager().newReinforcementView(gamePlayModel);
        theReinforcementView.hideView();

        theAttackView = Environment.getInstance().getViewManager().newAttackView(gamePlayModel);
        theAttackView.hideView();

        theFortificationView = Environment.getInstance().getViewManager().newFortificationView(gamePlayModel);
        theFortificationView.addActionListener(this);
        theFortificationView.showView();

        gamePlayModel.addObserver(theFortificationView);
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     */

    @Override
    public void actionPerformed(@NotNull ViewActionEvent event) {
        Object source = event.getSource();

        // -----------------------------
        // REINFORCEMENT PHASE
        // -----------------------------
        if (isReinforcementAction(source)) {
            handleReinforcementAction(source);
            return;
        }

        // -----------------------------
        // ATTACK PHASE
        // -----------------------------
        if (isAttackAction(source)) {
            handleAttackAction(source);
            return;
        }

        // -----------------------------
        // FORTIFICATION PHASE
        // -----------------------------
        if (isFortificationAction(source)) {
            handleFortificationAction(source);
        }
    }

    // =========================================================================
    // PHASE IDENTIFICATION
    // =========================================================================

    private boolean isReinforcementAction(Object source) {
        return IReinforcementView.ACTION_ADD.equals(source)
                || IReinforcementView.ACTION_ADD_MORE.equals(source)
                || IReinforcementView.ACTION_EXIT_CARD.equals(source)
                || IReinforcementView.ACTION_SAVE.equals(source);
    }

    private boolean isAttackAction(Object source) {
        return IAttackView.ACTION_NEXT.equals(source)
                || IAttackView.ACTION_ATTACK_COUNTRY_CHANGED.equals(source)
                || IAttackView.ACTION_DEFEND_COUNTRY_CHANGED.equals(source)
                || IAttackView.ACTION_SINGLE.equals(source)
                || IAttackView.ACTION_ALLOUT.equals(source)
                || IAttackView.ACTION_MOVE.equals(source)
                || IAttackView.ACTION_SAVE.equals(source);
    }

    private boolean isFortificationAction(Object source) {
        return IFortificationView.ACTION_MOVE.equals(source)
                || IFortificationView.ACTION_FROM_COUNTRY_CHANGED.equals(source)
                || IFortificationView.ACTION_ITEM_FROM_COUNTRY_CHANGED.equals(source)
                || IFortificationView.ACTION_SAVE.equals(source)
                || IFortificationView.ACTION_NEXT.equals(source);
    }

    // =========================================================================
    // PHASE DISPATCHERS
    // =========================================================================

    private void handleReinforcementAction(Object source) {
        if (IReinforcementView.ACTION_ADD.equals(source)) {
            handleReinforcementAdd();
            return;
        }
        if (IReinforcementView.ACTION_ADD_MORE.equals(source)) {
            handleReinforcementAddMore();
            return;
        }
        if (IReinforcementView.ACTION_EXIT_CARD.equals(source)) {
            handleReinforcementExitCard();
            return;
        }
        if (IReinforcementView.ACTION_SAVE.equals(source)) {
            handleReinforcementSave();
        }
    }

    private void handleAttackAction(Object source) {
        if (IAttackView.ACTION_NEXT.equals(source)) {
            handleAttackNext();
            return;
        }
        if (IAttackView.ACTION_ATTACK_COUNTRY_CHANGED.equals(source)) {
            gamePlayModel.setSelectedAttackComboBoxIndex(theAttackView.getAttackCountryIndex());
            return;
        }
        if (IAttackView.ACTION_DEFEND_COUNTRY_CHANGED.equals(source)) {
            gamePlayModel.setSelectedDefendComboBoxIndex(theAttackView.getDefendCountryIndex());
            return;
        }
        if (IAttackView.ACTION_SINGLE.equals(source)) {
            handleSingleAttack();
            return;
        }
        if (IAttackView.ACTION_ALLOUT.equals(source)) {
            handleAllOutAttack();
            return;
        }
        if (IAttackView.ACTION_MOVE.equals(source)) {
            handleAttackMove();
            return;
        }
        if (IAttackView.ACTION_SAVE.equals(source)) {
            handleAttackSave();
        }
    }

    private void handleFortificationAction(Object source) {
        if (IFortificationView.ACTION_MOVE.equals(source)) {
            handleFortificationMove();
            return;
        }
        if (IFortificationView.ACTION_FROM_COUNTRY_CHANGED.equals(source)
                || IFortificationView.ACTION_ITEM_FROM_COUNTRY_CHANGED.equals(source)) {
            gamePlayModel.setSelectedComboBoxIndex(theFortificationView.getFromCountryIndex());
            return;
        }
        if (IFortificationView.ACTION_SAVE.equals(source)) {
            handleFortificationSave();
            return;
        }
        if (IFortificationView.ACTION_NEXT.equals(source)) {
            handleFortificationNext();
        }
    }

        // -------------------------------------------------------------------------
        // REINFORCEMENT METHODS
        // -------------------------------------------------------------------------

        private void handleReinforcementAdd() {
            int selectedArmies = theReinforcementView.getNumOfTroops();

            if (selectedArmies >= 0) {
                CountryModel country = theReinforcementView.getCountryModel();
                gamePlayModel.setSelectedArmiesToCountries(selectedArmies, country);
                gamePlayModel.getConsole().append(selectedArmies + " armies added to " + country.getCountryName());
                return;
            }

            theReinforcementView.hideView();
            theAttackView = Environment.getInstance().getViewManager().newAttackView(gamePlayModel);
            theAttackView.addActionListener(this);
            theAttackView.showView();

            gamePlayModel.deleteObservers();
            gamePlayModel.addObserver(theAttackView);
            gamePlayModel.setArmyToMoveText(false);
            gamePlayModel.setCardToBeAssigned(false);
        }

        private void handleReinforcementAddMore() {
            int cardID = theReinforcementView.getCardId();
            int cardValue = 0;

            for (CardModel c : gamePlayModel.getCards()) {
                if (c.getCardId() == cardID) {
                    cardValue = c.getCardValue();
                    break;
                }
            }

            CardModel card = new CardModel();
            card.setCardId(cardID);
            card.setCardValue(cardValue);

            var player = gamePlayModel.getGameMap().getPlayerTurn();
            player.setremainTroop(player.getremainTroop() + cardValue);

            for (PlayerModel p : gamePlayModel.getPlayers()) {
                if (p.getName().equals(player.getName())) {
                    gamePlayModel.getConsole().append(
                            p.getName() + " is reimbursing card " + cardID + " and gets " + cardValue + " armies ");
                    p.removeCard(card);
                }
            }

            gamePlayModel.getCards().add(card);
            gamePlayModel.callObservers();
        }

        private void handleReinforcementExitCard() {
            var player = gamePlayModel.getGameMap().getPlayerTurn();
            boolean mustReimburse = player.getOwnedCards().size() >= 5;

            player.setShowReinforcementCard(mustReimburse);

            for (PlayerModel p : gamePlayModel.getPlayers()) {
                if (p.getName().equals(player.getName())) {
                    p.setShowReinforcementCard(mustReimburse);
                }
            }

            if (mustReimburse) {
                JOptionPane.showMessageDialog(null,
                        "Maximum 5 cards allowed. Please select a card id to reimburse");
            } else {
                gamePlayModel.callObservers();
            }
        }

        private void handleReinforcementSave() {
            handleSave("Reinforcement", theReinforcementView);
        }

        // -------------------------------------------------------------------------
        // ATTACK METHODS
        // -------------------------------------------------------------------------

        private void handleAttackNext() {
            theAttackView.hideView();

            theFortificationView = Environment.getInstance()
                    .getViewManager().newFortificationView(gamePlayModel);

            theFortificationView.addActionListener(this);
            theFortificationView.showView();

            gamePlayModel.addObserver(theFortificationView);
        }

        private void handleSingleAttack() {
            var player = gamePlayModel.getGameMap().getPlayerTurn();

            gamePlayModel.getConsole().append("This is a Single attack from " + player.getName());
            player.executeAttack();

            int attackDice = theAttackView.getNumOfDiceAttack();
            int defendDice = theAttackView.getNumOfDiceDefend();
            CountryModel attackCountry = theAttackView.getAttackCountryModel();
            CountryModel defendCountry = theAttackView.getDefendCountryModel();

            gamePlayModel.setDefeatedCountry(defendCountry);
            gamePlayModel.singleStrike(attackDice, attackCountry, defendDice, defendCountry);

            if (val.endOfGame(gamePlayModel)) {
                JOptionPane.showMessageDialog(null, "Bravo! You have won! Game is over!");
                theAttackView.hideView();
            }
        }

        private void handleAllOutAttack() {
            var player = gamePlayModel.getGameMap().getPlayerTurn();

            gamePlayModel.getConsole().append("This is an Allout attack from " + player.getName());
            player.executeAttack();

            CountryModel attackCountry = theAttackView.getAttackCountryModel();
            CountryModel defendCountry = theAttackView.getDefendCountryModel();

            gamePlayModel.setDefeatedCountry(defendCountry);

            gamePlayModel.getConsole().append("The attacker is " + attackCountry.getCountryName());
            gamePlayModel.getConsole().append("The defender is " + defendCountry.getCountryName());

            gamePlayModel.alloutStrike(attackCountry, defendCountry);

            if (val.endOfGame(gamePlayModel)) {
                JOptionPane.showMessageDialog(null, "Bravo! You have won! Game is over!");
                theAttackView.hideView();
            }
        }

        private void handleAttackMove() {
            CountryModel attackCountry = theAttackView.getAttackCountryModel();
            int armies = theAttackView.getNumOfArmiesToBeMoved();
            CountryModel defendCountry = gamePlayModel.getDefeatedCountry();

            gamePlayModel.moveArmies(attackCountry, defendCountry, armies);

            gamePlayModel.getConsole().append(
                    "The player " + gamePlayModel.getGameMap().getPlayerTurn().getName()
                            + " is moving " + armies + " from "
                            + attackCountry.getCountryName() + " to "
                            + defendCountry.getCountryName());
        }

        private void handleAttackSave() {
            handleSave("Attack", theAttackView);
        }

        // -------------------------------------------------------------------------
        // FORTIFICATION METHODS
        // -------------------------------------------------------------------------

        private void handleFortificationMove() {
            var player = gamePlayModel.getGameMap().getPlayerTurn();
            player.executeFortification();

            CountryModel from = theFortificationView.getFromCountryModel();
            CountryModel to = theFortificationView.getToCountryModel();
            int troops = theFortificationView.getNumOfTroops();

            if (val.checkIfValidMove(gamePlayModel.getGameMap(), from, to)) {
                gamePlayModel.getGameMap().setMovingArmies(troops, from, to);

                gamePlayModel.getConsole().append(
                        "The player " + player.getName()
                                + " is moving " + troops + " from " + from + " to " + to);
            }

            gamePlayModel.moveDeck();
            advancePlayerTurn();

            theFortificationView.hideView();
            new GamePlayController(gamePlayModel);
        }

        private void handleFortificationSave() {
            handleSave("Fortification", theFortificationView);
        }

        private void handleFortificationNext() {
            advancePlayerTurn();
            theFortificationView.hideView();
            new GamePlayController(gamePlayModel);
        }

        // -------------------------------------------------------------------------
        // SHARED UTILITIES
        // -------------------------------------------------------------------------

        private void handleSave(String phase, IView view) {
            gamePlayModel.setGamePhase(phase);
            String filename = JOptionPane.showInputDialog("File Name");

            try {
                SaveGame save = new SaveGame();
                if (filename == null || filename.isEmpty()) {
                    save.writeTOJSONFile(gamePlayModel, "file");
                } else {
                    save.writeTOJSONFile(gamePlayModel, filename);
                }

                JOptionPane.showMessageDialog(null, "Play has been saved");
                new WelcomeScreenController();
                view.hideView();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void advancePlayerTurn() {
            int index = gamePlayModel.getGameMap().getPlayerIndex();
            index = (index + 1) % gamePlayModel.getPlayers().size();

            gamePlayModel.getGameMap().setPlayerIndex(index);
            gamePlayModel.getPlayers().get(index).callObservers();
        }

}


