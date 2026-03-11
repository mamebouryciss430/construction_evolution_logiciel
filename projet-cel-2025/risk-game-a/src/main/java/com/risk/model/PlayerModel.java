package com.risk.model;

import com.risk.model.strategy.Strategy;
import org.atlanmod.risk.core.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;



/**
 * "PlayerModel" class represents an object of a player. The properties are:
 * Player name, player's troop number, the color in view screen (displaying)
 * representing the player,
 *
 * @author KaranPannu
 *
 */
public class PlayerModel extends Observable implements Player {

    /** The name player. */
    private String namePlayer;

    /** The type player. */
    private String typePlayer;

    /** The my troop. */
    private int myTroop;

    /** The color. */
    private Color color;

    /** The remain troop. */
    private int remainTroop;

    /** The owned countries. */
    private List<CountryModel> ownedCountries  = new ArrayList<CountryModel>();

    /** The owned cards. */
    private List<CardModel> ownedCards  = new ArrayList<CardModel>();

    /** The show reinforcement card. */
    private boolean showReinforcementCard;

    private Strategy strategy;

    /**
     * Constructor of PlayerModel.
     *
     * @param namePlayer the name player
     * @param myTroop the my troop
     * @param color the color
     * @param remainTroop the remain troop
     * @param ownedCountries the owned countries
     * @param ownedCards the owned cards
     */
    public PlayerModel(String namePlayer, String typePlayer, int myTroop, Color color, int remainTroop, List ownedCountries, List ownedCards) {
        this.namePlayer = namePlayer;
        this.typePlayer = typePlayer;
        this.myTroop = myTroop;
        this.color = color;
        this.remainTroop = remainTroop;
        this.ownedCountries = ownedCountries;
        this.ownedCards = ownedCards;
    }

    /**
     * Default constructor.
     */
    public PlayerModel() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the my troop.
     *
     * @return the value Control.
     */
    public int getmyTroop() {
        return myTroop;
    }

    /**
     * Sets the value Control.
     *
     * @param myTroop the new my troop
     */
    public void setmyTroop(int myTroop) {
        this.myTroop = myTroop;
    }

    /**
     * Gets the name player.
     *
     * @return the player name.
     */
    public String getName() {
        return namePlayer;
    }

    /**
     * Sets the continent name.
     *
     * @param namePlayer the new name player
     */
    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    /**
     * Gets the type player.
     *
     * @return the player type.
     */
    public String getTypePlayer() {
        return typePlayer;
    }

    /**
     * Sets the Player type.
     *
     * @param typePlayer the new type player
     */
    public void setTypePlayer(String typePlayer) {
        this.typePlayer = typePlayer;
    }

    /**
     * Gets the color.
     *
     * @return the continent name.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the continent name.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the remain troop.
     *
     * @return the .
     */
    public int getremainTroop() {
        return remainTroop;
    }

    /**
     * Sets the value Control.
     *
     * @param remainTroop the new remain troop
     */
    public void setremainTroop(int remainTroop) {
        this.remainTroop = remainTroop;
    }

    /**
     * Calling Observer.
     */
    public void callObservers() {
        setChanged();
        notifyObservers(this);

    }

    /**
     * Gets the owned countries.
     *
     * @return the list of array.
     */
    public List<CountryModel> getOwnedCountries() {
        return ownedCountries;
    }

    /**
     * Sets the list of array.
     *
     * @param ownedCountries the new owned countries
     */
    public void setOwnedCountries(List<CountryModel> ownedCountries) {
        this.ownedCountries = ownedCountries;
    }

    /**
     * Gets the owned cards.
     *
     * @return the list of array.
     */
    public List<CardModel> getOwnedCards() {
        return ownedCards;
    }

    /**
     * Sets the list of array.
     *
     * @param ownedCards the new owned cards
     */
    public void setOwnedCards(List<CardModel> ownedCards) {
        this.ownedCards = ownedCards;
    }

    /**
     * Attacked.
     *
     * @param countryForAddition the country for addition
     * @return true, if successful
     */
    public boolean attacked(CountryModel countryForAddition) {
        this.ownedCountries.add(countryForAddition);
        return true;
    }

    /**
     * Defend.
     *
     * @param countryForDeduction the country for deduction
     * @return true, if successful
     */
    public boolean defend(CountryModel countryForDeduction) {
        for(int i=0; i< this.ownedCountries.size(); i++) {
            if (this.ownedCountries.get(i).getCountryName().equals(countryForDeduction.getCountryName())) {
                this.ownedCountries.remove(i);
            }
        }
        return true;
    }

    /**
     * Adds the card.
     *
     * @param toAddCard the to add card
     * @return true, if successful
     */
    public boolean addCard(CardModel toAddCard) {
        this.ownedCards.add(toAddCard);
        return true;
    }

    /**
     * Removes the card.
     *
     * @param toRemoveCard the to remove card
     * @return true, if successful
     */
    public boolean removeCard(CardModel toRemoveCard) {
        boolean returnvalue = false;
        for(int i=0; i< this.ownedCards.size(); i++) {
            if (this.ownedCards.get(i).getCardId() == toRemoveCard.getCardId()) {
                this.ownedCards.remove(i);
                returnvalue = true;
            }
        }
        return returnvalue;
    }

    /**
     * Sets the show reinforcement card.
     *
     * @param showReinforcementCard the new show reinforcement card
     */
    public void setShowReinforcementCard(boolean showReinforcementCard) {
        this.showReinforcementCard = showReinforcementCard;
    }

    /**
     * Gets the show reinforcement card.
     *
     * @return the show reinforcement card
     */
    public boolean getShowReinforcementCard() {
        return this.showReinforcementCard;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    public void executeReinforcement() {
        this.strategy.reinforcement();
    }
    public void executeAttack() {
        this.strategy.attack();
    }
    public void executeFortification() {
        this.strategy.fortification();
    }
}
