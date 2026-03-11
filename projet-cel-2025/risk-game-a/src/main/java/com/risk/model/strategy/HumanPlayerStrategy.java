package com.risk.model.strategy;

import com.risk.model.GamePlayModel;

/**
 * In HumanPlayerStrategy, the data flow into model object and updates the
 * view whenever data changes.
 *
 * @author gursimransingh
 */
public class HumanPlayerStrategy implements Strategy{


    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gamePlayModel the game play model
     */
    public HumanPlayerStrategy(GamePlayModel gamePlayModel) {

        this.gamePlayModel = gamePlayModel;
        this.gamePlayModel.getConsoleText()
                .append("Initiating for " + gamePlayModel.getGameMap().getPlayerTurn().getName());
    }

    /**
     * This method is called in reinforcement phase.
     *
     */
    public void reinforcement() {
        System.out.println("Human - reinforcement");
        this.gamePlayModel.getConsoleText().setLength(0);
        this.gamePlayModel.callObservers();
        this.gamePlayModel.getConsoleText()
                .append("Initiating Reinforcement for " + gamePlayModel.getGameMap().getPlayerTurn().getName());
        this.gamePlayModel.getConsole()
                .append("Initiating Reinforcement for " + gamePlayModel.getGameMap().getPlayerTurn().getName());
        this.gamePlayModel.getGameMap().getPlayerTurn().setremainTroop(this.gamePlayModel.numberOfCountries()
                + this.gamePlayModel.continentCovered(gamePlayModel.getGameMap().getPlayerTurn()));
        if (gamePlayModel.getGameMap().getPlayerTurn().getOwnedCards().size() > 0) {
            this.gamePlayModel.getConsoleText().append("\n Reinforcement View - Please find the list of Cards: \n");
            for (int i = 0; i < gamePlayModel.getGameMap().getPlayerTurn().getOwnedCards().size(); i++) {
                this.gamePlayModel.getConsoleText()
                        .append(gamePlayModel.getGameMap().getPlayerTurn().getOwnedCards().get(i).getCardId() + "\n ");
            }
            this.gamePlayModel.getGameMap().getPlayerTurn().setShowReinforcementCard(true);
        }
    }

    /**
     * This method is called in fortification phase.
     */
    public void fortification() {
        System.out.println("Human - fortification");
        this.gamePlayModel.getConsole()
                .append("Initiating fortification " + gamePlayModel.getGameMap().getPlayerTurn().getName());

        this.gamePlayModel.getConsoleText().setLength(0);
        this.gamePlayModel.getConsoleText()
                .append("Initiating Fortification for " + gamePlayModel.getGameMap().getPlayerTurn().getName());

    }

    /**
     * This method is called in attack phase.
     */
    public void attack() {
        System.out.println("Human - attack");
        this.gamePlayModel.getConsole()
                .append("Initiating attack " + gamePlayModel.getGameMap().getPlayerTurn().getName());

        this.gamePlayModel.getConsoleText().setLength(0);
        this.gamePlayModel.getConsoleText()
                .append("Initiating " + gamePlayModel.getGameMap().getPlayerTurn().getName() + "'s attack");
    }

}
