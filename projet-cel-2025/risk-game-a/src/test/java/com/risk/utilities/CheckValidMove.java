package com.risk.utilities;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.PlayerModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CheckValidMove {
    private static final boolean False = false;
    GameMapModel gameMapModel;
    Validation val;
    ReadFile readFile;
    File file;
    ArrayList<PlayerModel> playersList = new ArrayList<>();

    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();
    ArrayList<CountryModel> cardList = new ArrayList<CountryModel>();

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
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        PlayerModel pm = new PlayerModel("X", "Human", 0, Color.WHITE, 0, countryList, cardList);
        PlayerModel pm1 = new PlayerModel("Y", "Human", 0, Color.WHITE, 0, countryList, cardList);
        playersList.add(pm);
        playersList.add(pm1);
        gameMapModel.setListOfPlayers(playersList);
        gameMapModel.setPlayerTurn(gameMapModel.getListOfPlayers().get(gameMapModel.getPlayerIndex()));
        for (int i = 0; i < this.gameMapModel.getCountries().size(); i++) {
            gameMapModel.getCountries().get(i).setRulerName(pm.getName());
            gameMapModel.getCountries().get(i+1).setRulerName(pm1.getName());
            i++;
        }
        setUpIsDone = true;
    }

    /**
     * Test check valid move
     */
    @Test
    public void testUnlinkedContinentVAlidation() {
        Assert.assertFalse(val.checkIfValidMove(gameMapModel, gameMapModel.getCountries().get(0),gameMapModel.getCountries().get(1)));
    }
}
