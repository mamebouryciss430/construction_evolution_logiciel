package com.risk.strategy;

import com.risk.model.Country;
import com.risk.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AggressiveTest {

    /**
     * Test: Reinforcement phase should add armies to strongest country.
     */
    @Test
    void reinforcementPhaseAddsArmiesToStrongestCountry() {
        Player player = new Player("Aggressive");
        player.setArmyCount(5);

        Country weak = new Country("Weak");
        weak.setNoOfArmies(2);
        weak.setPlayer(player);

        Country strong = new Country("Strong");
        strong.setNoOfArmies(10);
        strong.setPlayer(player);

        ObservableList<Country> countries = FXCollections.observableArrayList(weak, strong);

        Aggressive aggressive = new Aggressive();
        aggressive.reinforcementPhase(countries, strong, player);

        assertEquals(15, strong.getNoOfArmies()); // le plus fort reçoit les armées
        assertEquals(0, player.getArmyCount());   // armées consommées
    }

    /**
     * Test: Reinforcement phase with empty country list should do nothing.
     */
    @Test
    void reinforcementPhaseWithEmptyCountryListDoesNothing() {
        Player player = new Player("Aggressive");
        player.setArmyCount(5);

        ObservableList<Country> countries = FXCollections.observableArrayList();

        Aggressive aggressive = new Aggressive();
        aggressive.reinforcementPhase(countries, null, player);

        assertEquals(5, player.getArmyCount()); // aucune armée consommée
    }

    /**
     * Test: Reinforcement phase with zero armies should not change country.
     */
    @Test
    void reinforcementPhaseWithZeroArmies() {
        Player player = new Player("Aggressive");
        player.setArmyCount(0);

        Country strong = new Country("Strong");
        strong.setNoOfArmies(8);
        strong.setPlayer(player);

        ObservableList<Country> countries = FXCollections.observableArrayList(strong);

        Aggressive aggressive = new Aggressive();
        aggressive.reinforcementPhase(countries, strong, player);

        assertEquals(8, strong.getNoOfArmies()); // aucune armée ajoutée
        assertEquals(0, player.getArmyCount());  // reste à 0
    }
}
