package com.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PlayerVictoryManager {

    private PlayerVictoryManager() {}

    /**
     * Vérifie quels joueurs ont perdu (plus de pays)
     */
    public static List<Player> playersWhoLost(List<Player> players, Player currentPlayer, Player notifier) {
        List<Player> lost = new ArrayList<>();
        for (Player p : players) {
            if (p.getPlayerCountries().isEmpty()) {
                currentPlayer.getCardList().addAll(p.getCardList());
                lost.add(p);
                NotificationManager.notify(notifier, "Player " + p.getName() + " has lost the game.");
            }
        }
        return lost;
    }

    /**
     * Vérifie s’il y a un vainqueur (un seul joueur restant)
     */
    public static Optional<Player> winnerIfAny(List<Player> players, Player notifier) {
        if (players.size() == 1) {
            Player winner = players.get(0);
            NotificationManager.notify(notifier, "Player " + winner.getName() + " has won the game!");
            return Optional.of(winner);
        }
        return Optional.empty();
    }
}
