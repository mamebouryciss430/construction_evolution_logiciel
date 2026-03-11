package com.risk.model;

import java.util.ArrayList;

/**
 * "ContinentsModel" is a class for continents of the map. "continentName" and
 * "valueControl" are attributes of an object
 *
 * @author Namita
 *
 */
public class ContinentsModel {

    /** The continent name. */
    private String continentName;

    /** The value control. */
    private int valueControl;

    /** The list of countries. */
    private ArrayList<CountryModel> listOfCountries = new ArrayList<CountryModel>();

    /**
     * Constructor of ContinentsModel.
     *
     * @param continentName the continent name
     * @param valueControl  the value control
     */
    public ContinentsModel(String continentName, int valueControl) {
        this.continentName = continentName;
        this.valueControl = valueControl;
    }

    public ContinentsModel() {
    }

    /**
     * Returns the continent name.
     *
     * @return the continent name.
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Sets the continent name.
     *
     * @param continentName the new continent name
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Returns the continent value control.
     *
     * @return the value Control.
     */
    public int getValueControl() {
        return valueControl;
    }

    /**
     * Sets the continent value Control.
     *
     * @param valueControl the new value control
     */
    public void setValueControl(int valueControl) {
        this.valueControl = valueControl;
    }

    /**
     * Sets the covered countries.
     *
     * @param parmCountry the new covered countries
     */
    public void setCoveredCountries(CountryModel parmCountry) {
        this.listOfCountries.add(parmCountry);
    }

    /**
     * Gets the covered countries.
     *
     * @return the covered countries
     */
    public ArrayList<CountryModel> getCoveredCountries() {
        return listOfCountries;
    }
}
