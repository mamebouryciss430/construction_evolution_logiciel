package com.risk.utilities;

import com.risk.model.GameMapModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;

public class UnlinkedContinentValidationTest {
    GameMapModel gameMapModel;
    Validation val;
    ReadFile readFile;
    File file;


    /**
     * Set up variables
     */
    @BeforeEach
    public void setUp()
    {
        // do the setup
        readFile = new ReadFile();
        file = new File(Constant.filePath.toUri());
        readFile.setFile(file);
        val = new Validation();
        gameMapModel = new GameMapModel(file);
    }
    /**
     * Test unlink continent validation
     */
    @Test
    @Disabled("Avoid slow graphical tests")
    public void testUnlinkedContinentVAlidation()
    {
        assertFalse(val.unlinkedContinentValidation(gameMapModel));
    }


}
