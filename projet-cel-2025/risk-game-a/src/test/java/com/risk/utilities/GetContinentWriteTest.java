package com.risk.utilities;

import com.risk.model.CountryModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GetContinentWriteTest {

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
		file = new File(Constant.filePath.toUri());
		readFile.setFile(file);
		WriteMap writeMap = new WriteMap();
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

	/**
	 * Test single strike
	 */
	@Test
    @Disabled("Avoid graphical tests")
	public void test() {
		String value = WriteMap.getCountry(countryList.get(0));
		if (value != null) {
			equals(false);
		}else {
			equals(true);
		}
		
	}
}
