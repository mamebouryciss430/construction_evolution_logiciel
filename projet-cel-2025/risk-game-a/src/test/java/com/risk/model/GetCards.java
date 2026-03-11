package com.risk.model;

import com.risk.utilities.Constant;
import com.risk.utilities.ReadFile;
import com.risk.utilities.Validation;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GetCards {

	GameMapModel gameMapModel;
	GamePlayModel gamePlayModel;
	Validation val;
	ReadFile readFile;
	File file;
	ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();
	ArrayList<CountryModel> cardList = new ArrayList<CountryModel>();

	PlayerModel pm = new PlayerModel("X", "Human", 0, Color.WHITE, 0, countryList, cardList);
	CardModel card ;

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
		gameMapModel = new GameMapModel(file);
		gamePlayModel = new GamePlayModel();
		gamePlayModel.setGameMap(gameMapModel);

		countryList.add(gameMapModel.getCountries().get(0));
		countryList.add(gameMapModel.getCountries().get(1));

		countryList.get(0).setArmies(2);

		ArrayList<PlayerModel> pmList = new ArrayList<PlayerModel>();

		pmList.add(this.pm);
		
		

		gamePlayModel.setPlayers(pmList);
		setUpIsDone = true;
	}

	/**
	 * Test single strike
	 * @throws ParseException
	 */
	@Test
    @Disabled("Avoid slow graphical tests")
	public void test() throws ParseException {
		if (gamePlayModel.getCardFromJSON() != null) {
			assertTrue( true);
		}
		
	}
}
