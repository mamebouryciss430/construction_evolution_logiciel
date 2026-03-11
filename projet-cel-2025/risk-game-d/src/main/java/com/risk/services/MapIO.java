package com.risk.services;

import com.risk.model.Continent;

import com.risk.model.Country;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Map Input Output to read the contents from an existing map or to create a new
 * Map for the game.
 *
 * @author Karandeep Singh
 * @author Palash Jain
 */
public class MapIO implements Serializable {

    public String getFileName() {
        return fileName;
    }

    /** FileName of the existing map */
    private String fileName;

    /** List to hold the contents under the Map tag from the map */
    private ArrayList<String> mapTagData;

    /** FileName for the new map */
    private String newFileName;

    /** Object of MapGraph */
    private MapGraph mapGraph;

    /**
     * Constructor to load the contents of a New Map.
     */
    public MapIO() {
        this.mapGraph = new MapGraph();
        this.mapTagData = new ArrayList<>();
    }

    /**
     * Constructor to load the contents of an Existing Map.
     *
     * @param map
     *            Object of MapValidate
     */
    public MapIO(MapValidate map) {
        this.mapGraph = new MapGraph();
        this.mapGraph.setContinents(map.getContinentSetOfTerritories());
        this.mapGraph.setAdjacentCountries(map.getAdjacentCountries());
        this.fileName = map.getFileName();
        this.mapTagData = map.getMapTagData();
        this.mapGraph.setCountrySet(map.getCountrySet());

    }

    /**
     * Constructor to create a MapIO with an existing file name.
     */
    public MapIO(String fileName) {
        this.fileName = fileName;
        this.mapGraph = new MapGraph();
        this.mapTagData = new ArrayList<>();
    }

    /**
     * Constructor to create a MapIO with both fileName and newFileName.
     */
    public MapIO(String fileName, String newFileName) {
        this.fileName = fileName;
        this.newFileName = newFileName;
        this.mapGraph = new MapGraph();
        this.mapTagData = new ArrayList<>();
    }


    /**
     * Method to read the data of existing Map file.
     *
     * @return contents of the Map file
     */
    public MapIO readFile() {
        return this;
    }

    /**
     * MapIO write contents to .map file
     *
     * @param isNewFile
     *            boolean
     *
     * @return true if file is written correctly
     */
    public boolean writeToFile(boolean isNewFile) {
        File file;
        StringBuilder stringBuilder = new StringBuilder();
        if (isNewFile) {
            file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.fileName + ".map");
        } else {
            file = new File(new File("").getAbsolutePath() + "\\src\\main\\maps\\" + this.newFileName + ".map");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {

            String content = MapSerializer.serialize(mapGraph, mapTagData);
            writer.write(content);

        } catch (IOException e) {
            System.out.println("IO Exception while writing to a file");
            return false;
        }
        return true;
    }

    /**
     * Object to get the MapGraph data
     *
     * @return data of the MapGraph
     */
    public MapGraph getMapGraph() {
        return mapGraph;
    }

    /**
     * Method to get the mapTagData contents
     *
     * @return mapTagData contents
     */
    public ArrayList<String> getMapTagData() {
        return mapTagData;
    }

}
