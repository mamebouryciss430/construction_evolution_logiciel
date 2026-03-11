package com.risk.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.risk.model.ContinentsModel;
import com.risk.model.CountryModel;

/**
 * "ReadFile" class reads map file and gives continents and countries details
 *
 * @author KaranPannu
 */
public class ReadFile {

    public static File FILE;

    /**
     * This method reads the map file and returns continent details such as name and
     * its value-control.
     *
     * @return the array list of continents
     */
    public ArrayList<ContinentsModel> getMapContinentDetails() {

        File file = getFile();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<ContinentsModel> listOfContinents = new ArrayList<ContinentsModel>();

        while (sc.hasNextLine()) {
            String sc1 = sc.nextLine();
            if (sc1.contains("[Continents]")) {
                String sc2 = sc.nextLine();
                sc2.trim();
                while (!"".equals(sc2)) {
                    int positionEqual = sc2.indexOf('=');
                    String sc3 = sc2.substring(0, positionEqual);
                    System.out.println("Continents: " + sc3);

                    String sc4 = sc2.substring(positionEqual + 1);
                    int result = Integer.parseInt(sc4);
                    System.out.println("Value: " + sc4);

                    ContinentsModel tempMyContinents = new ContinentsModel(sc3, result);
                    listOfContinents.add(tempMyContinents);
                    sc2 = sc.nextLine();
                    sc2.trim();
                }
            }
        }
        return listOfContinents;
    }

    /**
     * This method reads the map file and returns country details such as name,
     * position, continent that it belongs to, and list of countries that it is
     * connected to.
     *
     * @return the array list of countries
     */
    public ArrayList<CountryModel> getMapCountryDetails() {
        File file = getFile();
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<CountryModel> listOfCountryModel;
        HashMap<String, CountryModel> listOfCountries = new HashMap<String, CountryModel>();

        while (sc.hasNextLine()) {
            String fileData = sc.nextLine();

            if (fileData.contains("[Territories]")) {

                while (sc.hasNextLine()) {
                    String territories = sc.nextLine();
                    territories.trim();

                    if (!"".equals(territories)) {

                        int indexOfCountryName = territories.indexOf(',');
                        String countryName = territories.substring(0, indexOfCountryName).trim();
                        CountryModel cm = listOfCountries.get(countryName);
                        if (cm == null) {
                            cm = new CountryModel();
                            cm.setCountryName(countryName);
                            listOfCountries.put(cm.getCountryName(), cm);
                        }

                        int indexOfXPos = territories.indexOf(',', (indexOfCountryName + 1));
                        String xPosition = territories.substring((indexOfCountryName + 1), indexOfXPos);
                        cm.setXPosition(Integer.parseInt(xPosition.trim()));
                        System.out.println("xposition: " + xPosition);

                        int indexOfYPos = territories.indexOf(',', (indexOfXPos + 1));
                        String yPosition = territories.substring((indexOfXPos + 1), indexOfYPos);
                        cm.setYPosition(Integer.parseInt(yPosition.trim()));
                        System.out.println("yPosition" + yPosition);

                        int indexOfContinent = territories.indexOf(',', (indexOfYPos + 1));
                        String continent = territories.substring((indexOfYPos + 1), indexOfContinent).trim();
                        cm.setContinentName(continent.trim());
                        System.out.println("Continent: " + continent);

                        String neighbouringCountries = territories.substring((indexOfContinent + 1)).trim();
                        List<String> listOfNeighbouringCountries = Arrays
                                .asList(neighbouringCountries.split("\\s*,\\s*"));
                        CountryModel newNeighbour;

                        List<CountryModel> linkedCountriesList = new ArrayList<CountryModel>();

                        for (int i = 0; i < listOfNeighbouringCountries.size(); i++) {
                            if (listOfCountries.containsKey(listOfNeighbouringCountries.get(i).trim())) {
                                newNeighbour = listOfCountries.get(listOfNeighbouringCountries.get(i).trim());
                            } else {
                                newNeighbour = new CountryModel();
                                newNeighbour.setCountryName(listOfNeighbouringCountries.get(i).trim());
                            }
                            listOfCountries.put(listOfNeighbouringCountries.get(i).trim(), newNeighbour);
                            linkedCountriesList.add(newNeighbour);
                        }
                        cm.setLinkedCountries(linkedCountriesList);
                    }
                }
            }
        }
        Collection<CountryModel> c = listOfCountries.values();
        listOfCountryModel = new ArrayList<CountryModel>(c);
        return listOfCountryModel;
    }

    /**
     * Setting Set file
     *
     * @param file
     */
    public void setFile(File file) {
        FILE = file;
    }

    /**
     * Getting File
     *
     * @return
     */
    public File getFile() {
        return FILE;
    }

    /**
     * Validate read continent.
     *
     * @param list the list
     * @param arrayList the array list
     * @return true, if successful
     */
    public boolean validateReadContinent(List<ContinentsModel> list, List<ContinentsModel> arrayList) {
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (list.get(i).getContinentName().equals(arrayList.get(j).getContinentName())) {
                    counter++;
                }
            }
            if (counter == 1) {
                counter = 0;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate read country.
     *
     * @param list the list
     * @param arrayList the array list
     * @return true, if successful
     */
    public boolean validateReadCountry(List<CountryModel> list, List<CountryModel> arrayList) {
        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                if (list.get(i).getCountryName().equals(arrayList.get(j).getCountryName())) {
                    counter++;
                }
            }
            if (counter == 1) {
                counter = 0;
            } else {
                return false;
            }
        }
        return true;
    }

}