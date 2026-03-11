package com.risk.model;


import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.risk.view.*;

import java.io.*;
import java.util.*;


/**
 * "GamePlayerModel" class represents an object containing current map and
 * players.
 *
 * @author Gursimran Singh
 */
public class GamePlayModel extends Observable {

    /**
     * The game map model.
     */
    private GameMapModel gameMapModel = new GameMapModel();

    /**
     * The players.
     */
    private ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();

    /**
     * The deck.
     */
    private ArrayList<CardModel> deck = new ArrayList<CardModel>();

    /**
     * to save Selected ComboBox index.
     */
    private int selectedComboBoxIndex;

    /**
     * to save Selected ComboBox index.
     */
    private int selectedAttackComboBoxIndex;

    /**
     * to save Selected ComboBox index.
     */
    private int selectedDefendComboBoxIndex;

    /**
     * The country owned.
     */
    private boolean countryOwned = false;

    /**
     * The army to move flag.
     */
    private boolean armyToMoveFlag;

    /**
     * The card to be assigned.
     */
    private boolean cardToBeAssigned;

    /**
     * The console text.
     */
    private StringBuilder consoleText;

    /**
     * The defeated country.
     */
    private CountryModel defeatedCountry = new CountryModel();

    private PlayConsoleView console;

    private String gamePhase = null;


    /**
     * Constructor.
     *
     * @param gameMap the game map
     * @param players the players
     * @param deck    the deck
     */
    public GamePlayModel(GameMapModel gameMap, ArrayList<PlayerModel> players, ArrayList<CardModel> deck) {
        this.gameMapModel = gameMap;
        this.players = players;
        this.deck = deck;
        this.consoleText = new StringBuilder("Hello to the Risk Game ! ");
        this.console = new PlayConsoleView();
        this.console.setVisible(true);
    }

    /**
     * Default Constructor.
     */
    public GamePlayModel() {
        this.gameMapModel = gameMapModel;
        this.consoleText = new StringBuilder("Hello to the Risk Game ! ");
        this.console = new PlayConsoleView();
        this.console.setVisible(true);
        this.console.append("I am alive");
    }

    /**
     * Gets the gamePhase.
     *
     * @return the gamePhase.
     */
    public String getGamePhase() {
        return gamePhase;
    }

    /**
     * Sets the gamePhase.
     *
     * @param gamePhase
     */
    public void setGamePhase(String gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Gets the game map.
     *
     * @return the gameMap.
     */
    public GameMapModel getGameMap() {
        return gameMapModel;
    }

    public PlayConsoleView getConsole() {
        return this.console;
    }

    /**
     * Sets the gameMap Model.
     *
     * @param gameMap the new game map
     */
    public void setGameMap(GameMapModel gameMap) {
        this.gameMapModel = gameMap;
    }

    /**
     * Gets the players.
     *
     * @return the list of players.
     */
    public ArrayList<PlayerModel> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players.
     *
     * @param players the new players
     */
    public void setPlayers(ArrayList<PlayerModel> players) {
        this.players = players;
    }

    /**
     * Sets the defeated country.
     *
     * @param defeatedCountry the new defeated country
     */
    public void setDefeatedCountry(CountryModel defeatedCountry) {
        this.defeatedCountry = defeatedCountry;
    }

    /**
     * Sets the countryOwned.
     *
     * @param countryOwned
     * @return
     */
    public void setCountryOwned(Boolean countryOwned) {
        this.countryOwned = countryOwned;
    }

    /**
     * Gets the countryOwned.
     *
     * @return the countryOwned
     */
    public Boolean getCountryOwned() {
        return this.countryOwned;
    }

    /**
     * Gets the armyToMoveFlag.
     *
     * @return the defeated country
     */
    public Boolean getArmyToMoveFlag() {
        return this.armyToMoveFlag;
    }

    /**
     * Gets the defeated country.
     *
     * @return the defeated country
     */
    public CountryModel getDefeatedCountry() {
        return this.defeatedCountry;
    }

    /**
     * Gets the card from JSON.
     *
     * @return the list of card.
     */
    public ArrayList<CardModel> getCardFromJSON() throws org.json.simple.parser.ParseException {
        try {
            JSONParser parser = new JSONParser();
            Object cards = parser.parse(new FileReader(
                    System.getProperty("user.dir") + "/ConstantCard.json"));
            JSONObject jsonObject = (JSONObject) cards;
            System.out.println("jsonObject " + jsonObject.get("cards"));
            JSONArray cardsJSON = (JSONArray) jsonObject.get("cards");
            Long lvalue;
            int value;

            for (Object o : cardsJSON) {
                CardModel cardModel = new CardModel();
                JSONObject card = (JSONObject) o;
                lvalue = Long.parseLong((String) card.get("cardId"));
                value = lvalue.intValue();
                cardModel.setCardId(value);

                lvalue = Long.parseLong((String) card.get("cardValue"));
                value = lvalue.intValue();
                cardModel.setCardValue(value);
                this.deck.add(cardModel);
            }
            //Collections.shuffle(this.deck);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.deck;
    }

    /**
     * Sets the list of card.
     *
     * @return the cards
     */
    public ArrayList<CardModel> getCards() {
        return this.deck;
    }

    /**
     * Sets the list of card.
     *
     * @return the card
     */
    public CardModel getCard() {
        CardModel temp = this.deck.get(this.deck.size() - 1);
        this.deck.remove(this.deck.size() - 1);
        return temp;
    }

    /**
     * Sets the cards.
     *
     * @param deck the new cards
     */
    public void setCards(ArrayList<CardModel> deck) {
        this.deck = deck;
    }

    /**
     * Gets the console text.
     *
     * @return the console text
     */
    public StringBuilder getConsoleText() {
        return consoleText;
    }

    /**
     * Sets the console text.
     *
     * @param consoleText the new console text
     */
    public void setConsoleText(StringBuilder consoleText) {
        this.consoleText = consoleText;
    }

    /**
     * Gets the player.
     *
     * @param parmCountry the parm country
     * @return the player
     */
    public PlayerModel getPlayer(CountryModel parmCountry) {
        int i, j;
        for (i = 0; i < players.size(); i++) {
            for (j = 0; j < players.get(i).getOwnedCountries().size(); j++) {
                if (parmCountry.getCountryName().equals(players.get(i).getOwnedCountries().get(j).getCountryName())) {
                    return players.get(i);
                } else {
                    continue;
                }
            }
        }
        return null;

    }

    /**
     * Sets the selected armies to countries.
     *
     * @param selectedArmies the selected armies
     * @param countryName    the country name
     */
    public void setSelectedArmiesToCountries(int selectedArmies, CountryModel countryName) {
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (this.gameMapModel.getCountries().get(i).equals(countryName)) {
                this.gameMapModel.getCountries().get(i)
                        .setArmies(this.gameMapModel.getCountries().get(i).getArmies() + selectedArmies);
                for (int j = 0; j < this.getPlayers().size(); j++) {
                    if (this.getPlayers().get(j).getName()
                            .equals(this.gameMapModel.getCountries().get(i).getRulerName())) {
                        this.getPlayers().get(j)
                                .setremainTroop(this.getPlayers().get(j).getremainTroop() - selectedArmies);
                        this.consoleText.append("\n" + this.getPlayers().get(j).getName() + " added "
                                + selectedArmies + " armies to " + countryName.getCountryName() + "\n");
                    }
                }
            }
        }

        callObservers();
    }

    /**
     * Number of countries.
     *
     * @return the int
     */
    public int numberOfCountries() {
        int numberOfCountries = 0;

        for (int i = 0; i < this.getPlayers().size(); i++) {

            if (this.getPlayers().get(i).getName().equals(this.getGameMap().getPlayerTurn().getName())) {
                numberOfCountries = this.getPlayers().get(i).getOwnedCountries().size();
            }
        }
        return reinforcementArmies(numberOfCountries);
    }

    /**
     * Reinforcement armies.
     *
     * @param numberOfCountries the number of countries
     * @return the int
     */
    public int reinforcementArmies(int numberOfCountries) {
        int reinforceArmies = 0;
        if (numberOfCountries > 3) {
            reinforceArmies = 3 + Math.round(numberOfCountries / 3);
        } else {
            reinforceArmies = 3;
        }
        if (reinforceArmies > 12) {
            reinforceArmies = 12;
        }
        return reinforceArmies;
    }

    /**
     * Gets the defend country list.
     *
     * @param attackCountryName the attack country name
     * @return the defend country list
     */
    public ArrayList<CountryModel> getDefendCountryList(CountryModel attackCountryName) {
        ArrayList<CountryModel> linkedCountry;
        ArrayList<CountryModel> defenderCountryList = new ArrayList<CountryModel>();
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (this.gameMapModel.getCountries().get(i).equals(attackCountryName)) {
                linkedCountry = (ArrayList<CountryModel>) this.gameMapModel.getCountries().get(i).getLinkedCountries();
                for (int j = 0; j < linkedCountry.size(); j++) {
                    if (!attackCountryName.getRulerName().equals(linkedCountry.get(j).getRulerName())) {
                        defenderCountryList.add(linkedCountry.get(j));
                    }
                }
            }
        }
        return defenderCountryList;
    }

    /**
     * Single strike.
     *
     * @param attackDice    the attack dice
     * @param attackCountry the attack country
     * @param defendDice    the defend dice
     * @param defendCountry the defend country
     */
    public boolean singleStrike(int attackDice, CountryModel attackCountry,
                                int defendDice, CountryModel defendCountry) {

        Integer[] attackRolls = rollDice(attackDice);
        Integer[] defendRolls = rollDice(defendDice);

        logDice(attackRolls, defendRolls);

        boolean result = resolveDiceResults(attackRolls, defendRolls, attackCountry, defendCountry);

        if (countryOwned) {
            handleCountryConquest(attackCountry, defendCountry);
        }

        callObservers();
        return result;
    }


    private Integer[] rollDice(int count) {
        Integer[] rolls = new Integer[count];
        for (int i = 0; i < count; i++) {
            rolls[i] = getRandomBetweenRange(1, 6);
        }
        Arrays.sort(rolls, Collections.reverseOrder());
        return rolls;
    }


    private void logDice(Integer[] attackRolls, Integer[] defendRolls) {
        String attackMsg = "\n Attack country dice " + Arrays.toString(attackRolls) + " \n";
        String defendMsg = "Defender country dice " + Arrays.toString(defendRolls) + " \n";

        System.out.println(Arrays.toString(attackRolls));
        consoleText.append(attackMsg);
        consoleText.append(defendMsg);
        console.append(attackMsg);
        console.append(defendMsg);
    }


    private boolean resolveDiceResults(Integer[] attackRolls, Integer[] defendRolls,
                                       CountryModel attackCountry, CountryModel defendCountry) {

        boolean result = false;
        int rounds = Math.min(attackRolls.length, defendRolls.length);

        for (int i = 0; i < rounds; i++) {
            if (attackRolls[i] > defendRolls[i]) {
                armiesDeduction(defendCountry, 1);
            } else {
                armiesDeduction(attackCountry, 1);
            }
            result = true;
        }

        return result;
    }


    private void handleCountryConquest(CountryModel attackCountry, CountryModel defendCountry) {

        String msg = "Attacker " + attackCountry.getRulerName()
                + " defeated Country " + defendCountry.getCountryName() + " \n";

        consoleText.append(msg);
        console.append(msg);

        updatePlayersAfterConquest(attackCountry, defendCountry);
        updateMapOwnership(attackCountry, defendCountry);

        setArmyToMoveText(true);
        setCardToBeAssigned(true);
    }


    private void updatePlayersAfterConquest(CountryModel attackCountry, CountryModel defendCountry) {
        for (var player : getPlayers()) {
            if (player.getName().equals(defendCountry.getRulerName())) {
                player.defend(defendCountry);
            }
            if (player.getName().equals(attackCountry.getRulerName())) {
                player.attacked(defendCountry);
            }
        }
    }


    private void updateMapOwnership(CountryModel attackCountry, CountryModel defendCountry) {
        for (CountryModel c : gameMapModel.getCountries()) {
            if (c.getCountryName().equals(defendCountry.getCountryName())) {
                c.setRulerName(attackCountry.getRulerName());
            }
        }
    }

    /**
     * Armies deduction.
     *
     * @param countryForDeduction the country for deduction
     * @param armiesToDeduct      the armies to deduct
     * @return the country model
     */
    public CountryModel armiesDeduction(CountryModel countryForDeduction, int armiesToDeduct) {

        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (this.gameMapModel.getCountries().get(i).getCountryName().equals(countryForDeduction.getCountryName())) {
                if (this.gameMapModel.getCountries().get(i).getArmies() == 1) {
                    countryOwned = true;
                    this.gameMapModel.getCountries().get(i)
                            .setArmies(this.gameMapModel.getCountries().get(i).getArmies() - armiesToDeduct);
                } else {
                    this.gameMapModel.getCountries().get(i)
                            .setArmies(this.gameMapModel.getCountries().get(i).getArmies() - armiesToDeduct);
                }
            }
        }
        for (int i = 0; i < this.getPlayers().size(); i++) {
            if (this.getPlayers().get(i).getName().equals(countryForDeduction.getRulerName())) {
                this.getPlayers().get(i).setmyTroop(this.getPlayers().get(i).getmyTroop() - armiesToDeduct);
            }
        }
        return countryForDeduction;
    }

    /**
     * Allout strike.
     *
     * @param attackCountry the attack country
     * @param defendCountry the defend country
     */
    public boolean alloutStrike(CountryModel attackCountry, CountryModel defendCountry) {
        int attackTotal = attackCountry.getArmies() - 1;
        int defendTotal = defendCountry.getArmies();
        boolean result = false;

        console.append("Attack's total army: " + attackCountry.getArmies() + " \n");
        console.append("Defender's total army: " + defendCountry.getArmies() + " \n");

        while (attackTotal > 0 && defendTotal > 0) {

            int attackDice = Math.min(attackTotal, 3);
            int defendDice = Math.min(defendTotal, 2);

            Integer[] attackRolls = rollDiceA(attackDice);
            Integer[] defendRolls = rollDiceA(defendDice);

            logDiceA(attackRolls, defendRolls);

            int rounds = Math.min(attackDice, defendDice);

            for (int i = 0; i < rounds; i++) {
                if (attackRolls[i] > defendRolls[i]) {
                    armiesDeduction(defendCountry, 1);
                    defendTotal--;
                } else {
                    armiesDeduction(attackCountry, 1);
                    attackTotal--;
                }
                result = true;
            }
        }

        if (countryOwned) {
            handleCountryConquestA(attackCountry, defendCountry);
        }

        callObservers();
        return result;
    }



    private Integer [] rollDiceA(int count) {
        Integer[] rolls = new Integer[count];
        for (int i = 0; i < count; i++) {
            rolls[i] = getRandomBetweenRange(1, 6);
        }
        Arrays.sort(rolls, Collections.reverseOrder());
        return rolls;
    }

    private void logDiceA(Integer[] attackRolls, Integer[] defendRolls) {
        String attackMsg = "Attack country dice " + Arrays.toString(attackRolls) + " \n";
        String defendMsg = "Defender country dice " + Arrays.toString(defendRolls) + " \n";

        System.out.println(Arrays.toString(attackRolls));
        System.out.println(Arrays.toString(defendRolls));

        console.append(attackMsg);
        console.append(defendMsg);
    }

    private void handleCountryConquestA(CountryModel attackCountry, CountryModel defendCountry) {

        String msg = "Attacker " + attackCountry.getRulerName()
                + " defeated Country " + defendCountry.getCountryName() + " \n";

        consoleText.append(msg);
        console.append(msg);

        updatePlayersAfterConquestA(attackCountry, defendCountry);
        updateMapOwnershipA(attackCountry, defendCountry);

        setArmyToMoveText(true);
        setCardToBeAssigned(true);
    }

    private void updatePlayersAfterConquestA(CountryModel attackCountry, CountryModel defendCountry) {
        for (var player : getPlayers()) {
            if (player.getName().equals(defendCountry.getRulerName())) {
                player.defend(defendCountry);
            }
            if (player.getName().equals(attackCountry.getRulerName())) {
                player.attacked(defendCountry);
            }
        }
    }

    private void updateMapOwnershipA(CountryModel attackCountry, CountryModel defendCountry) {
        for (CountryModel c : gameMapModel.getCountries()) {
            if (c.getCountryName().equals(defendCountry.getCountryName())) {
                c.setRulerName(attackCountry.getRulerName());
            }
        }
    }


    /**
     * Move armies.
     *
     * @param attackCountry the attack country
     * @param defendCountry the defend country
     * @param noOfArmiesToBeMoved the no of armies to be moved
     */
    public boolean moveArmies(CountryModel attackCountry, CountryModel defendCountry, int noOfArmiesToBeMoved) {
        boolean returnvalue = false;
        this.consoleText.append(attackCountry.getRulerName() + " Armies " + noOfArmiesToBeMoved + " moved from"
                + attackCountry.getCountryName() + " to " + defendCountry.getCountryName() + " \n");
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (this.gameMapModel.getCountries().get(i).getCountryName().equals(attackCountry.getCountryName())) {
                this.gameMapModel.getCountries().get(i)
                        .setArmies(this.gameMapModel.getCountries().get(i).getArmies() - noOfArmiesToBeMoved);
                returnvalue = true;
            }
            if (this.gameMapModel.getCountries().get(i).getCountryName().equals(defendCountry.getCountryName())) {
                this.gameMapModel.getCountries().get(i)
                        .setArmies(this.gameMapModel.getCountries().get(i).getArmies() + noOfArmiesToBeMoved);
                returnvalue = true;
            }
        }
        this.setArmyToMoveText(false);
        callObservers();
        return returnvalue;
    }

    /**
     * Sets the selected combo box index.
     *
     * @param selectedComboBoxIndex the new selected combo box index
     */
    public void setSelectedComboBoxIndex(int selectedComboBoxIndex) {
        this.selectedComboBoxIndex = selectedComboBoxIndex;
        callObservers();

    }

    /**
     * Sets the selected combo box index.
     *
     * @param selectedComboBoxIndex the new selected combo box index
     */
    public void setSelectedComboBoxIndexRead(int selectedComboBoxIndex) {
        this.selectedComboBoxIndex = selectedComboBoxIndex;

    }

    /**
     * Gets the selected combo box index.
     *
     * @return the selected combo box index
     */
    public int getSelectedComboBoxIndex() {
        return this.selectedComboBoxIndex;
    }

    /**
     * Sets the selected attack combo box index.
     *
     * @param selectedAttackComboBoxIndex the new selected attack combo box index
     */
    public void setSelectedAttackComboBoxIndex(int selectedAttackComboBoxIndex) {
        this.selectedAttackComboBoxIndex = selectedAttackComboBoxIndex;
        callObservers();

    }

    /**
     * Sets the selected attack combo box index.
     *
     * @param selectedAttackComboBoxIndex the new selected attack combo box index
     */
    public void setSelectedAttackComboBoxIndexRead(int selectedAttackComboBoxIndex) {
        this.selectedAttackComboBoxIndex = selectedAttackComboBoxIndex;

    }

    /**
     * Gets the selected attack combo box index.
     *
     * @return the selected attack combo box index
     */
    public int getSelectedAttackComboBoxIndex() {
        return this.selectedAttackComboBoxIndex;
    }

    /**
     * Sets the selected defend combo box index.
     *
     * @param selectedDefendComboBoxIndex the new selected defend combo box index
     */
    public void setSelectedDefendComboBoxIndex(int selectedDefendComboBoxIndex) {
        this.selectedDefendComboBoxIndex = selectedDefendComboBoxIndex;
        callObservers();

    }

    /**
     * Sets the selected defend combo box index.
     *
     * @param selectedDefendComboBoxIndex the new selected defend combo box index
     */
    public void setSelectedDefendComboBoxIndexRead(int selectedDefendComboBoxIndex) {
        this.selectedDefendComboBoxIndex = selectedDefendComboBoxIndex;

    }

    /**
     * Gets the selected defend combo box index.
     *
     * @return the selected defend combo box index
     */
    public int getSelectedDefendComboBoxIndex() {
        return this.selectedDefendComboBoxIndex;
    }

    /**
     * Sets the army to move text.
     *
     * @param armyToMoveFlag the new army to move text
     */
    public void setArmyToMoveText(boolean armyToMoveFlag) {
        this.armyToMoveFlag = armyToMoveFlag;
        if (armyToMoveFlag == true) {
            callObservers();
        }
    }

    /**
     * Sets the army to move text.
     *
     * @param armyToMoveFlag the new army to move text
     */
    public void setArmyToMoveTextRead(boolean armyToMoveFlag) {
        this.armyToMoveFlag = armyToMoveFlag;
    }

    /**
     * Gets the army to move text.
     *
     * @return the army to move text
     */
    public boolean getArmyToMoveText() {
        return this.armyToMoveFlag;
    }

    /**
     * Sets the card to be assigned.
     *
     * @param cardToBeAssigned the new card to be assigned
     */
    public void setCardToBeAssigned(boolean cardToBeAssigned) {
        this.cardToBeAssigned = cardToBeAssigned;
    }

    /**
     * Gets the card to be assigned.
     *
     * @return the card to be assigned
     */
    public boolean getCardToBeAssigned() {
        return this.cardToBeAssigned;
    }

    /**
     * This method gives the Random generation of numbers within two values.
     *
     * @param min the min
     * @param max the max
     * @return the random between range
     */
    public int getRandomBetweenRange(double min, double max) {
        int x = (int) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    };

    /**
     * Method used to notify state change whenever any change is reflected by
     * CreateContinentController via SwingCreateContinentView.
     */
    public void callObservers() {
        for (int i = 0; i < this.getPlayers().size(); i++) {
            this.worldCoverage(this.getPlayers().get(i));
            this.consoleText.append("						" + this.getPlayers().get(i).getName() + " has "
                    + this.getPlayers().get(i).getmyTroop() + " troops \n");
        }
        this.getConsoleText().append("\n");
        for (int i = 0; i < this.gameMapModel.getContinents().size(); i++) {
            continentCoverage(this.gameMapModel.getContinents().get(i));
        }

        setChanged();
        notifyObservers(this);
    }

    /**
     * World coverage.
     *
     * @param parmPlayer the parm player
     */
    public boolean worldCoverage(PlayerModel parmPlayer) {
        boolean returnvalue = true;
        double percentage = (parmPlayer.getOwnedCountries().size() * 100) / this.gameMapModel.getCountries().size();
        this.getConsoleText().append("						" + " Map coverage for " + parmPlayer.getName()
                + " is " + percentage + "% " + "\n");
        return returnvalue;
    }

    /**
     * Continent coverage.
     *
     * @param parmContinent the parm continent
     */
    public boolean continentCoverage(ContinentsModel parmContinent) {
        boolean returnvalue = false;
        this.getConsoleText().append("						" + "Continent " + parmContinent.getContinentName()
                + "'s coverage distribution: \n");
        int countryCount = 0;
        double percentage;

        for (int j = 0; j < this.players.size(); j++) {
            for (int k = 0; k < this.players.get(j).getOwnedCountries().size(); k++) {
                if (this.players.get(j).getOwnedCountries().get(k).getcontinentName()
                        .equals(parmContinent.getContinentName())) {
                    countryCount = countryCount + 1;
                }

            }
            returnvalue = true;
            percentage = (countryCount * 100) / parmContinent.getCoveredCountries().size();
            this.getConsoleText().append("						" + this.players.get(j).getName()
                    + " has covered " + percentage + "% of the continent " + parmContinent.getContinentName() + "\n");
            countryCount = 0;
        }
        return returnvalue;
    }

    /**
     * Continent covered.
     *
     * @param player the player
     * @return the int
     */
    public int continentCovered(PlayerModel player) {
        int countryCount = 0;
        double percentage = 0.0;
        int controlValue = 0;
        for (int j = 0; j < this.players.size(); j++) {
            if (this.players.get(j).getName().equals(player.getName())) {
                for (int i = 0; i < this.getGameMap().getContinents().size(); i++) {
                    for (int k = 0; k < this.players.get(j).getOwnedCountries().size(); k++) {
                        if (this.players.get(j).getOwnedCountries().get(k).getcontinentName()
                                .equals(this.getGameMap().getContinents().get(i).getContinentName())) {
                            countryCount = countryCount + 1;
                        }
                    }
                    percentage = (countryCount * 100)
                            / this.getGameMap().getContinents().get(i).getCoveredCountries().size();
                    if (percentage == 100.0) {
                        controlValue = controlValue + this.getGameMap().getContinents().get(i).getValueControl();
                    }
                    percentage = 0;
                    countryCount = 0;
                }
            }
        }
        return controlValue;
    }

    /**
     * Move deck.
     */
    public boolean moveDeck() {

        boolean returnValue = false;
        if (this.getCardToBeAssigned() == true) {
            CardModel card;

            for (int i = 0; i < this.getPlayers().size(); i++) {
                if (this.getPlayers().get(i).getName()
                        .equals(this.getGameMap().getPlayerTurn().getName())) {
                    card = this.getCard();
                    ArrayList<CardModel> tempList = (ArrayList<CardModel>) this.getPlayers().get(i).getOwnedCards();
                    tempList.add(card);
                    this.getPlayers().get(i).setOwnedCards(tempList);
                    returnValue = true;
                }
            }
            this.setCardToBeAssigned(false);
        }
        return returnValue;
    }

    public ArrayList<CountryModel> sortCountry(ArrayList<CountryModel> ownedCountries) {
        Collections.sort(ownedCountries, new Comparator<CountryModel>(){
            public int compare(CountryModel s1, CountryModel s2) {
                return Integer.valueOf(s1.getArmies()).compareTo(s2.getArmies());
            }
        });
        return ownedCountries;
    }

    public ArrayList<CountryModel> descCountry(ArrayList<CountryModel> ownedCountries) {
        Collections.sort(ownedCountries, new Comparator<CountryModel>(){
            public int compare(CountryModel s1, CountryModel s2) {
                return Integer.valueOf(s2.getArmies()).compareTo(s1.getArmies());
            }
        });
        return ownedCountries;
    }

    public ArrayList<CountryModel> selectWeakestCountry(ArrayList<CountryModel> ownedCountries) {
        Collections.sort(ownedCountries, new Comparator<CountryModel>(){
            public int compare(CountryModel s1, CountryModel s2) {
                return Integer.valueOf(s1.getArmies()).compareTo(s2.getArmies());
            }
        });
        ArrayList<CountryModel> weakestCounty = new ArrayList<CountryModel>();
        for (int i =0; i< ownedCountries.size(); i++) {
            if(ownedCountries.get(0).getArmies() == ownedCountries.get(i).getArmies()) {
                weakestCounty.add(ownedCountries.get(i));
            }
        }
        return weakestCounty;
    }
}