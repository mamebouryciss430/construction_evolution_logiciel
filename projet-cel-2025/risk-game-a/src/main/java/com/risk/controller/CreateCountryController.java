package com.risk.controller;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.risk.Environment;
import com.risk.model.ContinentsModel;
import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.view.ICreateCountryView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;

/**
 * In CreateCountryController, the data flow into model object and updates the
 * view whenever data changes.
 *
 * @version 1.0.0
 *
 */

public class CreateCountryController implements ViewActionListener {

    /** The game map model. */
    private GameMapModel gameMapModel;

    /** The create country view. */
    private ICreateCountryView createCountryView;


    /** The map point list. */
    private HashMap<String, ArrayList<Point>> mapPointList;

    /** The color map list. */
    private HashMap<String, Color> colorMapList;

    /** The index map. */
    private HashMap<String, Integer> indexMap;

    /**/
    private static  final String INVALID = "Invalid";

    /**
     * Constructor initializes values and sets the screen too visible.
     *
     * @param gameMapModel the game map model
     * @param mapPointList the map point list
     * @param colorMapList the color map list
     * @param indexMap     the index map
     */
    public CreateCountryController(GameMapModel gameMapModel, HashMap<String, ArrayList<Point>> mapPointList,
                                   HashMap<String, Color> colorMapList, HashMap<String, Integer> indexMap) {
        this.gameMapModel = gameMapModel;
        this.mapPointList = mapPointList;
        this.colorMapList = colorMapList;
        this.indexMap = indexMap;
        this.createCountryView =
                Environment.getInstance().getViewManager().newCreateCountryView(this.gameMapModel.getContinents());
        this.createCountryView.showView();
        this.gameMapModel.addObserver(this.createCountryView);
        this.createCountryView.addActionListener(this);
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {

        if (ICreateCountryView.ACTION_ADD.equals(event.getSource())) {
            handleAddCountry();
        } else if (ICreateCountryView.ACTION_NEXT.equals(event.getSource())) {
            handleActionNext();
        }
    }

     private void handleAddCountry (){

         ContinentsModel newContinentModel;
         String countryValue = createCountryView.getCountryValue();

         if (countryValue == null || countryValue.trim().isEmpty()) {
             createCountryView.showOptionDialog("Please enter a valid input", INVALID);
             return;
         }

         if (sameCountryValidation()) {
             createCountryView.showOptionDialog("Please enter a different country", INVALID);
             return;
         }

         newContinentModel = this.createCountryView.getContinentModel();

         CountryModel temp = new CountryModel();
         temp.setContinentName(newContinentModel.getContinentName());
         temp.setCountryName(countryValue);
         temp.setBackground(Color.WHITE);
         temp.setBorderColor(Color.BLACK);

         this.gameMapModel.getCountries().add(temp);
         this.gameMapModel.setCountries(this.gameMapModel.getCountries());


     }

     private void handleActionNext(){
         if (emptyContinentValidation()) {
             createCountryView.showOptionDialog("Please enter at least one country for each continent", INVALID);
             return;
         }

         for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
             CountryModel country = this.gameMapModel.getCountries().get(i);
             String continentName = country.getcontinentName();

             ArrayList<Point> pointList = this.mapPointList.get(continentName);
             int index = this.indexMap.get(continentName);

             Point position = pointList.get(index);
             System.out.println("==> " + position.x);

             country.setXPosition(position.x);
             country.setYPosition(position.y);
             country.setBackgroundColor(this.colorMapList.get(continentName));

             this.indexMap.put(continentName, index + 1);
         }

         new ConnectCountryController(this.gameMapModel);
         this.createCountryView.hideView();

     }

    /**
     * Check for same country validation.
     *
     * @return boolean
     */
    public boolean sameCountryValidation() {
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            if (this.gameMapModel.getCountries().get(i).getCountryName()
                    .equals(this.createCountryView.getCountryValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check for empty continent Value.
     *
     * @return boolean
     */
    public boolean emptyContinentValidation() {
        List<ContinentsModel> listOfContinents = this.gameMapModel.getContinents();
        List<CountryModel> listOfCountrys = this.gameMapModel.getCountries();
        String continentName = "";
        for (int i = 0; i < listOfContinents.size(); i++) {
            continentName = listOfContinents.get(i).getContinentName();
            int count = 0;
            for (int j = 0; j < listOfCountrys.size(); j++) {
                count++;
                if (continentName.equals(listOfCountrys.get(j).getcontinentName())) {
                    count = 0;
                    break;
                }
            }
            if (count == listOfCountrys.size()) {
                return true;
            }
        }
        return false;
    }
}
