package org.atlanmod.risk;

import java.util.List;

/**
 * Allows the creation of org.atlanmod.risk.Risk org.atlanmod.risk.Continent objects.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class Continent {

    private final String name;
    private final int bonusArmies;
    private final List<Country> countries;

    public Continent(String name, int bonusArmies, List<Country> memberCountries) {
		this.name = name;
		this.bonusArmies = bonusArmies;
		countries = memberCountries;

		    }

    public String getName() {
		return name;
    }

    /**
     *  Returns the number of bonus armies a player gets per round when the player controls this
     * continent
     **/
    public int getBonusArmies() {
		return bonusArmies;
    }

    /**
     * Retuens a list of the country objects that are on this continent
     **/
    public List<Country> getMemberCountries() {
		return countries;
    }
}
