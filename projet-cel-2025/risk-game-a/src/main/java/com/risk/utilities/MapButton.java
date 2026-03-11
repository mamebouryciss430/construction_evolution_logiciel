package com.risk.utilities;

import java.awt.Color;

import javax.swing.JButton;

import com.risk.model.CountryModel;


/**
 * "MapButton" class gives the positioning of the country.
 *
 * @author KaranPannu
 */
public class MapButton extends JButton {

    /** The x pos. */
    private int xPos;

    /** The y pos. */
    private int yPos;

    /** The country. */
    private CountryModel country;

    /** The color. */
    private Color color;

    /**
     * Gets the x pos.
     *
     * @return the xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Sets x.
     *
     * @param xPos the new x pos
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    /**
     * Gets the y pos.
     *
     * @return the yPos
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Sets y.
     *
     * @param yPos the new y pos
     */
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public CountryModel getCountry() {
        return country;
    }

    /**
     * Sets the parameter to the country.
     *
     * @param country the new country
     */
    public void setCountry(CountryModel country) {
        this.country = country;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

}
