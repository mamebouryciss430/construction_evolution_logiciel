package com.risk.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Card.
 * 
 * @author Farhan Shaheen
 *
 */
public class CardTest {

	/** Object for Card class */
    private Card card;

    /** ArrayList to hold list of cards in the game */
    private ArrayList<Card> listOfCards;

	/**
	 * Set up the initial objects for Round Robin Phase.
	 * 
	 */
    @BeforeEach
    public void initialize() {

        card = new Card();
        listOfCards = new ArrayList<>();
    }

	/**
	* Test to check exchange for different cards for
	* a valid card trade possibility.
	* 
	*/
    @Test
    public void checkExchangeForDiffCards(){
        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.CAVALRY));

        assertEquals(true,card.isExchangePossible(listOfCards));
        }
    
	/**
	* Test to check exchange for same cards for
	* a valid card trade possibility.
	* 
	*/
    @Test
    public void checkExchangeForSameCards(){
        listOfCards.add(new Card(ICardType.CAVALRY));
        listOfCards.add(new Card(ICardType.CAVALRY));
        listOfCards.add(new Card(ICardType.CAVALRY));

        assertEquals(true,card.isExchangePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.INFANTRY));
        listOfCards.add(new Card(ICardType.INFANTRY));
        assertEquals(true,card.isExchangePossible(listOfCards));
        listOfCards.clear();

        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        listOfCards.add(new Card(ICardType.ARTILLERY));
        assertEquals(true,card.isExchangePossible(listOfCards));
        listOfCards.clear();
        }
    }
