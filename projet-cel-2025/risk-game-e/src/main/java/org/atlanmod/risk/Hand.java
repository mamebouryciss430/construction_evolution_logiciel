package org.atlanmod.risk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hand {


	private final ArrayList<Card> cards;

	/**
	 * No-arg constructor. Instantiate org.atlanmod.risk.Deck.
	 **/
	public Hand() {
	
		cards = new ArrayList<>();
	}

	/**
	 * Adds the card to the hand 
	 **/
	public void add(Card card) {
	
		cards.add(card);
	}
	/**
	 * Removes the cards at the given indices from the hand
	 **/
	public void removeCardsFromHand(int index1, int index2, int index3) {
		if (!canTurnInCards(index1, index2, index3)) {
			return;
		}

		int[] indices = { index1, index2, index3 };
		Arrays.sort(indices);

		cards.remove(indices[2]);
		cards.remove(indices[1]);
		cards.remove(indices[0]);

	}

	/**
	 * returns true if the player can turn in cards
	 **/
	public boolean canTurnInCards(int index1, int index2, int index3) {

		if (cards.size() < 3) {
			return false;
		}

		boolean allSame = cards.get(index1).getType().equals(cards.get(index2).getType())
				&& cards.get(index1).getType().equals(cards.get(index3).getType());

		boolean allDifferent = !cards.get(index1).getType().equals(cards.get(index2).getType())
				&& !cards.get(index1).getType().equals(cards.get(index3).getType())
				&& !cards.get(index2).getType().equals(cards.get(index3).getType());

		return allSame || allDifferent;
	}

	/**
	 * Returns true if the player must turn in cards
	 **/
	public boolean mustTurnInCards() {
        boolean condition;

        condition = cards.size() >= 5;
		return condition;
	}

	/**
	 * Returns the hand
	 **/
	public List<Card> getCards() {
		return cards;
	}

}