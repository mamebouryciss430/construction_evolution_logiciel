package com.risk.services;


import com.risk.model.Continent;
import com.risk.model.Country;

public class MapEditorPrinter {

    private final MapIO mapIO;

    public MapEditorPrinter(MapIO mapIO) {
        this.mapIO = mapIO;
    }

    public void printCurrentMapContents() {
        System.out.println("\nCurrent map contents:\n");

        for (Continent continent : mapIO.getMapGraph().getContinents().values()) {
            System.out.println("Continent: " + continent.getName() +
                    " (control value: " + continent.getControlValue() + ")");

            for (Country country : continent.getListOfCountries()) {
                System.out.print("  " + country.getName() + " -> ");
                for (Country adj : country.getAdjacentCountries()) {
                    System.out.print(adj.getName() + " ");
                }
                System.out.println();
            }
        }
    }
}
