package com.risk.strategy;

import com.risk.model.Continent;
import com.risk.model.Country;
import com.risk.model.Player;
import com.risk.services.MapIO;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBehaviourTest {

    @Test
    void testFortificationPhaseValidWhenAdjacentCountryOwnedByPlayer() {
        Player player = new Player("TestPlayer");

        Country india = new Country("India");
        india.setPlayer(player);
        india.setNoOfArmies(5);

        Country china = new Country("China");
        china.setPlayer(player);

        india.getAdjacentCountries().add(china);

        Continent continent = new Continent("Asia", 5);
        continent.getListOfCountries().add(india);
        continent.getListOfCountries().add(china);

        MapIO mapIO = new MapIO();
        mapIO.getMapGraph().getContinents().put("Asia", continent);

        PlayerBehaviour behaviour = new Benevolent(); // n'importe quelle sous-classe
        assertTrue(behaviour.isFortificationPhaseValid(mapIO, player));
    }

    @Test
    void testFortificationPhaseInvalidWhenNotEnoughArmies() {
        Player player = new Player("TestPlayer");

        Country india = new Country("India");
        india.setPlayer(player);
        india.setNoOfArmies(1); // armées insuffisantes

        Country china = new Country("China");
        china.setPlayer(player);

        india.getAdjacentCountries().add(china);

        Continent continent = new Continent("Asia", 5);
        continent.getListOfCountries().add(india);
        continent.getListOfCountries().add(china);

        MapIO mapIO = new MapIO();
        mapIO.getMapGraph().getContinents().put("Asia", continent);

        PlayerBehaviour behaviour = new Benevolent();
        assertFalse(behaviour.isFortificationPhaseValid(mapIO, player));
    }

    @Test
    void testFortificationPhaseInvalidWhenNoAdjacentCountryOwned() {
        Player player = new Player("TestPlayer");

        Country india = new Country("India");
        india.setPlayer(player);
        india.setNoOfArmies(5);

        Country china = new Country("China");
        Player otherPlayer = new Player("Other");
        china.setPlayer(otherPlayer); // appartient à un autre joueur

        india.getAdjacentCountries().add(china);

        Continent continent = new Continent("Asia", 5);
        continent.getListOfCountries().add(india);
        continent.getListOfCountries().add(china);

        MapIO mapIO = new MapIO();
        mapIO.getMapGraph().getContinents().put("Asia", continent);

        PlayerBehaviour behaviour = new Benevolent();
        assertFalse(behaviour.isFortificationPhaseValid(mapIO, player));
    }
}
