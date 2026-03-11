
package com.risk.utilities;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Tag("GUI")
public class LoadGameTest {

    private static boolean setUpIsDone = false;
    GameMapModel gameMapModel;
    GamePlayModel gamePlayModel;
    Validation val;
    ReadFile readFile;
    File file;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();
    String name = "file";

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
        file = new File(Constant.filePath.toUri());

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
    public void test() {
        SaveGame sg = new SaveGame();
        try {
            gamePlayModel = sg.readFROMJSONFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}

