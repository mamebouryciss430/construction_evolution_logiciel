package com.risk.strategy;

import com.risk.common.Action;
import com.risk.common.SleepTime;
import com.risk.model.Country;
import com.risk.model.Phase;
import com.risk.model.Player;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * Benevolent Strategy class
 */
public class BenevolentStrategy extends AbstractStrategy implements PlayerBehaviorStrategy, Serializable {

    private String name;

    /**
     * Constructor
     * @param player the corresponding player
     */
    public BenevolentStrategy(Player player) {
        super(player);
        name = "benevolent";
    }


    /**
     * Get name
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Orderly execute reinforcement(), attack() and fortification method
     */
    @Override
    public void execute() throws InterruptedException {

        //reinforcement
        reinforcement();

        //attack
        updatePhase("Attack Phase");
        attack(null, "0", null, "0", true);
        if (Phase.getInstance().getActionResult() == Action.WIN) {
            return;
        }

        //fortification
        updatePhase("Fortification Phase");
        fortification(null, null, 0);
    }


    /**
     * Reinforcement method
     * reinforces its weakest countries
     */
    @Override
    public void reinforcement() throws InterruptedException {

        while (player.getTotalCards() >= 5) {
            player.autoTradeCard();
        }

        // computer the armies that need to added roundly
        updatePhase("Reinforcement Phase");
        player.addRoundArmies();
        Phase.getInstance().update();

        // find the weakest country
        Country weakest = player.getCountriesOwned().stream()
                .min(Comparator.comparingLong(Country::getArmies))
                .get();

        // add all the armies to weakest
        weakest.addArmies(player.getArmies());
        player.setArmies(0);

        // update phase
        showNextPhaseButton();

        // FIXME: Why?
        sleep(SleepTime.getSleepTime());

    }

    /**
     * Attack method
     * never attacks
     */
    @Override
    public void attack(Country attacker, String attackerNum, Country defender, String defenderNum, boolean isAllOut) throws InterruptedException {


        showNextPhaseButton();

        // FIXME: Remove sleep() and check if the code works
        sleep(SleepTime.getSleepTime());

    }

    /**
     * moveArmy method
     * no attacks, no victory, no move army
     */
    @Override
    public void moveArmy(String num) {

    }

    /**
     * Fortification method
     * move armies to weaker countries
     * @param source null
     * @param target null
     * @param armyNumber 0
     */
    @Override
    public void fortification(Country source, Country target, int armyNumber) throws InterruptedException {

        List<Country> increaseSorted = player.getCountriesOwned().stream()
                .sorted(Comparator.comparingLong(Country::getArmies))
                .collect(Collectors.toList());

        List<Country> decreaseSorted = player.getCountriesOwned().stream()
                .sorted((c1, c2) -> {
                    if (c2.getArmies() - c1.getArmies() > 0 ) return 1;
                    else if (c2.getArmies() - c1.getArmies() == 0) return 0;
                    else return -1; })
                .collect(Collectors.toList());


        for (Country weaker : increaseSorted) {
            for (Country stronger : decreaseSorted) {
                if (player.isConnected(weaker, stronger)) {
                    // re-allocated armies
                    long total = stronger.getArmies() + weaker.getArmies();
                    stronger.setArmies(total/2);
                    weaker.setArmies(total - total/2);

                    showNextPhaseButton();

                    return;
                }
            }
        }


        // FIXME: remove sleep() and check the code works
        sleep(SleepTime.getSleepTime());
    }
}

