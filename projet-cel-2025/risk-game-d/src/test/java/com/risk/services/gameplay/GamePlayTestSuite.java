package com.risk.services.gameplay;

import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({RoundRobinTest.class})
@ExcludeTags("GUI")
/**
 * TestSuite Class for all GamePlay test classes
 * 
 * @author Neha Pal
 * @author Palash Jain
 *
 */
public class GamePlayTestSuite {

}
