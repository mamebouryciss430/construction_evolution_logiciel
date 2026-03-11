package com.risk.model;

import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import com.risk.utilities.Validation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class SortCountryTest {
    GameMapModel gameMapModel;
    GamePlayModel gamePlayModel;
    Validation val;
    ReadFile readFile;
    File file;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();
    ArrayList<CountryModel> cardList = new ArrayList<CountryModel>();



    private static boolean setUpIsDone = false;

    /**
     * Set up file
     */
    @BeforeEach
    public void setUp() throws Exception {
        if (setUpIsDone) {
            return;
        }

        readFile = new ReadFile();
        countryList.add(new CountryModel("India",0,0,"Asia",null,2,"Bill"));
        countryList.add(new CountryModel("China",0,0,"Asia",null,5,"Bill"));
        countryList.add(new CountryModel("China",0,0,"Asia",null,5,"Bill"));
        countryList.add(new CountryModel("China",0,0,"Asia",null,5,"Bill"));
        file = new File(Constant.filePath.toUri());
        readFile.setFile(file);
        gameMapModel = new GameMapModel(file);
        gamePlayModel = new GamePlayModel();
        gamePlayModel.setGameMap(gameMapModel);
        setUpIsDone = true;
    }

    /**
     * Sort Countries
     */
    @Test
    @Disabled("Avoid slow graphical tests")
    public void testSortCountry()
    {
        cardList= gamePlayModel.sortCountry(countryList);
        Assert.assertEquals(cardList.get(0).getCountryName(),"India");

    }

}