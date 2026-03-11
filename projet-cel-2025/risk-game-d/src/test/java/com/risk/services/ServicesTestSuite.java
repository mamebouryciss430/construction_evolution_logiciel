package com.risk.services;

import com.risk.services.gameplay.RoundRobinTest;
import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({ConnectedGraphTest.class,MapValidateTest.class,MapGraphTest.class,MapEditorTest.class,
                RoundRobinTest.class,StartUpPhaseTest.class})

/**
 * TestSuite Class for all services test classes
 * 
 * @author Palash Jain
 * @author Farhan Shaheen
 *
 */
@ExcludeTags("GUI")
public class ServicesTestSuite {
}
