package com.risk.model.strategy;

import com.risk.model.CountryModel;
import com.risk.model.GamePlayModel;
import com.risk.utilities.Validation;

import java.util.ArrayList;

/**
 * In BenevolentPlayerStrategy, the data flow into model object and updates
 * the view whenever data changes.
 *
 * @author Namita
 * @version 1.0.0
 *
 */
public class BenevolentPlayerStrategy implements Strategy {

    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /** The val. */
    private Validation val = new Validation();

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gamePlayModel the game play model
     */
    public BenevolentPlayerStrategy(GamePlayModel gamePlayModel) {

        this.gamePlayModel = gamePlayModel;
    }

    /**
     * This method is called in reinforcement phase.
     *
     */
    public void reinforcement() {
        this.gamePlayModel.getConsole().append("Benevolent - reinforcement");
        this.gamePlayModel.getConsole()
                .append("Initiating Reinforcement for " + gamePlayModel.getGameMap().getPlayerTurn().getName());
        this.gamePlayModel.getGameMap().getPlayerTurn().setremainTroop(this.gamePlayModel.numberOfCountries()
                + this.gamePlayModel.continentCovered(gamePlayModel.getGameMap().getPlayerTurn()));
        int value = this.gamePlayModel.numberOfCountries()
                + this.gamePlayModel.continentCovered(gamePlayModel.getGameMap().getPlayerTurn());
        this.gamePlayModel.getConsole().append("Number of armies assigned to the player: " + value);

        // select Weakest Country
        ArrayList<CountryModel> listofcountry = this.gamePlayModel.selectWeakestCountry(
                (ArrayList<CountryModel>) gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries());

        if (listofcountry.size() > 3) {
            int selectedArmies = Math.round(this.gamePlayModel.getGameMap().getPlayerTurn().getremainTroop() / 3);
            int index;
            for (int i = 0; i < 3; i++) {
                index = getRandomBetweenRange(1, listofcountry.size());
                this.gamePlayModel.setSelectedArmiesToCountries(selectedArmies, listofcountry.get(index - 1));
                this.gamePlayModel.getConsole().append("Number of armies assigned to the"
                        + listofcountry.get(index - 1).getCountryName() + " is " + selectedArmies);

            }

        } else {
            int selectedArmies = Math
                    .round(this.gamePlayModel.getGameMap().getPlayerTurn().getremainTroop() / listofcountry.size());
            for (int i = 0; i < listofcountry.size(); i++) {
                this.gamePlayModel.setSelectedArmiesToCountries(selectedArmies, listofcountry.get(i));
                this.gamePlayModel.getConsole().append("Number of armies assigned to the"
                        + listofcountry.get(i).getCountryName() + " is " + selectedArmies);
            }
        }
    }

    /**
     * This method is called in fortification phase.
     */
    public void fortification() {
        this.gamePlayModel.getConsole().append("Benevolent - fortification");
        ArrayList<CountryModel> listofcountry = this.gamePlayModel
                .sortCountry((ArrayList<CountryModel>) gamePlayModel.getGameMap().getPlayerTurn().getOwnedCountries());

        // BFS
        int i = 0;
        int j = listofcountry.size() - 1;
        boolean flag = false;
        int armies;
        while (flag == false) {
            if (val.checkIfValidMove(this.gamePlayModel.getGameMap(), listofcountry.get(i), listofcountry.get(j))) {
                flag = true;
                armies = listofcountry.get(j).getArmies() - 1;
                System.out.println("listofcountry " + i + " " + j);
                this.gamePlayModel.getGameMap().setMovingArmies(armies, listofcountry.get(j), listofcountry.get(i));
                this.gamePlayModel.getConsole().append("From " + listofcountry.get(j).getCountryName() + " armies "
                        + armies + " has been moved to " + listofcountry.get(i).getCountryName());
            }
            i++;
            j--;
            if (i > listofcountry.size() / 2) {
                flag = true;
            }
        }
    }

    /**
     * This method is called in attack phase.
     */
    public void attack() {
        this.gamePlayModel.getConsole().append("Benevolent - attack");
        this.gamePlayModel.getConsole().append("Benevolent passes his attack");
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
