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

public class EndOfGameTest {

	GameMapModel gameMapModel;
	GamePlayModel gamePlayModel;
	Validation val;
	ReadFile readFile;
	File file;
	ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();


	/**
	 * Set up file
	 */
	@BeforeEach
	public void setUp() throws Exception {
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
	}

	/**
	 * Test single strike
	 */
	@Test
    @Disabled("Avoid graphical tests")
	public void test() {
		boolean endOfGameFlag = this.val.endOfGame(gamePlayModel);
		if(endOfGameFlag) {
			MessageUtil msg = new MessageUtil("It is an end of game");			
		}else {
			MessageUtil msg = new MessageUtil("It is not an end of game");
		}
	}
}
