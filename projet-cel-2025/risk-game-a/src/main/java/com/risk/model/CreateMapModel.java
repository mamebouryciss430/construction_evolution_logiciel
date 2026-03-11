package com.risk.model;

/**
 * "CreateMapModel" represents Map Model as an object Properties are: continents
 * array, countries array, and links like continent-country and country-country.
 *
 * @author KaranPannu
 */
public class CreateMapModel {
    /** The continents. */
    String[] continents;

    /** The countries. */
    String[] countries;

    /** The continents to countries. */
    boolean[][] continentsToCountries;

    /** The links. */
    boolean[][] links;

}
