package org.atlanmod.risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Allows the creation of the org.atlanmod.risk.Risk deck containing the 42 org.atlanmod.risk.Risk cards.
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class Deck {

    private ArrayList<Card> deck;


	/**
	* Creates all 42 cards, one for each territory. Each card has either 
	* a type of Infantry, Cavalry, or Artillery. Ensure that the number of
	* Infantry, Cavalry, and Artillery are the same
	**/
	public Deck (List<Country> countries) {
	
		Collections.shuffle(countries);
		
		//Types of cards
        String[] typesArray = new String[]{"Infantry", "Cavalry", "Artillery"};
		
		deck = new ArrayList<>();

        int i;
        for (i = 0; i < countries.size(); i++) {
		// Add new cards to deck
			deck.add(new Card(typesArray[i / 14], countries.get(i)));
		}
		Collections.shuffle(deck);
	}

	/**
	* Removes a card from the deck and return it
	**/
	public Card draw() {
  Card drawCard;
	
		drawCard = deck.get(0);
		deck.remove(0);
		
		return drawCard;
	}

	/**
	* Add a card to the deck
	**/
	public void add(Card card) {
	
		deck.add(card);
	}

	/**
	* Shuffle the deck of cards
	**/
	public void shuffle() {
	
		Collections.shuffle(deck);
	}
}
