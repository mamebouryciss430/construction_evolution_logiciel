package com.risk;

import com.risk.controller.*;
import com.risk.model.*;
import com.risk.utilities.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
				UnlinkedContinentValidationTest.class,
				CheckInterlinkedContinentTest.class,
				EmptyContinentValidationTest.class,
				ReadFileContinent.class,
				ReadFileCountry.class,
				EmptyLinkCountryValidationTest.class,
				AddCardTest.class,
				AddCountryToAttackerTest.class,
				AllOutTest.class,
				CheckSingleStrike.class,
				ContinentCoverageTest.class,
				GetArmiesTest.class,
				MoveDeckTest.class,
				MovingArmies.class,
				CheckValidMove.class,
				ReinforcementArmyNumberTest.class,
				RemoveCardTest.class,
				RemoveCountryToDefeaterTest.class,
				WorldCoverageTest.class,
				SetNeighbouringCountriesTest.class,
				EndOfGameTest.class,
				GetContinentWriteTest.class,
				GetCountryWriteTest.class,
				GetCardForPlayerTest.class,
				GetCards.class,
				GetPlayerForCountryTest.class,
				StartupTest.class,
				WinnerCheckTest.class,
				SaveGameTest.class,
				LoadGameTest.class,
				FileSelectionTest.class,
				SameCountryValidationTest.class,
				ContinentCoveredTest.class,
				DescCountryTest.class,
				SortCountryTest.class,
				WeakestCountryTest.class,
				NonContinentValidationTest.class,
				TournmentMapVerificationTest.class,
				CheckForOverallArmiesZeroTest.class,
				StartupControllerGetRandomTest.class,
				PlayerModelDefendTest.class
				})

/**
 * Test suite 
 */
public class TestSuite {
	
}
