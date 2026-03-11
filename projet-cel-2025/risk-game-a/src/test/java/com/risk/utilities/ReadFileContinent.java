package com.risk.utilities;

import com.risk.model.GameMapModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;

@Tag("GUI")
public class ReadFileContinent {
    private static final boolean False = false;
    GameMapModel gameMapModel;
    Validation val;
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
        this.readFile = new ReadFile();
        file = new File(Constant.filePath.toUri());
        this.readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        setUpIsDone = true;
    }

    /**
     * Test read File get continent
     */
    @Test
    public void testReadFileGetContinent() {
        Assert.assertEquals(true, readFile.validateReadContinent(gameMapModel.getContinents(), readFile.getMapContinentDetails()));
    }

}
