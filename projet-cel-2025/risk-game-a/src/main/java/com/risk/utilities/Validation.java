package com.risk.utilities;

import com.risk.model.ContinentsModel;
import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;


// TODO: Auto-generated Javadoc
/**
 * This class is to valdiate map for its correctness.
 *
 * @author Gursimran
 */
public class Validation {

    /**
     * Non continent validation.
     *
     * @param mapModel the map model
     * @return true, if successful
     */
    public boolean nonContinentValidation(GameMapModel mapModel) {
        int flagIntra = 0;
        int flagInter = 0;

        for (int i = 0; i < mapModel.getContinents().size(); i++) {
            for (int j = 0; j < mapModel.getContinents().get(i).getCoveredCountries().size(); j++) {
                for (int k = 0; k < mapModel.getContinents().get(i).getCoveredCountries().get(j).getLinkedCountries()
                        .size(); k++) {
                    try
                    {
                        if (mapModel.getContinents().get(i).getCoveredCountries().get(j).getLinkedCountries().get(k)
                                .getcontinentName()
                                .equals(mapModel.getContinents().get(i).getCoveredCountries().get(j).getcontinentName())) {
                            flagIntra++;
                        } else {
                            flagInter++;
                        }
                    }
                    catch(Exception E)
                    {
                        JOptionPane.showMessageDialog(null,
                                mapModel.getContinents().get(i).getCoveredCountries().get(j).getLinkedCountries().get(k).getCountryName()+" cannot be linked properly with " + mapModel.getContinents().get(i).getCoveredCountries().get(j).getCountryName(), "Map Loaded",
                                JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    }
                }
                if (flagIntra < 1) {
                    JOptionPane.showMessageDialog(null,
                            mapModel.getContinents().get(i).getCoveredCountries().get(j)+" does not have a link within its continent!", "Map Loaded",
                            JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
                flagIntra = 0;
            }
            if (flagInter < 1) {
                JOptionPane.showMessageDialog(null,
                        mapModel.getContinents().get(i)+" does not have a link with any other continent!", "Map Loaded",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;

            }
            flagInter = 0;
        }
        return false;
    }

    /**
     * Check on unlinked Continent Validation.
     *
     * @param mapModel the map model
     * @return true, if successful
     */
    public boolean unlinkedContinentValidation(GameMapModel mapModel) {
        boolean flag = true;
        List<CountryModel> listOfCountrys = mapModel.getCountries();
        List<CountryModel> listOfLinkedCountries;

        HashMap<CountryModel, CountryModel> listOfCountriesInContinent = new HashMap<CountryModel, CountryModel>();

        for (int i = 0; i < mapModel.getContinents().size(); i++) {
            for (int j = 0; j < listOfCountrys.size(); j++) {
                if (mapModel.getContinents().get(i).getContinentName()
                        .equals(listOfCountrys.get(j).getcontinentName())) {
                    listOfCountriesInContinent.put(listOfCountrys.get(j), listOfCountrys.get(j));
                }

            }
            Iterator it = listOfCountriesInContinent.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<CountryModel, CountryModel> pair = (Map.Entry<CountryModel, CountryModel>) it.next();
                listOfLinkedCountries = pair.getValue().getLinkedCountries();
                int counter = 0;
                for (int k = 0; k < listOfLinkedCountries.size(); k++) {
                    if (listOfCountriesInContinent.containsValue((listOfLinkedCountries.get(k)))) {
                        counter++;
                    }
                }

                if (counter == listOfLinkedCountries.size() - 1) {
                    return false;
                }
            }

        }
        return flag;
    }

    /**
     * Check on empty link Country Validation.
     *
     * @param mapModel the map model
     * @return boolean
     */
    public boolean emptyLinkCountryValidation(GameMapModel mapModel) {
        List<CountryModel> listOfCountrys = mapModel.getCountries();
        List<CountryModel> linkedCountry;
        for (int j = 0; j < listOfCountrys.size(); j++) {
            linkedCountry = listOfCountrys.get(j).getLinkedCountries();
            if (linkedCountry == null || linkedCountry.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check on Inter Linked Contient.
     *
     * @param mapModel the map model
     * @return boolean
     */
    public boolean checkInterlinkedContinent(GameMapModel mapModel) {

        String continent = "";
        List<ContinentsModel> listOfContinents = mapModel.getContinents();
        List<CountryModel> listOfCountrys = mapModel.getCountries();
        List<String> Countryname = new ArrayList<String>();
        ;
        int numb;
        boolean emptyLinkContinent = false;
        try {
            for (int j = 0; j < listOfContinents.size(); j++) {
                continent = listOfContinents.get(j).getContinentName();
                numb = 0;
                for (int i = 0; i < listOfCountrys.size(); i++) {
                    if (continent.equals(listOfCountrys.get(i).getcontinentName())) {
                        Countryname.add(listOfCountrys.get(i).getCountryName());
                    }
                }
                for (int i = 0; i < Countryname.size(); i++) {
                    for (int k = 0; k < listOfCountrys.size(); k++) {
                        if (!continent.equals(listOfCountrys.get(k).getcontinentName())) {
                            for (int a = 0; a < listOfCountrys.get(k).getLinkedCountries().size(); a++) {
                                if (Countryname.get(i)
                                        .equals(listOfCountrys.get(k).getLinkedCountries().get(a).getCountryName())) {
                                    numb++;
                                }
                            }
                        }
                    }
                }
                if (numb == 0) {
                    emptyLinkContinent = true;
                    return emptyLinkContinent;
                }
            }
        } catch (NullPointerException e) {
            JOptionPane.showOptionDialog(null, "All continents are not linked", "Invalid", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null);
        }
        return emptyLinkContinent;

    }

    /**
     * Check on empty Continent Validation.
     *
     * @param mapModel the map model
     * @return boolean
     */
    public boolean emptyContinentValidation(GameMapModel mapModel) {
        List<ContinentsModel> listOfContinents = mapModel.getContinents();
        List<CountryModel> listOfCountrys = mapModel.getCountries();
        String continentName = " ";
        int numb;

        for (int i = 0; i < listOfContinents.size(); i++) {
            continentName = listOfContinents.get(i).getContinentName();
            numb = 0;
            for (int j = 0; j < listOfCountrys.size(); j++) {
                if (continentName.equals(listOfCountrys.get(j).getcontinentName())) {
                    numb++;
                }
            }
            if (numb == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check on valid move for fortification.
     *
     * @param gameMapModel the game map model
     * @param fromCountryModel the from country model
     * @param toCountryModel the to country model
     * @return true, if successful
     */
    public boolean checkIfValidMove(GameMapModel gameMapModel, CountryModel fromCountryModel,
                                    CountryModel toCountryModel) {

        LinkedList<CountryModel> temp;

        HashMap<CountryModel, Integer> mapOfCountries = new HashMap<CountryModel, Integer>();

        for (int i = 0; i < gameMapModel.getCountries().size(); i++) {
            mapOfCountries.put(gameMapModel.getCountries().get(i), i);
        }

        if (isReachable(mapOfCountries.get(fromCountryModel), mapOfCountries.get(toCountryModel), gameMapModel,
                mapOfCountries)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is reachable.
     *
     * @param s              the s
     * @param d              the d
     * @param gameMapModel   the game map model
     * @param mapOfCountries the map of countries
     * @return the boolean
     */
    // prints BFS traversal from a given source s
    Boolean isReachable(int s, int d, GameMapModel gameMapModel, HashMap<CountryModel, Integer> mapOfCountries) {
        LinkedList<Integer> temp;

        boolean visited[] = new boolean[gameMapModel.getCountries().size()];

        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        Iterator<Integer> i;
        while (queue.size() != 0) {
            s = queue.poll();

            int n;

            List<Integer> m = new ArrayList<Integer>();
            for (int l = 0; l < gameMapModel.getCountries().get(s).getLinkedCountries().size(); l++) {
                if (gameMapModel.getCountries().get(s).getRulerName()
                        .equals(gameMapModel.getCountries().get(s).getLinkedCountries().get(l).getRulerName())) {
                    m.add(mapOfCountries.get(gameMapModel.getCountries().get(s).getLinkedCountries().get(l)));
                }
            }

            i = m.listIterator();

            while (i.hasNext()) {
                n = i.next();

                // If this adjacent node is the destination node,
                // then return true
                if (n == d)
                    return true;

                // Else, continue to do BFS
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        // If BFS is complete without visited d
        return false;
    }

    /**
     * End of game.
     *
     * @param gamePlayModel the game play model
     * @return true, if successful
     */
    public boolean endOfGame(GamePlayModel gamePlayModel) {
        for (int i = 0; i < gamePlayModel.getPlayers().size(); i++) {
            if (gamePlayModel.getPlayers().get(i).getOwnedCountries().size() == gamePlayModel.getGameMap()
                    .getCountries().size()) {
                return true;
            }
        }
        return false;
    }
    public String determineWinner(GamePlayModel gamePlayModel) {
        for (int i = 0; i < gamePlayModel.getPlayers().size(); i++) {
            if (gamePlayModel.getPlayers().get(i).getOwnedCountries().size() == gamePlayModel.getGameMap()
                    .getCountries().size()) {
                return  gamePlayModel.getPlayers().get(i).getName();
            }
        }
        return "draw";
    }

}
