package com.risk.model;

import com.risk.common.Message;
import com.risk.common.STATE;
import com.risk.view.CountryView;

import java.io.Serializable;
import java.util.*;


public class MapModel extends Observable implements Serializable {

    private HashMap<String, Country> countries;
    private ArrayList<Continent> continents;

    public MapModel() {
        countries = new HashMap<>();
        continents = new ArrayList<>();
    }

    /**
     * initiate continents
     * @param continentsList The list of continents
     */
    void initiateContinents(String continentsList){
        int index = continentsList.indexOf("[Continents]");
        continentsList = continentsList.substring(index + 13);
        String[] list = continentsList.split("\n");
        for (String s : list) {
            int indexOfCol = s.indexOf('=');
            String continentName = s.substring(0,indexOfCol);
            int controlVal = Integer.parseInt(s.substring(indexOfCol + 1));
            Continent newContinent = new Continent(continentName,controlVal);
            newContinent.setColor();
            this.continents.add(newContinent);
        }
    }

    /**
     * initiate countries and all the neighbours
     * @param countriesList The list of countries
     */
    void initiateCountries(String countriesList){
        if (countriesList.startsWith("[")) {
            countriesList = countriesList.substring(countriesList.indexOf(']') + 2);
        }

        Arrays.stream(countriesList.split("\n"))
                .map(line -> Arrays.stream(line.split(","))
                        .map(String::trim)
                        .toArray(String[]::new))
                .forEach(this::processCountryData);
    }
    private void processCountryData(String[] data) {
        Country country = countries.computeIfAbsent(data[0], Country::new);
        Continent continent = continents.stream()
                .filter(c -> c.getName().equals(data[3]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Continent not found"));

        country.setX(data[1]);
        country.setY(data[2]);
        country.setContinent(continent);
        continent.addCountry(country);

        // voisins
        Arrays.stream(data).skip(4)
                .map(name -> countries.computeIfAbsent(name, Country::new))
                .forEach(country::addEdge);
    }


    /**
     * link every country in the countries with corresponding CountryView
     * @param countryViewHashMap The CountryView map
     */
    public void linkCountryObservers(HashMap<Integer, CountryView> countryViewHashMap){

        //link every countryView
        int id = 1;
        for (String key:countries.keySet()) {
            countries.get(key).addObserver(countryViewHashMap.get(id));
            id ++;
        }
        //send next state message
        Message message = new Message(STATE.PLAYER_NUMBER,null);
        notify(message);
    }

    /**
     *  notify the view that model state has changed
     * @param message The message to send to the view, may include some important information
     */
    private void notify(Message message) {
        setChanged();
        notifyObservers(message);
    }
    public void setContinents(List<Continent> continents) { continents = (ArrayList<Continent>) continents; }
    public HashMap<String, Country> getCountries() { return countries; }
    public ArrayList<Continent> getContinents() { return continents; }
}
