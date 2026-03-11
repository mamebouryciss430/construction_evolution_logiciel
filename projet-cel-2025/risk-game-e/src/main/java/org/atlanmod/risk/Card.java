package org.atlanmod.risk;

/**
 * Allows the creation of org.atlanmod.risk.Risk org.atlanmod.risk.Card objects.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public final class Card implements org.atlanmod.risk.core.Card {

    private final String type;
    private final Country country;

    public Card( String type, Country country ) {
		this.type = type;
		this.country = country;
    }
	
	public String getName() {
		return country.getName() + ", " + type;
	}

    public String getType() {
		return type;
    }

    public Country getCountry() {
		return country;
    }
}
