package com.risk.model;

import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SetNeighbouringCountriesTest {
    List<ContinentsModel> continentList;
    List<CountryModel> countryList;
    GameMapModel gameMapModel;
    ReadFile readFile;
    File file;


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
    }

    /**
     * Test adding Neighbour validation
     */
    @Test
    @Disabled("Avoid slow graphical tests")
    public void testAddingNeighboringCountries() {
        assertEquals(true,gameMapModel.setNeighbouringCountry(this.countryList.get(0),this.countryList.get(1)));
    }


}
