package com.risk.strategy;

import java.util.ArrayList;
import java.util.List;


import com.risk.controller.GamePlayController;
import com.risk.model.Country;
import com.risk.model.Player;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Benevolent.
 * 
 * @author Farhan Shaheen
 * @author Neha Pal
 *
 */
public class BenevolentTest {

	/** Object for Country class */
	private Country country;

	/** Object for Country class */
	private Country adjCountry1;

	/** Object for Country class */
	private Country adjCountry2;

	/** ArrayList to hold adjacent countries */
	private ArrayList<Country> adjacentCountries;

	/** Object for Player class */
	private Player player;

	/** Object for Benevolent class */
	private Benevolent benevolent;

	/** Object for GamePlayController class */
	private GamePlayController gamePlayController;

	private List<Country> list;

	/**
	 * Set up the initial objects for Benevolent class
	 * 
	 */
	@BeforeEach
	public void initialize() {
		player = new Player();
		country = new Country("India");
		country.setPlayer(player);

		list = new ArrayList<Country>();
		list.add(country);

		adjCountry1 = new Country("China");
		adjCountry1.setPlayer(player);

		adjCountry2 = new Country("Malaysia");
		adjCountry2.setPlayer(player);

		adjacentCountries = new ArrayList<Country>();
		adjacentCountries.add(adjCountry1);
		adjacentCountries.add(adjCountry2);

		country.setAdjacentCountries(adjacentCountries);

		gamePlayController = new GamePlayController();
		benevolent = new Benevolent(gamePlayController);
	}

	/**
	 * Test to check strongest adjacent country.
	 */
	@Test
	public void getStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(3);
		assertEquals(adjCountry1,benevolent.getStrongestAdjacentCountry(country));
	}

	/**
	 * Test to check strongest adjacent country returns null
	 */
	@Test
	public void getNullStrongestAdjacentCountryTest() {
		adjCountry1.setNoOfArmies(1);
		assertEquals(null,benevolent.getStrongestAdjacentCountry(country));
	}

	/**
	 * Test to check check and find the weakest country if
     * no adjacent country to fortify
	 */
	@Test
	public void checkAndFindWeakestIfNoAdjacentCountryToFortifyTest() {
		list.get(0).setNoOfArmies(3);
		adjCountry1.setNoOfArmies(3);
		// TODO: Replace prints with logging or other
		// System.out.println(benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
		assertEquals(list.get(0), benevolent.checkAndFindWeakestIfNoAdjacentCountryToFortify(list));
	}

    /**
     * Test: Reinforcement phase should add armies to weakest country.
     */
    @Test
    public void reinforcementPhaseAddsArmiesToWeakestCountry() {
        player.setArmyCount(5);

        Country weak = new Country("Weak");
        weak.setNoOfArmies(2);
        weak.setPlayer(player);

        Country strong = new Country("Strong");
        strong.setNoOfArmies(10);
        strong.setPlayer(player);

        List<Country> ownedCountries = new ArrayList<>();
        ownedCountries.add(weak);
        ownedCountries.add(strong);

        benevolent.reinforcementPhase(FXCollections.observableArrayList(ownedCountries), weak, player);

        assertEquals(7, weak.getNoOfArmies()); // le plus faible reçoit les armées
        assertEquals(0, player.getArmyCount()); // armées consommées
    }

    /**
     * Test: Reinforcement phase with empty country list should do nothing.
     */
    @Test
    public void reinforcementPhaseWithEmptyCountryListDoesNothing() {
        player.setArmyCount(5);
        List<Country> emptyList = new ArrayList<>();

        benevolent.reinforcementPhase(FXCollections.observableArrayList(emptyList), null, player);

        assertEquals(5, player.getArmyCount()); // aucune armée consommée
    }

    /**
     * Test: Reinforcement phase with zero armies should not change country.
     */
    @Test
    public void reinforcementPhaseWithZeroArmies() {
        player.setArmyCount(0);

        Country weak = new Country("Weak");
        weak.setNoOfArmies(3);
        weak.setPlayer(player);

        List<Country> ownedCountries = new ArrayList<>();
        ownedCountries.add(weak);

        benevolent.reinforcementPhase(FXCollections.observableArrayList(ownedCountries), weak, player);

        assertEquals(3, weak.getNoOfArmies()); // aucune armée ajoutée
        assertEquals(0, player.getArmyCount()); // reste à 0
    }


}


