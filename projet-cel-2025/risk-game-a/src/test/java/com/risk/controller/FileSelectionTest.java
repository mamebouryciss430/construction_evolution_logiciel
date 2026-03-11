package com.risk.controller;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import com.risk.utilities.Validation;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import  org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

@Tag("GUI")
public class FileSelectionTest {
    GameMapModel gameMapModel;
    GamePlayModel gamePlayModel;
    Validation val;
    ReadFile readFile;
    File file;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();
    String name= "file";
    EditContinentController editContinentController;

    /**
     * Set up file
     */
    @BeforeEach
    public void setUp() throws Exception {
        readFile = new ReadFile();
        file = new File(String.valueOf(Constant.filePath));

        readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        gamePlayModel = new GamePlayModel();
        gamePlayModel.setGameMap(gameMapModel);
        editContinentController =  new EditContinentController();
    }

    /**
     * Test for checking file opening validation
     */
    @Test
    @Disabled("FIXME: Unit Tests should not be blocking")
    @Tag("GUI")
    @Ignore("Slow graphical test")
    public void testFileSelection()

    {
        if (editContinentController.selectFile() != null)
        {
            // FIXME: This tests never fails
            assertTrue(true);
        }
    }

}
