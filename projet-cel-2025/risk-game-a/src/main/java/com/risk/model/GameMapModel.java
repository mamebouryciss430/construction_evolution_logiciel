package com.risk.model;

import com.risk.utilities.ReadFile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Model of whole map file that gets generated in the end
 *
 * @author KaranPannu
 *
 */

public class GameMapModel extends Observable {

    /**
     * List of Continents
     */
    private List<ContinentsModel> continentList;

    /**
     * List of Countries
     */
    private List<CountryModel> countryList;

    /**
     * Read file object for creating a file in parameterized constructor
     */
    private ReadFile readfile;

    /**
     * to save player's turn
     */
    private PlayerModel playerTurn;

    /**
     * to save player's index
     */
    private int playerIndex;
    /**
     * to save list of player Model
     */
    private ArrayList<PlayerModel> listOfPlayers;

    /**
     * Index to fetch the left model in list view
     */
    private int leftModelIndex = 0;

    /**
     * Index to fetch the right model in list view
     */
    private int rightModelIndex = 0;

    /**
     * @return the leftModelIndex to get
     */
    public int getLeftModelIndex() {
        return leftModelIndex;
    }

    /**
     * @param leftModelIndex to set
     */
    public void setLeftModelIndex(int leftModelIndex) {
        this.leftModelIndex = leftModelIndex;
    }

    /**
     * @return the rightModelIndex to get
     */
    public int getRightModelIndex() {
        return rightModelIndex;
    }

    /**
     * @param rightModelIndex to set
     */
    public void setRightModelIndex(int rightModelIndex) {
        this.rightModelIndex = rightModelIndex;
    }

    /**
     * Parameterized constructor that helps edit the map
     *
     * @param file
     */
    public GameMapModel(File file) {
        readfile = new ReadFile();
        try {
            readfile.setFile(file);
            this.continentList = readfile.getMapContinentDetails();
            this.countryList = readfile.getMapCountryDetails();
            this.countriesInContinent();
        } catch (Exception e) {
            // handle properly
            e.printStackTrace();
        }
    }

    /**
     * Default constructor to make new map
     */
    public GameMapModel() {
        this.continentList = new ArrayList<ContinentsModel>();
        this.countryList = new ArrayList<CountryModel>();

    }

    /**
     * @return the list of Continents.
     */
    public List<ContinentsModel> getContinents() {
        return this.continentList;
    }

    /**
     * Sets the value Control.
     *
     * @param Continents
     */
    public void setContinents(List<ContinentsModel> Continents) {
        this.continentList = Continents;
        callObservers();
    }

    /**
     * @return the list of Countries.
     */
    public List<CountryModel> getCountries() {
        return countryList;
    }

    /**
     * Sets the list of Countries.
     *
     * @param Countries
     */
    public void setCountries(List<CountryModel> Countries) {
        this.countryList = Countries;
        callObservers();
    }

    /**
     * Countries in continent.
     */
    public void countriesInContinent() {
        boolean firsttime = true;
        for (int i = 0; i < this.continentList.size(); i++) {
            for (int j = 0; j < this.countryList.size(); j++) {
                try {

                    if (this.countryList.get(j).getcontinentName()
                            .equals(this.continentList.get(i).getContinentName())) {
                        this.continentList.get(i).setCoveredCountries(this.countryList.get(j));
                    }
                } catch (Exception e) {
                    if (firsttime) {
                        JOptionPane.showOptionDialog(null, "There are some possible errors in the map", "Invalid",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {},
                                null);
                        firsttime = false;
                    }

                    // System.exit(0);
                }

            }
        }
    }

    /**
     * Method used to notify state change whenever any change is reflected by
     * CreateContinentController via SwingCreateContinentView
     */
    public void callObservers() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Remove continent with the continent model name
     *
     * @param newContinentModel
     */
    public void removeContinent(ContinentsModel newContinentModel) {
        this.continentList.remove(newContinentModel);
        callObservers();
    }

    /**
     * Update countries when added
     *
     * @param mapModel
     * @return
     */
    public GameMapModel updateCountries(GameMapModel mapModel) {
        List<CountryModel> newCountryList = new ArrayList<CountryModel>();
        for (int i = 0; i < mapModel.continentList.size(); i++) {
            for (int j = 0; j < mapModel.countryList.size(); j++) {
                if (mapModel.countryList.get(j).getcontinentName()
                        .equals(mapModel.continentList.get(i).getContinentName())) {
                    newCountryList.add(mapModel.countryList.get(j));
                }
            }
        }
        mapModel.countryList = null;
        mapModel.countryList = newCountryList;
        return mapModel;
    }

    /**
     * Set color to a country during create phases
     *
     * @param country
     * @param color
     */
    public void setColorToCountry(CountryModel country, Color color) {
        for (int i = 0; i < this.countryList.size(); i++) {
            if (this.countryList.get(i).equals(country)) {
                this.countryList.get(i).setBackgroundColor(color);
            }
        }
        callObservers();
        System.out.println(this.countryList);
    }

    /**
     * Set neighboring country for the countries entered during create screens
     *
     * @param leftModel
     * @param rightModel
     */
    public boolean setNeighbouringCountry(CountryModel leftModel, CountryModel rightModel) {
        for (int i = 0; i < this.getCountries().size(); i++) {
            if (this.getCountries().get(i).equals(leftModel)) {
                List<CountryModel> temp = this.getCountries().get(i).getLinkedCountries();
                if (temp == null) {
                    temp = new ArrayList<CountryModel>();
                }
                temp.add((CountryModel) rightModel);
                this.getCountries().get(i).setLinkedCountries(temp);
                this.setLeftModelIndex(i);
            } else if (this.getCountries().get(i).equals(rightModel)) {
                List<CountryModel> temp = this.getCountries().get(i).getLinkedCountries();
                if (temp == null) {
                    temp = new ArrayList<CountryModel>();
                }
                temp.add((CountryModel) leftModel);
                this.getCountries().get(i).setLinkedCountries(temp);
                this.setRightModelIndex(i);
            }
        }
        callObservers();
        return true;
    }

    /**
     * Remove neighbouring country whenever remove button is pressed upon in view
     *
     * @param leftModelCountry
     * @param rightModelCountry
     */
    public void removeNeighbouringCountry(CountryModel leftModelCountry, CountryModel rightModelCountry) {

        for (int i = 0; i < this.getCountries().size(); i++) {
            if (this.getCountries().get(i).equals(leftModelCountry)) {
                List<CountryModel> temp = this.getCountries().get(i).getLinkedCountries();
                if (temp == null) {
                    temp = new ArrayList<CountryModel>();
                }
                temp.remove((CountryModel) rightModelCountry);
                this.getCountries().get(i).setLinkedCountries(temp);
                this.setLeftModelIndex(i);
            } else if (this.getCountries().get(i).equals(rightModelCountry)) {
                List<CountryModel> temp = this.getCountries().get(i).getLinkedCountries();
                if (temp == null) {
                    temp = new ArrayList<CountryModel>();
                }
                temp.remove((CountryModel) leftModelCountry);
                this.getCountries().get(i).setLinkedCountries(temp);
                this.setRightModelIndex(i);
            }
        }
        callObservers();
    }

    /**
     * Assign armies in robin round fashion
     *
     * @param loopvlaue
     * @param namePlayer
     * @param Country
     * @param selectedArmies
     * @param listOfPlayers
     */
    public void robinTroopAssignButton(int loopvlaue, String namePlayer, CountryModel Country, int selectedArmies,
                                       List<PlayerModel> listOfPlayers) {
        System.out.println("selectedArmies " + selectedArmies);
        for (int i = 0; i < this.countryList.size(); i++) {
            if (Country.getCountryName().equals(this.countryList.get(i).getCountryName())) {
                int prevArmies;
                System.out.println("namePlayer " + namePlayer);
                for (int j = 0; j < listOfPlayers.size(); j++) {
                    if (namePlayer.equals(listOfPlayers.get(j).getName())) {
                        if (listOfPlayers.get(j).getremainTroop() > 0) {
                            int remainTroops = listOfPlayers.get(j).getremainTroop() - selectedArmies;
                            System.out.println("remainArmies[i] " + remainTroops);
                            prevArmies = this.countryList.get(i).getArmies();
                            this.countryList.get(i).setArmies(prevArmies + selectedArmies);
                            listOfPlayers.get(j).setremainTroop(remainTroops);
                        } else {
                            checkForRemainArmies(loopvlaue, listOfPlayers);
                        }
                    }
                }
            }
        }
        callObservers();
    }

    /**
     * Check on Remaining Armies
     *
     * @param loopvlaue
     * @param listOfPlayers
     */
    public void checkForRemainArmies(int loopvlaue, List<PlayerModel> listOfPlayers) {
        loopvlaue++;

        while (loopvlaue < listOfPlayers.size()) {

            if (listOfPlayers.get(loopvlaue).getremainTroop() == 0) {
                loopvlaue++;
            } else {
                loopvlaue--;
                break;
            }
        }
    }

    /**
     * Assign moving armies in fortification
     *
     * @param armies
     * @param fromCountryName
     * @param toCountryName
     */
    public boolean setMovingArmies(int armies, CountryModel fromCountryName, CountryModel toCountryName) {
        boolean returnvalue = false;
        int previousArmies = 0;
        for (int i = 0; i < this.getCountries().size(); i++) {
            if (fromCountryName.equals(this.getCountries().get(i))) {
                previousArmies = this.getCountries().get(i).getArmies();
                this.getCountries().get(i).setArmies(previousArmies - armies);
                returnvalue = true;
            }
            if (toCountryName.equals(this.getCountries().get(i))) {
                previousArmies = this.getCountries().get(i).getArmies();
                this.getCountries().get(i).setArmies(previousArmies + armies);
                returnvalue = true;
            }
        }
        return returnvalue;
    }

    /**
     * set player's turn
     */
    public void setPlayerTurn(PlayerModel playerModel) {
        this.playerTurn = playerModel;
    }

    /**
     * @return the playerTurn
     */
    public PlayerModel getPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * @return the playerIndex
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     * @param playerIndex the playerIndex to set
     */
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * @return the listOfPlayers
     */
    public ArrayList<PlayerModel> getListOfPlayers() {
        return listOfPlayers;
    }

    /**
     * @param listOfPlayers the listOfPlayers to set
     */
    public void setListOfPlayers(ArrayList<PlayerModel> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

}