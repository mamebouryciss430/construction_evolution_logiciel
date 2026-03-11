package org.atlanmod.risk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Allows the creation of org.atlanmod.risk.Risk player objects.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class Player implements org.atlanmod.risk.core.Player {


	private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

	private final boolean isAI;

    private int armies;
	private int turnInCount;
	private final int index;

    private final String name;
		
    private final HashMap<String, Country> countriesHeld;

	private final Hand hand;

    public Player(String name, int armies, int index, boolean isAI) {
	
		this.name = name;
		this.armies = armies;
		this.index = index;
		this.isAI = isAI;

        countriesHeld = new HashMap<>();

        hand = new Hand();
		
		turnInCount = 0;
    }
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getArmies() {
		return armies;
	}
	
	public boolean getAI() {
		return isAI;
	}
	
    /**
     * Decreases the count of the number of numArmies the player has on the board
     * This count has to reflect the actual number of numArmies the players has on 
     * the board
     **/
	public void decrementArmies(int numArmies) {

		armies -= numArmies;

		LOGGER.log(Level.INFO,
				"{0} has {1} reinforcements remaining.",
				new Object[]{name, armies});
	}

	/**
     * Increases the count of the number of numArmies the player has on the board
     * This count has to reflect the actual number of numArmies the players has on 
     * the board
     **/
    public void incrementArmies(int numArmies) {
	
		armies += numArmies;
			LOGGER.log(Level.INFO,
				"{0} has gained {1} reinforcements remaining.Total available: {2}",
				new Object[]{name, numArmies,armies});

    }

    /**
     * When a player conquers a country the country needs to be added to a data structure
     * that keeps track of all the countries the player occupies
     **/
    public void addCountry(Country country) {
		LOGGER.log(Level.INFO,
				"{0} now occupies {1} !",
				new Object[]{name, country.getName()});
				countriesHeld.put(country.getName(), country);
    }


    /**
     * When a player loses a country, the country must be removed from the data structure
     * tracking which countries the player occupies
     **/
    public void removeCountry(String countryName) {

		LOGGER.log(Level.INFO,
				"{0} no longer occupies {1} !",
				new Object[]{name, countryName});

		countriesHeld.remove(countryName);
    }


    /**
     * Adds a risk card to the players hand
     **/
    public void addRiskCard(Card riskCard) {
	
		hand.add(riskCard);
    }

    /**
     * Removed a set of risk cards from the players hand to reflect risk cards being turned in
     **/
    public void removeCards(int[] cardsTurnedInIndex) {
	
		hand.removeCardsFromHand(cardsTurnedInIndex[0], cardsTurnedInIndex[1], cardsTurnedInIndex[2]);
    }
	
	public List<Country> getOwnedCountries() {
	
		return new ArrayList<>(countriesHeld.values());
	}
	
	public int getTurnInCount() {
	
		turnInCount++;
		return turnInCount;
	}
	
	public List<Card> getHand() {
	
		return hand.getCards();
	}
	
	public Hand getHandObject() {
		
		return hand;
	}
	
	public boolean mustTurnInCards() {
	
		return hand.mustTurnInCards();
	}
}