package org.atlanmod.risk;

import java.util.List;

/**
 * Allows the creation of org.atlanmod.risk.Risk org.atlanmod.risk.Country objects.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class Country {

    private int armies;
	private boolean hasPlayer;
	
    private final String name;
    private Player occupant;
    private List<Country> adjacencies;

    public Country(String name) {
	
		this.name = name;
		hasPlayer = false;
		armies = 0;
		
	}

    /**
     * Used only when contstructing the country object, it should not be called after
     * the board is initialized
     **/
    public void addAdjacencies(List<Country> adjacencies) {
	
		this.adjacencies = adjacencies;
    }

    public String getName() {
		return name;
    }

    /**
     * When a player conquers a country the player object is set as the occupant of the 
	 * country
     **/
    public void setOccupant(Player occupant) {
		hasPlayer = true;
		this.occupant = occupant;
    }

    /**
     * Returns the player object who currently occupies the country
     **/
    public Player getOccupant()
	{
		return occupant;
    }
	
    /**
     * Used to set the number of armies currently stationed in this country
     **/
    public void setNumArmies(int numArmies) {
		armies = numArmies;
    }
	
	public void incrementArmies(int numArmies) {
		armies = armies + numArmies;
	}
	
	public void decrementArmies(int numArmies) {
		armies = armies - numArmies;
	}

    /**
     * Returns the number of armies currently stationed in this country
     **/
    public int getArmies() {
		return armies;
    }

    /**
     *  Returns a list of the country objects that are adjacent to this country on the baord
     **/
    public List<Country> getAdjacencies() {
		return adjacencies;
    }
	
	public boolean hasPlayer() {
		return hasPlayer;
	}
}
