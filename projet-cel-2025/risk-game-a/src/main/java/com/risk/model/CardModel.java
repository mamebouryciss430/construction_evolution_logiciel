package com.risk.model;

import org.atlanmod.risk.core.Card;

/**
 *
 * @author Namita Faujdar
 */
public class CardModel implements Card {

    /** The card id. */
    private int cardId;

    /** The card value. */
    private int cardValue;

    /**
     * Constructor of CardModel.
     *
     * @param cardId    the card id
     * @param cardValue the card value
     */
    public CardModel(int cardId, int cardValue) {
        this.cardId = cardId;
        this.cardValue = cardValue;
    }

    /**
     * Instantiates a new card model.
     */
    public CardModel() {
    }

    /**
     * Returns the CardId.
     *
     * @return the CardId.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Sets the CardId.
     *
     * @param cardId the new card id
     */
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    /**
     * Returns the cardValue.
     *
     * @return the cardValue.
     */
    public int getCardValue() {
        return cardValue;
    }

    /**
     * Sets the cardValue.
     *
     * @param cardValue the new card value
     */
    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }
}
