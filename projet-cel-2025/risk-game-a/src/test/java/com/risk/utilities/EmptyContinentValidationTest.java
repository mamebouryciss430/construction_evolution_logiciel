package com.risk.utilities;

import com.risk.model.GameMapModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

public class EmptyContinentValidationTest {
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
    public void setUp()
    {
        if (setUpIsDone) {
            return;
        }
        // do the setup
        readFile = new ReadFile();
        file = new File(Constant.filePath.toUri());
        readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
        setUpIsDone = true;
    }
    /**
     * Test empty continent validation
     */
    @Test
    @Disabled("Avoid slow graphical tests")
    public void testUnlinkedContinentVAlidation()
    {
        assertFalse(val.emptyContinentValidation(gameMapModel));
    }

}
