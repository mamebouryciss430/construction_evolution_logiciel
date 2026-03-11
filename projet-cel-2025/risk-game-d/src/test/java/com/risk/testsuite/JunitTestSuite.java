package com.risk.testsuite;

import com.risk.model.CardTest;
import com.risk.model.DiceTest;
import com.risk.model.PlayerTest;
import com.risk.model.TournamentModelTest;
import com.risk.services.*;
import com.risk.services.gameplay.RoundRobinTest;
import com.risk.services.saveload.ResourceManagerTest;
import com.risk.strategy.BenevolentTest;
import com.risk.strategy.HumanTest;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({MapEditorTest.class, MapGraphTest.class, MapValidateTest.class, ResourceManagerTest.class,
        RoundRobinTest.class, BenevolentTest.class, HumanTest.class, TournamentModelTest.class, ConnectedGraphTest.class, CardTest.class, DiceTest.class, PlayerTest.class, StartUpPhaseTest.class})
@ExcludeTags("GUI")
/**
 * TestSuite Class to test all test cases
 *
 * @author Ruthvik Shandilya
 *
 */
public class JunitTestSuite {
}
