package com.risk.utilities;

import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.controller.StartupController;
import com.risk.model.CountryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

public class StartupTest {
    GameMapModel gameMapModel;
    GamePlayModel gamePlayModel;
    Validation val;
    ReadFile readFile;
    File file;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();

    private static boolean setUpIsDone = false;

    /**
     * Set up file
     */
    @BeforeEach
    public void setUp() throws Exception {
        if (setUpIsDone) {
            return;
        }
        // do the setup
        readFile = new ReadFile();
        file = new File(String.valueOf(Constant.filePath));
        readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        gamePlayModel = new GamePlayModel();
        gamePlayModel.setGameMap(gameMapModel);

        countryList.add(gameMapModel.getCountries().get(0));
        countryList.add(gameMapModel.getCountries().get(1));

        countryList.get(0).setArmies(2);

        PlayerModel pm = new PlayerModel("X", "Human", 0, Color.WHITE, 0, countryList, null);
        ArrayList<PlayerModel> pmList = new ArrayList<PlayerModel>();

        pmList.add(pm);

        gamePlayModel.setPlayers(pmList);
        setUpIsDone = true;
    }

    @Test
    @Disabled("Avoid slow graphical tests")
    public void teststartup()
    {
        StartupController statup = new StartupController(gamePlayModel);
        assertFalse(statup.checkForOverallArmies());
    }

}
