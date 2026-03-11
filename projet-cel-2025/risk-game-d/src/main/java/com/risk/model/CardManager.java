package com.risk.model;

import java.util.List;

public final class CardManager {

    private CardManager() {}

    /**
     * Échange des cartes contre des armées
     */
    public static void exchangeCards(Player currentPlayer, List<Card> selectedCards, int numberOfCardSetExchanged, Player notifier) {
        int armiesGained = 5 * numberOfCardSetExchanged;
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + armiesGained);
        NotificationManager.notify(notifier, currentPlayer.getName() + " exchanged 3 cards for " + armiesGained + " armies.");

        for (Card card : selectedCards) {
            if (currentPlayer.getPlayerCountries().contains(card.getCountry())) {
                card.getCountry().setNoOfArmies(card.getCountry().getNoOfArmies() + 2);
                NotificationManager.notify(notifier, currentPlayer.getName() + " got extra 2 armies on " + card.getCountry().getName());
                break;
            }
        }
    }
}

