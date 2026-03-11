package com.risk.model;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.util.List;
import java.util.Random;
import com.risk.services.MapIO;
import com.risk.strategy.Human;

public final class PlayerPhaseManager {

    private PlayerPhaseManager() {}

    /**
     * Phase de renfort
     */
    public static void reinforcementPhase(ObservableList<Country> countries, Country country, List<Player> playerList, Player currentPlayer, Player notifier) {
        currentPlayer.getPlayerBehaviour().reinforcementPhase(countries, country, currentPlayer);
        if (currentPlayer.getArmyCount() <= 0 && playerList.size() > 1) {
            NotificationManager.notify(notifier, "Reinforcement Phase Ended");
            notifier.hasChanged();
            notifier.notifyObservers("Attack");
        }
    }

    /**
     * Phase d’attaque
     */
    public static void attackPhase(ListView<Country> attackingCountries, ListView<Country> defendingCountries, List<Player> playerList, Player currentPlayer, Player notifier) {
        currentPlayer.getPlayerBehaviour().attackPhase(attackingCountries, defendingCountries, currentPlayer);
        if (!(currentPlayer.getPlayerBehaviour() instanceof Human) && playerList.size() > 1) {
            notifier.hasChanged();
            notifier.notifyObservers(currentPlayer.getName() + " (" + currentPlayer.getPlayerType() + ") is skipping attack.");
            notifier.hasChanged();
            notifier.notifyObservers("skipAttack");
        }
    }

    /**
     * Phase de fortification
     */
    public static void fortificationPhase(ListView<Country> selectedCountries, ListView<Country> adjCountries, List<Player> playerList, Player currentPlayer, Player notifier) {
        boolean success = currentPlayer.getPlayerBehaviour().fortificationPhase(selectedCountries, adjCountries, currentPlayer);
        if (success && playerList.size() > 1) {
            notifier.hasChanged();
            notifier.notifyObservers("Fortification phase ended.");
            notifier.hasChanged();
            notifier.notifyObservers("Reinforcement");
        }
    }

    /**
     * Vérification de validité de la fortification
     */
    public static boolean isFortificationPhaseValid(MapIO mapIO, Player currentPlayer, Player notifier) {
        boolean flag = currentPlayer.getPlayerBehaviour().isFortificationPhaseValid(mapIO, currentPlayer);
        notifier.hasChanged();
        notifier.notifyObservers(flag ? "Fortification" : "noFortificationMove");
        return flag;
    }

    /**
     * Placement des armées au startup
     */
    public static void placeArmyOnCountry(ListView<Country> selectedCountryList, List<Player> gamePlayerList, Player currentPlayer, Player notifier) {
        if (currentPlayer.getPlayerBehaviour() instanceof Human) {
            int playerArmies = currentPlayer.getArmyCount();
            if (playerArmies > 0) {
                Country country = selectedCountryList.getSelectionModel().getSelectedItem();
                if (country == null) {
                    country = selectedCountryList.getItems().get(0);
                }
                country.setNoOfArmies(country.getNoOfArmies() + 1);
                currentPlayer.setArmyCount(playerArmies - 1);
            }
        } else {
            automaticAssignPlayerArmiesToCountry(currentPlayer, notifier);
        }

        boolean armiesExhausted = areAllArmiesExhausted(gamePlayerList);
        if (armiesExhausted) {
            notifier.hasChanged();
            notifier.notifyObservers("StartUp Phase Completed.");
            notifier.hasChanged();
            notifier.notifyObservers("FirstAttack");
        } else {
            notifier.hasChanged();
            notifier.notifyObservers("placeArmyOnCountry");
        }
    }

    /**
     * Placement automatique des armées
     */
    public static void automaticAssignPlayerArmiesToCountry(Player currentPlayer, Player notifier) {
        if (currentPlayer.getArmyCount() > 0) {
            Country country = currentPlayer.getPlayerCountries()
                    .get(new Random().nextInt(currentPlayer.getPlayerCountries().size()));
            country.setNoOfArmies(country.getNoOfArmies() + 1);
            currentPlayer.setArmyCount(currentPlayer.getArmyCount() - 1);
            notifier.hasChanged();
            notifier.notifyObservers("Country " + country.getName() + " has been assigned one army.");
        }
    }

    /**
     * Vérifie si tous les joueurs ont épuisé leurs armées
     */
    public static boolean areAllArmiesExhausted(List<Player> allPlayers) {
        return allPlayers.stream().allMatch(p -> p.getArmyCount() == 0);
    }

    /**
     * Vérifie si le joueur peut attaquer
     */
    public static boolean playerCanAttack(ListView<Country> attackingCountries, Player currentPlayer, Player notifier) {
        boolean canAttack = currentPlayer.getPlayerBehaviour().playerCanAttack(attackingCountries);
        if (!canAttack) {
            notifier.hasChanged();
            notifier.notifyObservers("checkIfFortificationPhaseValid");
        }
        return canAttack;
    }
}
