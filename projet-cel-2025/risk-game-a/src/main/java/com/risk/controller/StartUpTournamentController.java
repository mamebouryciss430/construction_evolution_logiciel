package com.risk.controller;

import com.risk.model.CountryModel;
import com.risk.model.GamePlayModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gursimransingh
 */
public class StartUpTournamentController {

    private GamePlayModel gamePlayModel;
    private int noOfPlayers;

    /** Regroupe toutes les données d’un joueur pour éviter les tableaux parallèles */
    private static class PlayerSetup {
        int countryCount;
        Color color;
        int totalArmies;
        int remainingArmies;
        List<CountryModel> ownedCountries = new ArrayList<>();
    }

    /** Liste des setups, un par joueur */
    private List<PlayerSetup> playerSetups = new ArrayList<>();

    public StartUpTournamentController() {}

    public StartUpTournamentController(GamePlayModel gamePlayModel, int noOfTurns) {
        this.gamePlayModel = gamePlayModel;
        this.noOfPlayers = gamePlayModel.getPlayers().size();

        allocateArmies();
        checkForOverallArmies();

        new GamePlayController(gamePlayModel, noOfTurns);
    }



    public void allocateArmies() {

        // Initialisation des PlayerSetup
        for (int i = 0; i < noOfPlayers; i++) {
            PlayerSetup setup = new PlayerSetup();
            setup.color = pickColor(i);
            playerSetups.add(setup);
        }

        // Attribution des pays
        for (CountryModel country : gamePlayModel.getGameMap().getCountries()) {

            int playerIndex = getRandomBetweenRange(1, noOfPlayers) - 1;
            PlayerSetup setup = playerSetups.get(playerIndex);

            String playerName = gamePlayModel.getPlayers().get(playerIndex).getName();
            country.setRulerName(playerName);
            country.setArmies(1);

            setup.countryCount++;
            setup.ownedCountries.add(country);
        }

        gamePlayModel.getConsoleText().append("Countries have been allocated randomly\n");

        // Calcul des armées
        for (int i = 0; i < noOfPlayers; i++) {
            PlayerSetup setup = playerSetups.get(i);

            if (setup.countryCount >= 3) {
                int bonus = (setup.countryCount / 3) - 1;
                setup.totalArmies = setup.countryCount + bonus;
                setup.remainingArmies = setup.totalArmies - setup.countryCount;
            } else {
                setup.totalArmies = setup.countryCount;
                setup.remainingArmies = 0;
            }
        }

        assignPlayerModel();
    }


    public void assignPlayerModel() {
        for (int i = 0; i < noOfPlayers; i++) {
            PlayerSetup setup = playerSetups.get(i);

            var player = gamePlayModel.getPlayers().get(i);
            player.setOwnedCountries(setup.ownedCountries);
            player.setColor(setup.color);
            player.setmyTroop(setup.totalArmies);
            player.setremainTroop(setup.remainingArmies);
        }
    }

    private Color pickColor(int i) {
        switch (i) {
            case 0: return Color.RED;
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.YELLOW;
            case 4: return Color.GRAY;
            default: return Color.BLACK;
        }
    }

    public int getRandomBetweenRange(double min, double max) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }



    private void checkForOverallArmies() {
        int playersWithRemaining = 0;

        for (var player : gamePlayModel.getPlayers()) {
            if (player.getremainTroop() != 0) {
                playersWithRemaining++;
            }
        }

        if (playersWithRemaining == 0) {
            gamePlayModel.getGameMap().setPlayerIndex(0);
            return;
        }

        // Distribution des armées restantes
        for (var player : gamePlayModel.getPlayers()) {
            while (player.getremainTroop() > 0) {
                for (CountryModel country : gamePlayModel.getGameMap().getCountries()) {
                    if (player.getremainTroop() == 0) break;

                    if (player.getName().equals(country.getRulerName())) {
                        country.setArmies(country.getArmies() + 1);
                        player.setremainTroop(player.getremainTroop() - 1);
                    }
                }
            }
        }
    }
}