package com.risk.controller;

import com.risk.model.GameMapModel;
import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import com.risk.utilities.Validation;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author gursimransingh
 */
@Tag("GUI")
public class StartupControllerGetRandomTest {

    private static final boolean False = false;
    GameMapModel gameMapModel;
    Validation val;
    ReadFile readFile;
    File file;
    TournmentDetailController tournmentDetailController;
    int playerNumber;

    private static boolean setUpIsDone = false;

    /**
     * Set up variables
     */
    @BeforeEach
    public void setUp() {
        if (setUpIsDone) {
            return;
        }
        this.readFile = new ReadFile();
        file = new File(Constant.filePath.toUri());
        this.readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        setUpIsDone = true;
        StartUpTournamentController startUpTournamentController = new StartUpTournamentController();
        int noOfPlayers=5;
         playerNumber = startUpTournamentController.getRandomBetweenRange(1, noOfPlayers);

        tournmentDetailController = new TournmentDetailController();
    }

    /**
     * Test read File get continent
     */
    @Disabled("FIXME: No graphical tests")
    @Test
    public void testReadFileGetContinent() {
        Assertions.assertNotNull(playerNumber);
    }



}