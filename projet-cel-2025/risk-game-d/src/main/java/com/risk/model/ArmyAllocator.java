package com.risk.model;

import java.util.*;

public final class ArmyAllocator {

    private ArmyAllocator() {}

    /**
     * Calcul du nombre d'armées initiales selon le nombre de joueurs
     */
    public static int calculateInitialArmies(int playersCount) {
        switch (playersCount) {
            case 3: return 35;
            case 4: return 30;
            case 5: return 25;
            case 6: return 20;
            default: throw new IllegalArgumentException("Unsupported number of players: " + playersCount);
        }
    }

    /**
     * Assigner les armées initiales à chaque joueur
     */
    public static void assignInitialArmies(List<Player> players, Player notifier) {
        int armiesPerPlayer = calculateInitialArmies(players.size());
        for (Player player : players) {
            player.setArmyCount(armiesPerPlayer);
            NotificationManager.notify(notifier, armiesPerPlayer + " armies assigned to " + player.getName());
        }
    }

    /**
     * Calculer les renforts pour un joueur
     */
    public static int calculateTurnReinforcements(Player player, Player notifier) {
        int numberOfArmies = Math.max(3, player.getPlayerCountries().size() / 3);

        Set<Continent> continentsOwned = new HashSet<>();
        for (Country country : player.getPlayerCountries()) {
            continentsOwned.add(country.getPartOfContinent());
        }

        for (Continent continent : continentsOwned) {
            if (player.getPlayerCountries().containsAll(continent.getListOfCountries())) {
                numberOfArmies += continent.getControlValue();
            }
        }

        NotificationManager.notify(notifier, "Player " + player.getName() + " has been assigned " + numberOfArmies + " armies.");

        return numberOfArmies;
    }
}

