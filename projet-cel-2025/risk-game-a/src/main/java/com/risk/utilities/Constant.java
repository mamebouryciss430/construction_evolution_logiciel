package com.risk.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Constant file to load
 * the map fie
 *
 * @author KaranPannu
 *
 */

public class Constant {
    public static final Path filePath = Paths.get("mapfiles", "World.map");
    public static final Path fileCard = Paths.get("mapfiles", "ConstantCard.json");
}
