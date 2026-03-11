package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;

public class MapEditorActions {
    private final MapIO mapIO;

    public MapEditorActions(MapIO mapIO) {
        this.mapIO = mapIO;
    }

    public void addContinent(String name, int controlValue) {
        Continent continent = new Continent(name, controlValue);
        mapIO.getMapGraph().addContinent(continent);
    }

    public void removeContinent(String name) {
        mapIO.getMapGraph().removeContinent(mapIO.getMapGraph().getContinents().get(name));
    }

    public void addCountry(Country country) {
        mapIO.getMapGraph().addCountry(country);
    }

    public void removeCountry(String name) {
        mapIO.getMapGraph().removeCountry(mapIO.getMapGraph().getCountrySet().get(name));
    }

    public void addEdge(Country c1, Country c2) {
        mapIO.getMapGraph().addEdgeBetweenCountries(c1, c2);
    }

    public void removeEdge(Country c1, Country c2) {
        mapIO.getMapGraph().deleteEdgeBetweenCountries(c1, c2);
    }
}

