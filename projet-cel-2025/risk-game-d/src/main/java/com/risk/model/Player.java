package com.risk.model;

import com.risk.services.MapIO;
import com.risk.strategy.*;
import com.risk.strategy.Random;
import com.risk.services.Util.WindowUtil;
import com.risk.controller.GamePlayController;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.Serializable;
import java.util.*;

/**
 * Class for the player object for the class
 * @author Karandeep Singh
 * @author Palash Jain
 */
public class Player extends Observable implements Observer, Serializable, org.atlanmod.risk.core.Player {

    /**  Player currently playing.*/
    public static Player currentPlayer;

    /** Name of the player*/
    private String name;

    /**  Number of armies*/
    private int armyCount;

    /** Player countries */
    private ArrayList<Country> playerCountries;

    /** Player's cards */
    private ArrayList<Card> cardList;

    /** PlayerType */
    private String playerType;

    /** PlayerBehaviour */
    private PlayerBehaviour playerBehaviour;

    /** Number of countries won by the player */
    private int CountryWon;

    /** Player constructor, initializes initial army count */
    public Player() {
        armyCount = 0;
    }
    /**
     * Constructor for player class
     * @param name player name
     */
    public Player(String name) {
        this.name = name;
        this.cardList = new ArrayList<>();
    }
    /**
     * Player constructor
     * @param name               name
     * @param playerType         player type as string
     * @param gamePlayController GamePlayController object
     */
    public Player(String name, String playerType, GamePlayController gamePlayController) {
        init(name, playerType);
        this.playerBehaviour = PlayerBehaviourFactory.create(playerType, gamePlayController);
        this.addObserver(gamePlayController);
    }

    public Player(String name, String playerType) {
        init(name, playerType);
        this.playerBehaviour = PlayerBehaviourFactory.create(playerType);
    }
    /**
     * Méthode privée pour factoriser l’initialisation commune
     */
    private void init(String name, String playerType) {
        this.name = name;
        this.playerType = playerType;
        this.armyCount = 0;
        this.playerCountries = new ArrayList<>();
        this.cardList = new ArrayList<>();
    }
    /**
     * Method to get name of the player
     * @return player's name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter got player's name
     * @param name of the player
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Getter for player armies
     * @return armyCount of the player
     */
    public int getArmyCount() {
        return armyCount;
    }
    /**
     * Setter for players army count
     * @param armyCount armyCount of the player
     */
    public void setArmyCount(int armyCount) {
        this.armyCount = armyCount;
    }
    /**
     * Getter for list of player's armies.
     * @return List of playerCountries
     */
    public ArrayList<Country> getPlayerCountries() {
        return playerCountries;
    }
    /**
     * Setter for list of player's armies.
     * @param playerCountries set player armies
     */
    public void setMyCountries(ArrayList<Country> playerCountries) {
        this.playerCountries = playerCountries;
    }
    /**
     * Method to add a country to the player's country list
     * @param country country object
     */
    public void addCountry(Country country) {
        this.playerCountries.add(country);
    }
    /**
     * Method for getting the player's list of card
     * @return cardList of player
     */
    public ArrayList<Card> getCardList() {
        return cardList;
    }
    /**
     * Method for returning the player's list of card
     * @param cardList of player
     */
    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }
    /**
     * Method for getting playerType
     * @return playerType
     */
    public String getPlayerType() {
        return playerType;
    }
    /**
     * Method for setting player behaviour
     * @param playerBehaviour Type of player
     */
    public void setPlayerBehaviour(PlayerBehaviour playerBehaviour) {
        this.playerBehaviour = playerBehaviour;
    }
    /**
     * Method to get player behaviour
     * @return playerBehaviour
     */
    public PlayerBehaviour getPlayerBehaviour() {
        return playerBehaviour;
    }
    /**
     * Method for adding armies to a country
     * @param country        Country to which armies are to be assigned
     * @param numberOfArmies number for armies to be assigned
     */
    public void addArmiesToCountry(Country country, int numberOfArmies) {
        if (this.getArmyCount() > 0 && this.getArmyCount() >= numberOfArmies) {
            if (!this.getPlayerCountries().contains(country)) {
                System.out.println("This country is not under your Ownership.");
            } else {
                country.setNoOfArmies(country.getNoOfArmies() + numberOfArmies);
                this.setArmyCount(this.getArmyCount() - numberOfArmies);
            }
        } else {
            System.out.println("Sufficient number of armies not available.");
        }
    }
    /**
     * Getter for current PLayer
     * @return current Player
     */
    public static Player getPlayerPlaying() {
        return Player.currentPlayer;
    }
    /**
     * Method for allocating initial armies to the player,
     * depending upon the total number of players
     * @param players List of all the players
     * @return true, is armies are successfully assigned,; otherwise false
     */
    public boolean assignArmiesToPlayers(List<Player> players) {
        ArmyAllocator.assignInitialArmies(players, this);
        return true;
    }

    /**
     * Method for generating players according to the data entered by the user
     * @param hm                 Map of all the player details
     * @param gamePlayController GamePlayController object as observer
     * @return List of player objects
     */
    public ArrayList<Player> generatePlayer(HashMap<String, String> hm, GamePlayController gamePlayController) {

        ArrayList<Player> listPlayer = new ArrayList<>();
        for (Map.Entry<String, String> playerEntry : hm.entrySet()) {
            listPlayer.add(new Player(playerEntry.getKey().trim(), playerEntry.getValue(), gamePlayController));
            System.out.println("Created player " + playerEntry.getKey().trim() + ".\n");
            setChanged();
            notifyObservers("Created player " + playerEntry.getKey().trim() + ".\n");
        }
        return listPlayer;
    }
    /**
     * Method for calculating number of reinforcement armies to be allocated to the player
     * @param currentPlayer Player to which armies are to be allocated
     * @return Player, object of the current player
     */
    public Player noOfReinforcementArmies(Player currentPlayer) {
        int reinforcements = ArmyAllocator.calculateTurnReinforcements(currentPlayer, this);
        currentPlayer.setArmyCount(currentPlayer.getArmyCount() + reinforcements);
        return currentPlayer;
    }
    /**
     * Method to calculate no of armies
     * @param player Player object
     * @return numberOfArmies
     */
    public int findNoOfArmies(Player player) {
        return ArmyAllocator.calculateTurnReinforcements(player, this);
    }
    /**
     * Method governing the reinforcement phase.
     * @param countries  countries Observable List
     * @param country    country to which reinforcement armies are to be assigned
     * @param playerList list of players
     */
    public void reinforcementPhase(ObservableList<Country> countries, Country country, List<Player> playerList) {
        PlayerPhaseManager.reinforcementPhase(countries, country, playerList, currentPlayer, this);
    }

    /** Method governing the attack phase */
    public void attackPhase(ListView<Country> attackingCountries, ListView<Country> defendingCountries, List<Player> playerList) {
        PlayerPhaseManager.attackPhase(attackingCountries, defendingCountries, playerList, currentPlayer, this);
    }

    /** Method governing the fortification phase */
    public void fortificationPhase(ListView<Country> selectedCountries, ListView<Country> adjCountries, List<Player> playerList) {
        PlayerPhaseManager.fortificationPhase(selectedCountries, adjCountries, playerList, currentPlayer, this);
    }
    /**
     * Method to check if the fortification move taking place in fortification is valid or not
     * @param mapIO         MapIO object
     * @return true if the move is valid; otherwise false
     */
    public boolean isFortificationPhaseValid(MapIO mapIO) {
        return PlayerPhaseManager.isFortificationPhaseValid(mapIO, currentPlayer, this);
    }
    /**
     * Method for placing armies on the countries during the startup phase.
     * @param selectedCountryList List view for the countries of the current player.
     * @param gamePlayerList      List of all the players of playing the game.
     */
    public void placeArmyOnCountry(ListView<Country> selectedCountryList, List<Player> gamePlayerList) {
        PlayerPhaseManager.placeArmyOnCountry(selectedCountryList, gamePlayerList, currentPlayer, this);
    }
    /**
     * Method to check if the player can attack or not.
     */
    public boolean playerCanAttack(ListView<Country> attackingCountries) {
        return PlayerPhaseManager.playerCanAttack(attackingCountries, currentPlayer, this);
    }
    /**
     * Method to check if the player has armies or not.
     * @param allPlayers List of all the players of playing the game.
     * @return true if player has armies left; otherwise false.
     */
    public boolean isPlayerArmyLeft(List<Player> allPlayers) {
        int count = 0;

        for (Player player : allPlayers) {
            if (player.getArmyCount() == 0) {
                count++;
            }
        }
        if (count == allPlayers.size()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method to check if the attack move is valid or not
     */
    public ValidationResult isAttackMoveValid(Country attackingCountry, Country defendingCountry) {
        if (defendingCountry.getPlayer().equals(attackingCountry.getPlayer())) {
            return new ValidationResult(false, "You cannot attack your own country.");
        }
        if (attackingCountry.getNoOfArmies() < 2) {
            return new ValidationResult(false, "You must have at least 2 armies to attack.");
        }
        return new ValidationResult(true, "Valid attack move.");
    }
    /**
     * Methods for exchanging cards of the player for armies
     * @param selectedCards            List of selected cards by the player
     * @param numberOfCardSetExchanged Number of card sets to be exchanged
     * @return Player object exchanging the cards
     */
    public Player exchangeCards(List<Card> selectedCards, int numberOfCardSetExchanged) {
        CardManager.exchangeCards(currentPlayer, selectedCards, numberOfCardSetExchanged, this);
        return currentPlayer;
    }
    /**
     * Setter for setting the current player
     * @param currentPlayer current player
     */
    public static void setPlayerPlaying(Player currentPlayer) {
        Player.currentPlayer = currentPlayer;
    }
    /**
     * Getter for number of countries won by the player
     * @return countryWonCount
     */
    public int getCountryWon() {
        return CountryWon;
    }
    /**
     * Setter for countries won by the player
     * @param CountryWon number of countries won by the player
     */
    public void setCountryWon(int CountryWon) {
        this.CountryWon = CountryWon;
    }
    /**
     * update method for PLayer object
     * @param o   Observable
     * @param arg String which is passed t the player object
     */
    public void update(Observable o, Object arg) {
        String view = (String) arg;
        if (view.equals("rollDiceComplete")) {
            Dice dice = (Dice) o;
            setCountryWon(dice.getCountriesWonCount());
        }
        setChanged();
        notifyObservers(view);
    }
}
