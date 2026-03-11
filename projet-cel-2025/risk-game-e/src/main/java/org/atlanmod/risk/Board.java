package org.atlanmod.risk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class sets up the countries and continents on the org.atlanmod.risk.Risk game board.
 * 
 * @author Ted Mader
 * @version Alpha
 * @date 5/02/14
 **/
public class Board {

	private ArrayList<Country> countriesList;
	private HashMap<String, Country> countriesMap;
    private HashMap<String, Continent> continentsMap;
	
    public Board() {
		countriesList = new ArrayList<>();
        initializeMaps();

    }

    /**
     * Loads the information needed to construct the board and constructs the country and continent objects
     * needed for the board from three files.  The first file lists all the countries.  The second file lists 
     * all of the continents and which countries are on a given continent.  The third file lists the adjacencies 
     * for each country.
     **/
    public boolean loadBoard(String[] countriesArray, String[] adjacenciesFileArray, String[] continentsFileArray) {


        initializeMaps();

        // Populates countriesMap
        loadCountries(countriesArray);

        // Populates country adjacencies
        loadAdjacencies(adjacenciesFileArray);
        // Populates continents
        loadContinents(continentsFileArray);
        // Sets isLoaded to true if successful
		return true;
	}

    private void initializeMaps() {
        countriesMap = new HashMap<>();
        continentsMap = new HashMap<>();
    }

    private void loadContinents(String[] continentsFileArray) {
        String[] continentsArray;
        ArrayList<Country> memberCountries;
        for (String s : continentsFileArray) {
            // Splits the data of each line
            continentsArray = s.split(",");

            memberCountries = new ArrayList<>();

            // Populates memberCountries, starting at index 2
            for (int j = 2; j < continentsArray.length; j++) {


                memberCountries.add(countriesMap.get(continentsArray[j]));
            }

            // Populates continents hash map
            continentsMap.put(continentsArray[0], new Continent(continentsArray[0], Integer.parseInt(continentsArray[1]), memberCountries));
        }
    }

    private void loadAdjacencies(String[] adjacenciesFileArray) {
        ArrayList<Country> adjacenciesList;
        String[] adjacenciesArray;
        for (String s : adjacenciesFileArray) {


            // Splits each line into the individual country names
            adjacenciesArray = s.split(",");


            adjacenciesList = new ArrayList<>();

            // Creates a list of countries adjacent to the country in index 0
            for (int j = 1; j < adjacenciesArray.length; j++) {


                adjacenciesList.add(countriesMap.get(adjacenciesArray[j]));

            }

            // Adds the adjacencies to the country
            countriesMap.get(adjacenciesArray[0]).addAdjacencies(adjacenciesList);
        }
    }

    private void loadCountries(String[] countriesArray) {
        for (String s : countriesArray) {

            countriesMap.put(s, new Country(s));
        }

        countriesList = new ArrayList<>(countriesMap.values());
    }

    /**
     * Returns a list containing the continent objects the board has
     * */
    public List<Continent> getContinents() {
		return new ArrayList<>(continentsMap.values());
    }


    /**
     * Returns the continent object whose name is the string continentName
     **/
    public Continent getContinentByName(String continentName) {
		return continentsMap.get(continentName);
    }


    /**
     * Returns the number of bonus armies awarded to a player for controlling all the countries in
     * the continent whose name is the string continentName
     **/
    public int getBonusArmies(String continentName) {
		return continentsMap.get(continentName).getBonusArmies();
    }


    /**
     * Returns a list of the country objects that are in the continent specified 
     * by the string continentName
     **/
    public List<Country> getMemberCountries(String continentName) {
		return continentsMap.get(continentName).getMemberCountries();
    }


    /**
     * Returns a list of all of the country objects in the board
     **/
    public List<Country> getCountries() {
		return countriesList;
    }

    /**
     * Returns the country object for the country specified by the string
     * countryName
     **/
    public Country getCountryByName(String countryName) {
		return countriesMap.get(countryName);
    }


    /**
     * Sets the occupant of the country object specified by the string countryName
     * to be the player object supplied as an argument.
     **/
    public void setOccupant(String countryName, Player occupant) {
		countriesMap.get(countryName).setOccupant(occupant);
    }


    /**
     * Returns the player object that currently occupies the country specufied by
     * string countryName
     **/
    public Player getOccupant(String countryName) {
		return countriesMap.get(countryName).getOccupant();
    }


    /**
     * Sets the number of armies currently in the country specified by the string
     * countryName to the integer supplied as an argument
     **/
    public void setNumberArmies(String countryName, int numberArmies) {
		countriesMap.get(countryName).setNumArmies(numberArmies);
    }


    /**
     * Returns the number of armies currently in the country specified by the string
     * countryName
     **/
    public int getNumberArmies(String countryName) {
		return countriesMap.get(countryName).getArmies();
    }


    /**
     * Returns a list of the country objects that are the countries adjacent to the country
     * specified by the string countryName on the board
     **/
    public List<Country> getAdjacencies(String countryName) {
		return countriesMap.get(countryName).getAdjacencies();
    }
	
	public List<Country> getUnoccupied() {
  ArrayList<Country> unoccupied;

		unoccupied = new ArrayList<>();

        for (Country country : countriesList) {

            if (!country.hasPlayer()) {
                unoccupied.add(country);
            }
        }
		return unoccupied;
	}
	
	public boolean checkAdjacency(String countryA, String countryB) {

        return countriesMap.get(countryA).getAdjacencies().contains(countriesMap.get(countryB));
	}
}
