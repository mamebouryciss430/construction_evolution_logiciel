package com.risk.strategy;

/**
 * The test suite of strategy
 **/

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BenevolentStrategyTest.class,
        CheaterStrategyTest.class,
        AggressiveStrategyTest.class,
        RandomStrategyTest.class,
})
public class StrategyTestSuite {

}
