package com.risk.controller;

import com.risk.model.ContinentsModel;
import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class SameCountryValidationTest {
    List<ContinentsModel> continentList;
    List<CountryModel> countryList;
    GameMapModel gameMapModel;
    ReadFile readFile;
    File file;
    CreateCountryController createCountryController ;
    HashMap<String, Color> colorMapList = new HashMap<>();
    HashMap<String, ArrayList<Point>> mapPointList = new HashMap<>();
    HashMap<String, Integer> indexMap = new HashMap<>();

    private static boolean setUpIsDone = false;

    /**
     * Set up variables
     */
    @BeforeEach
    public void setUp() {
        if (setUpIsDone) {
            return;
        }
        // do the setup
        readFile = new ReadFile();
        file = new File(Constant.filePath.toUri());
        readFile.setFile(file);
        this.continentList = readFile.getMapContinentDetails();
        this.countryList = readFile.getMapCountryDetails();
        gameMapModel = new GameMapModel();
        setUpIsDone = true;
        ArrayList<ArrayList<Point>> pointsList = new ArrayList<>();
        ArrayList<Color> colorList = new ArrayList<>();

        colorList.add(Color.RED);
        colorList.add(Color.GREEN);
        colorList.add(Color.BLUE);
        colorList.add(Color.CYAN);
        colorList.add(Color.ORANGE);

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
        for (int i = 0; i < this.gameMapModel.getContinents().size(); i++) {
            mapPointList.put(this.gameMapModel.getContinents().get(i).getContinentName(), pointsList.get(i));
            colorMapList.put(this.gameMapModel.getContinents().get(i).getContinentName(), colorList.get(i));
            indexMap.put(this.gameMapModel.getContinents().get(i).getContinentName(), 0);
        }
        createCountryController = new CreateCountryController(gameMapModel,mapPointList,colorMapList,indexMap);
    }

    /**
     * Test checking same country validation
     */
    @Test
    @Ignore("Slow graphical test")
    @Disabled("FIXME: Unit Tests should not be blocking")
    public void testSameCountryValidation() {
        assertFalse(createCountryController.sameCountryValidation());
    }

}