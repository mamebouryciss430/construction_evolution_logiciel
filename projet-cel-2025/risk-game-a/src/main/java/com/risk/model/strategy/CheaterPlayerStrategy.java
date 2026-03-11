package com.risk.model.strategy;

import com.risk.model.CountryModel;
import com.risk.model.GamePlayModel;
import com.risk.utilities.Validation;

/**
 * In CheaterPlayerStrategy, the data flow into model object and updates the
 * view whenever data changes.
 * @author gursimransingh
 */
public class CheaterPlayerStrategy implements Strategy {


    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /** The val. */
    private Validation val = new Validation();

    /** The index 2. */
    private int index1, index2;

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gamePlayModel the game play model
     */
    public CheaterPlayerStrategy(GamePlayModel gamePlayModel) {

        this.gamePlayModel = gamePlayModel;
    }

    /**
     * This method is called in reinforcement phase.
     *
     */
    public void reinforcement() {
        this.gamePlayModel.getConsole().append("Cheater - reinforcement");

        for (int j = 0; j < this.gamePlayModel.getPlayers().size(); j++) {
            if (this.gamePlayModel.getPlayers().get(j).getName()
                    .equals(this.gamePlayModel.getGameMap().getPlayerTurn().getName())) {
                for (int i = 0; i < this.gamePlayModel.getPlayers().get(j).getOwnedCountries().size(); i++) {
                    this.gamePlayModel.getPlayers().get(j).getOwnedCountries().get(i).setArmies(
                            this.gamePlayModel.getPlayers().get(j).getOwnedCountries().get(i).getArmies() * 2);
                    this.gamePlayModel.getConsole().append("Country "
                            + this.gamePlayModel.getPlayers().get(j).getOwnedCountries().get(i).getCountryName()
                            + " got " + this.gamePlayModel.getPlayers().get(j).getOwnedCountries().get(i).getArmies()
                            + " armies.");
                }
            }
        }
        for (int i = 0; i < this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size(); i++) {
            this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(i).setArmies(
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(i).getArmies() * 2);
            for (int j = 0; j < this.gamePlayModel.getGameMap().getCountries().size(); j++) {
                if (this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(i).getCountryName()
                        .equals(this.gamePlayModel.getGameMap().getCountries().get(j).getCountryName())) {
                    this.gamePlayModel.getGameMap().getCountries().get(j)
                            .setArmies(this.gamePlayModel.getGameMap().getCountries().get(j).getArmies() * 2);
                    this.gamePlayModel.getConsole()
                            .append("Country " + this.gamePlayModel.getGameMap().getCountries().get(j).getCountryName()
                                    + " got " + this.gamePlayModel.getGameMap().getCountries().get(j).getArmies()
                                    + " armies.");
                }
            }
        }
    }

    /**
     * This method is called in fortification phase.
     */
    public void fortification() {

        this.gamePlayModel.getConsole().append("Cheater - fortification");
        index1 = getRandomBetweenRange(1, this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());
        index1 = index1 - 1;

        index2 = indexRandomvalues();
        index2 = index2 - 1;

        int armies = (this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1).getArmies() - 1)
                * 2;
        if (val.checkIfValidMove(this.gamePlayModel.getGameMap(),
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1),
                this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2))) {
            this.gamePlayModel.getGameMap().setMovingArmies(armies,
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1),
                    this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2));
            this.gamePlayModel.getConsole().append("From Country "
                    + this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1).getCountryName()
                    + "armies " + armies + " has been moved to "
                    + this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index2).getCountryName());
        }
    }

    /**
     * This method is called in attack phase.
     */
    public void attack() {
        this.gamePlayModel.getConsole().append("Cheater - attack");
        index1 = getRandomBetweenRange(1, this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().size());
        index1 = index1 - 1;

        CountryModel attackCountry = this.gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries().get(index1);
        this.gamePlayModel.getConsole().append("Attack Country - " + attackCountry.getCountryName());
        for (int i = 0; i < attackCountry.getLinkedCountries().size(); i++) {
            for (int j = 0; j < this.gamePlayModel.getGameMap().getCountries().size(); j++) {
                if (attackCountry.getLinkedCountries().get(i).getCountryName()
                        .equals(this.gamePlayModel.getGameMap().getCountries().get(j).getCountryName())) {
                    if (this.gamePlayModel.getGameMap().getPlayerTurn().getName()
                            .equals(this.gamePlayModel.getGameMap().getCountries().get(j).getRulerName())) {
                        continue;
                    } else {
                        for (int k = 0; k < this.gamePlayModel.getPlayers().size(); k++) {
                            if (this.gamePlayModel.getGameMap().getCountries().get(j).getRulerName()
                                    .equals(this.gamePlayModel.getPlayers().get(k).getName())) {
                                this.gamePlayModel.getPlayers().get(k)
                                        .defend(this.gamePlayModel.getGameMap().getCountries().get(j));
                                this.gamePlayModel.getConsole()
                                        .append(" Country "
                                                + this.gamePlayModel.getGameMap().getCountries().get(j).getCountryName()
                                                + "is removed from player "
                                                + this.gamePlayModel.getPlayers().get(k).getName());
                            }
                            if (this.gamePlayModel.getGameMap().getPlayerTurn().getName()
                                    .equals(this.gamePlayModel.getPlayers().get(k).getName())) {
                                this.gamePlayModel.getPlayers().get(k)
                                        .attacked(this.gamePlayModel.getGameMap().getCountries().get(j));
                                this.gamePlayModel.getConsole()
                                        .append(" Country "
                                                + this.gamePlayModel.getGameMap().getCountries().get(j).getCountryName()
                                                + "is added to player "
                                                + this.gamePlayModel.getPlayers().get(k).getName());
                            }
                        }
                        this.gamePlayModel.getGameMap().getPlayerTurn()
                                .attacked(this.gamePlayModel.getGameMap().getCountries().get(j));
                        this.gamePlayModel.getGameMap().getCountries().get(j)
                                .setRulerName(this.gamePlayModel.getGameMap().getPlayerTurn().getName());
                    }
                }
            }
        }

    }

    /**
     * Index randomvalues.
     *
     * @return an index the int
     */
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
