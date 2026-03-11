package com.risk.services;

import com.risk.model.Continent;
import com.risk.model.Country;

import java.util.ArrayList;
import java.util.Map;

public class MapSerializer {

    private static final String COMMA = ",";

    public static String serialize(MapGraph mapGraph, ArrayList<String> mapTagData) {

        StringBuilder sb = new StringBuilder();

        // [Map]
        sb.append("[Map]\n");
        for (String line : mapTagData) {
            sb.append(line).append("\n");
        }
        sb.append("\n");

        // [Continents]
        sb.append("[Continents]\n");
        for (Map.Entry<String, Continent> entry : mapGraph.getContinents().entrySet()) {
            sb.append(entry.getValue().getName())
                    .append("=")
                    .append(entry.getValue().getControlValue())
                    .append("\n");
        }
        sb.append("\n");

        // [Territories]
        sb.append("[Territories]\n");
        for (Map.Entry<Country, ArrayList<Country>> entry : mapGraph.getAdjacentCountries().entrySet()) {

            Country country = entry.getKey();
            ArrayList<Country> neighbours = entry.getValue();

            String line = country.getName()
                    + COMMA + country.getxValue()
                    + COMMA + country.getyValue()
                    + COMMA + country.getContinent();

            for (Country neighbour : neighbours) {
                line += COMMA + neighbour.getName();
            }

            sb.append(line).append("\n");
        }

        return sb.toString();
    }
}
