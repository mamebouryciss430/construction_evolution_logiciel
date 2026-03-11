package com.risk.model;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * "CountryModel" represents a country's properties such as name, position,
 * continent name that it belongs to, countries that it connected to, owner
 * (player who owns it) name, Number of armies in, and color.
 *
 * @author Namita
 *
 */
public class CountryModel extends JButton {

    /** The country name. */
    private String countryName;

    /** The x position. */
    private int xPosition;

    /** The y position. */
    private int yPosition;

    /** The continent name. */
    private String continentName;

    /** The linked countries. */
    private List<CountryModel> linkedCountries = new ArrayList<CountryModel>();

    /** The armies. */
    private int armies;

    /** The background color. */
    private Color backgroundColor;

    /** The border color. */
    private Color borderColor;

    /** The ruler name. */
    private String rulerName;

    /**
     * Constructor of CountryModel with parameters.
     *
     * @param countryName     the country name
     * @param xPosition       the x position
     * @param yPosition       the y position
     * @param continentName   the continent name
     * @param linkedCountries the linked countries
     * @param armies          the armies
     * @param rulerName       the ruler name
     */
    public CountryModel(String countryName, int xPosition, int yPosition, String continentName, List linkedCountries,
                        int armies, String rulerName) {
        this.countryName = countryName;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.continentName = continentName;
        this.linkedCountries = linkedCountries;
        this.armies = armies;
        this.backgroundColor = Color.WHITE;
        this.borderColor = Color.BLACK;
        this.rulerName = rulerName;
    }

    /**
     * Default constructor.
     */
    public CountryModel() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the country name.
     *
     * @return the country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name.
     *
     * @param countryName the new country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Gets the x position.
     *
     * @return the X Position Value.
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Sets the X Position Value.
     *
     * @param xPosition the new x position
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Gets the y position.
     *
     * @return the Y Position Value.
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Sets the Y Position Value.
     *
     * @param yPosition the new y position
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Gets the continent name.
     *
     * @return the Continent Name.
     */
    public String getcontinentName() {
        return continentName;
    }

    /**
     * Sets the Continent Name.
     *
     * @param continentName the new continent name
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Gets the linked countries.
     *
     * @return the list of array.
     */
    public List<CountryModel> getLinkedCountries() {
        return linkedCountries;
    }

    /**
     * Sets the list of array.
     *
     * @param linkedCountries the new linked countries
     */
    public void setLinkedCountries(List<CountryModel> linkedCountries) {
        this.linkedCountries = linkedCountries;
    }

    /**
     * Gets the armies.
     *
     * @return the army number of the country.
     */
    public int getArmies() {
        return armies;
    }

    /**
     * Sets the army number of the country.
     *
     * @param armies the new armies
     */
    public void setArmies(int armies) {
        this.armies = armies;
    }

    /**
     * Gets the background color.
     *
     * @return the color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the color of the country.
     *
     * @param color the new background color
     */
    public void setBackgroundColor(Color color) {
        this.setBackground(color);
        this.backgroundColor = color;
    }

    /**
     * Gets the border color.
     *
     * @return the borderColor
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Sets the borderColor.
     *
     * @param borderColor the new border color
     */
    public void setBorderColor(Color borderColor) {
        this.setBorder(new LineBorder(borderColor));
        this.borderColor = borderColor;
    }

    /**
     * Gets the ruler name.
     *
     * @return the ruler name of the country.
     */
    public String getRulerName() {
        return rulerName;
    }

    /**
     * Sets the ruler name of the country.
     *
     * @param rulerName the new ruler name
     */
    public void setRulerName(String rulerName) {
        this.rulerName = rulerName;
    }

}
