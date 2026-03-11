package com.risk.model.strategy;

import com.risk.model.CardModel;
import com.risk.model.CountryModel;
import com.risk.model.GamePlayModel;
import com.risk.utilities.Validation;

import java.util.ArrayList;

/**
 * @author gursimransingh
 */
public class RandomPlayerStrategy implements Strategy {


    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /** The val. */
    private Validation val = new Validation();

    private int index1, index2;

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gamePlayModel the game play model
     */
    public RandomPlayerStrategy(GamePlayModel gamePlayModel) {

        this.gamePlayModel = gamePlayModel;
    }

    /**
     * This method is called in reinforcement phase.
     *
     */
    public void reinforcement() {
        this.gamePlayModel.getConsole().append("Random - reinforcement");

        this.gamePlayModel.getGameMap().getPlayerTurn().setremainTroop(this.gamePlayModel.numberOfCountries()
                + this.gamePlayModel.continentCovered(gamePlayModel.getGameMap().getPlayerTurn()));
        if (gamePlayModel.getGameMap().getPlayerTurn().getOwnedCards().size() == 5) {
            this.gamePlayModel.getGameMap().getPlayerTurn()
                    .setremainTroop(5 + this.gamePlayModel.getGameMap().getPlayerTurn().getremainTroop());
            for (int j = 0; j < gamePlayModel.getGameMap().getPlayerTurn().getOwnedCards().size(); j++) {
                CardModel card = new CardModel();
                int cardID = this.gamePlayModel.getCards().get(j).getCardId();
                int cardValue = this.gamePlayModel.getCards().get(j).getCardValue();
                card.setCardId(cardID);
                card.setCardValue(cardValue);
                for (int i = 0; i < this.gamePlayModel.getPlayers().size(); i++) {
                    if (gamePlayModel.getGameMap().getPlayerTurn().getName()
                            .equals(gamePlayModel.getPlayers().get(i).getName())) {
                        this.gamePlayModel.getPlayers().get(i).removeCard(card);
                    }
                }
                this.gamePlayModel.getCards().add(card);
                this.gamePlayModel.getConsole().append("Card " + cardID + " is reimbursed");
            }
        }

        int selectedArmies = this.gamePlayModel.getGameMap().getPlayerTurn().getremainTroop();
        int index = getRandomBetweenRange(1,
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());

        this.gamePlayModel.setSelectedArmiesToCountries(selectedArmies,
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index - 1));
        this.gamePlayModel.getConsole().append("Country "
                + this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index - 1).getCountryName()
                + " got " + selectedArmies + " armies.");

    }

    /**
     * This method is called in fortification phase.
     */
    public void fortification() {
        this.gamePlayModel.getConsole().append("Random - fortification");

        index1 = getRandomBetweenRange(1, this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());
        index1 = index1 - 1;

        index2 = indexRandomvalues();
        index2 = index2 - 1;

        int armies = this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1).getArmies() - 1;
        if (val.checkIfValidMove(this.gamePlayModel.getGameMap(),
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1),
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2))) {
            this.gamePlayModel.getGameMap().setMovingArmies(armies,
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1),
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2));
            this.gamePlayModel.getConsole().append("From Country "
                    + this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1).getCountryName()
                    + " armies " + armies + " has been moved to "
                    + this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2).getCountryName());
        }

        this.gamePlayModel.moveDeck();

    }

    /**
     * This method is called in attack phase.
     */
    public void attack() {
        this.gamePlayModel.getConsole().append("Random - attack");
        index1 = getRandomBetweenRange(1, this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());
        index1 = index1 - 1;

        CountryModel attackCountry = this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1);
        this.gamePlayModel.getConsole().append("Attack country name - " + attackCountry.getCountryName());
        CountryModel defendCountry = new CountryModel();
        ArrayList<CountryModel> linkedCountry = new ArrayList<CountryModel>();
        for (int i = 0; i < this.gamePlayModel.getGameMap().getCountries().size(); i++) {
            if (attackCountry.getCountryName()
                    .equals(this.gamePlayModel.getGameMap().getCountries().get(i).getCountryName())) {
                linkedCountry = (ArrayList<CountryModel>) this.gamePlayModel.getGameMap().getCountries().get(i)
                        .getLinkedCountries();
            }
        }
        if (linkedCountry != null) {
            for (int i = 0; i < linkedCountry.size(); i++) {
                if (!this.gamePlayModel.getGameMap().getPlayerTurn().getName()
                        .equals(linkedCountry.get(i).getCountryName())) {
                    defendCountry = linkedCountry.get(i);
                }
            }
            this.gamePlayModel.getConsole().append("Defend country name - " + defendCountry.getCountryName());
            this.gamePlayModel.setDefeatedCountry(defendCountry);
            this.gamePlayModel.alloutStrike(attackCountry, defendCountry);
            int noOfArmiesToBeMoved = 0;
            for (int i = 0; i < this.gamePlayModel.getGameMap().getCountries().size(); i++) {
                if (attackCountry.getCountryName()
                        .equals(this.gamePlayModel.getGameMap().getCountries().get(i).getCountryName())) {
                    noOfArmiesToBeMoved = this.gamePlayModel.getGameMap().getCountries().get(i).getArmies() - 1;
                }
            }
            if (this.gamePlayModel.getArmyToMoveText()) {
                this.gamePlayModel.moveArmies(attackCountry, defendCountry, noOfArmiesToBeMoved);
                this.gamePlayModel.getConsole().append(" Armies " + noOfArmiesToBeMoved + " has been moved from "
                        + attackCountry.getCountryName() + "to " + defendCountry.getCountryName());
            }
        } else {
            this.gamePlayModel.getConsole().append(" there is no linked country");
        }
    }

    public int indexRandomvalues() {
        boolean flag = false;
        index2 = getRandomBetweenRange(1, this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());

        if (index1 == index2) {
            flag = true;
        }
        while (flag == true) {
            index2 = getRandomBetweenRange(1,
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());
            if (index1 != index2) {
                flag = false;
            }
        }
        return index2;
    }

    /**
     * This method gives the Random generation of numbers within two values.
     *
     * @param min the min
     * @param max the max
     * @return the random between range
     */
    public int getRandomBetweenRange(double min, double max) {
        int x = (int) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }
}
