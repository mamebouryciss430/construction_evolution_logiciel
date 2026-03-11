package com.risk.controller;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import com.risk.Environment;
import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.view.ICreateContinentView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;

/**
 * In CreateContinentController, the data flow into model object and updates the
 * view whenever data changes.
 *
 * @author KaranPannu
 * @version 1.0.0
 *
 */

public class CreateContinentController implements ViewActionListener {

    /**
     * The game map model.
     */
    private GameMapModel gameMapModel;

    /**
     * The create continent view.
     */
    private ICreateContinentView createContinentView;

    /**
     * to solve duplication problem
     */
    private static final String invalid = "Invalid";

    /**
     * Constructor initializes values and sets the screen too visible.
     */
    public CreateContinentController() {
        this.gameMapModel = new GameMapModel();
        this.createContinentView =
                Environment.getInstance().getViewManager().newCreateContinentView();
        this.gameMapModel.addObserver(this.createContinentView);
        this.createContinentView.addActionListener(this);
        this.createContinentView.showView();
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {
        Object source = event.getSource();

        if (ICreateContinentView.ACTION_ADD.equals(source)) {
            handleActionAdd();
        } else if (ICreateContinentView.ACTION_NEXT.equals(event.getSource())) {
            handleActionNext();
        }
    }

    private void handleActionAdd() {
        String controlValueStr = createContinentView.getControlValue();
        String continentValue = createContinentView.getContinentValue();
        if (isEmpty(controlValueStr) || isEmpty(continentValue)) {
            createContinentView.showOptionDialog("Please enter values in all the fields", invalid);
            return;
        }
        int controlValue;
        try {
            controlValue = Integer.parseInt(controlValueStr);
        } catch (NumberFormatException e) {
            createContinentView.showOptionDialog("Control value must be a number", invalid);
            return;
        }
        System.out.println("The input from the view is " + controlValueStr + " " + continentValue);
        if (controlValue <= 0 || controlValue >= 10) {
            createContinentView.showOptionDialog("Please enter a control value between 0 and 10", invalid);
            return;
        }
        if (continentValue == null) {
            createContinentView.showOptionDialog("Please enter some country name", invalid);
            return;
        }
        for (int i = 0; i < gameMapModel.getContinents().size(); i++) {
            if (gameMapModel.getContinents().get(i).getContinentName().equals(continentValue)) {
                createContinentView.showOptionDialog("You have already added this Continent", invalid);
                return;
            }
            ContinentsModel newContinent = new ContinentsModel(continentValue, controlValue);
            gameMapModel.getContinents().add(newContinent);
            gameMapModel.setContinents(gameMapModel.getContinents());

        }
    }

    private void handleActionNext() {
        if (this.gameMapModel.getContinents().isEmpty()) {
            createContinentView.showOptionDialog("Please enter atleast one Continent to proceed", invalid);
            return;
        }
        ArrayList<ArrayList<Point>> pointsList = buildPointList();
        ArrayList<Color> colorList = buildColorList();
        HashMap<String, Color> colorMapList = new HashMap<>();
        HashMap<String, ArrayList<Point>> mapPointList = new HashMap<>();
        HashMap<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < gameMapModel.getContinents().size(); i++) {
            String continentName = gameMapModel.getContinents().get(i).getContinentName();
            mapPointList.put(continentName, pointsList.get(i));
            colorMapList.put(continentName, colorList.get(i));
            indexMap.put(continentName, 0);
        }
        new CreateCountryController(gameMapModel, mapPointList, colorMapList, indexMap);
        this.createContinentView.hideView();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private ArrayList<Color> buildColorList() {
        ArrayList<Color> colorList = new ArrayList<>();
        colorList.add(Color.RED);
        colorList.add(Color.GREEN);
        colorList.add(Color.BLUE);
        colorList.add(Color.CYAN);
        colorList.add(Color.ORANGE);
        return colorList;
    }

    private ArrayList<ArrayList<Point>> buildPointList() {
        ArrayList<ArrayList<Point>> pointsList = new ArrayList<>();
        ArrayList<Point> p = new ArrayList<>();
        p.add(new Point(330, 40));
        p.add(new Point(300, 95));
        p.add(new Point(255, 110));
        p.add(new Point(270, 120));
        p.add(new Point(325, 130));
        pointsList.add(p);

        p = new ArrayList<>();
        p.add(new Point(230, 160));
        p.add(new Point(265, 150));
        p.add(new Point(290, 160));
        p.add(new Point(300, 180));
        p.add(new Point(270, 195));
        pointsList.add(p);

        p = new ArrayList<>();
        p.add(new Point(200, 210));
        p.add(new Point(240, 200));
        p.add(new Point(255, 220));
        p.add(new Point(230, 245));
        p.add(new Point(275, 225));
        pointsList.add(p);

        p = new ArrayList<>();
        p.add(new Point(300, 210));
        p.add(new Point(290, 240));
        p.add(new Point(300, 260));
        p.add(new Point(260, 285));
        p.add(new Point(210, 270));
        pointsList.add(p);

        p = new ArrayList<>();
        p.add(new Point(165, 260));
        p.add(new Point(125, 220));
        p.add(new Point(120, 260));
        p.add(new Point(70, 290));
        p.add(new Point(30, 285));
        pointsList.add(p);
        return pointsList;
    }
}
