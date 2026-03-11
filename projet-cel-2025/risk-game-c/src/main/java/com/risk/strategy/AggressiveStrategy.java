package com.risk.strategy;


import com.risk.common.Action;
import com.risk.model.Country;
import com.risk.model.Phase;
import com.risk.model.Player;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aggressive strategy class
 *
 */
public class AggressiveStrategy extends AbstractStrategy implements PlayerBehaviorStrategy, Serializable {

    private String name;

    /**
     * constructor
     * @param player player with this strategy
     */
    public AggressiveStrategy(Player player) {
        super(player);
        name = "aggressive";
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
     * reinforces its strongest country
     */
    @Override
    public void reinforcement() throws InterruptedException {
        // TODO: Replace prints with logging

        HashMap<String, Long> reinforceCountry = new HashMap<>();

        while (player.getTotalCards() >= 5) {
            player.autoTradeCard();
        }


        player.addRoundArmies();
        Phase.getInstance().update();


        // find the strongest country
        Country strongest = player.getCountriesOwned().stream()
                .max(Comparator.comparingLong(Country::getArmies))
                .get();

        // add all the armies to weakest
        strongest.addArmies(player.getArmies());
        reinforceCountry.put(strongest.getName(),player.getArmies());
        player.setArmies(0);

        // update phase
        showNextPhaseButton();

    }



    /**
     * Attack method
     * always attack with the strongest country until it cannot attack anymore
     * @param attacker null
     * @param attackerNum 0
     * @param defender null
     * @param defenderNum 0
     * @param isAllOut true
     */
    @Override
    public void attack(Country attacker, String attackerNum, Country defender, String defenderNum, boolean isAllOut) throws InterruptedException {

        // attacker is the strongest country
        Country strongest = player.getCountriesOwned().stream()
                .max(Comparator.comparingLong(Country::getArmies))
                .get();

        List<Country> enemies = strongest.getAdjCountries().stream()
                .filter(c -> !c.getOwner().equals(player))
                .collect(Collectors.toList());

        for (Country enemy : enemies) {
            if (strongest.getArmies() >= 2) {
                player.allOut(strongest, enemy);

                if (Phase.getInstance().getActionResult() == Action.WIN) {
                    Phase.getInstance().update();
                    player.addRandomCard();
                    return;
                }

                if (Phase.getInstance().getActionResult() == Action.MOVE_AFTER_CONQUER) {
                    moveArmy(String.valueOf(player.getAttackerDiceNum()));
                }
            }
        }

        player.addRandomCard();
        Phase.getInstance().update();

    }



    /**
     * Move army method
     * move the mininum armies that could
     * @param num number of army to move
     */
    @Override
    public void moveArmy(String num) {

        int numArmies = Integer.valueOf(num);
        Country attacker = player.getAttacker();
        Country defender = player.getDefender();

        attacker.setArmies(attacker.getArmies() - numArmies);
        defender.setArmies(defender.getArmies() + numArmies);

        Phase.getInstance().setActionResult(Action.SHOW_NEXT_PHASE_BUTTON);
        Phase.getInstance().setInvalidInfo("Army Movement Finish. You Can Start Another Attack Or Enter Next Phase Now");
    }


    /**
     * Fortification method
     * maximize aggregation of forces in one country
     * @param source from source
     * @param target to target
     * @param armyNumber move num of army
     */
    @Override
    public void fortification(Country source, Country target, int armyNumber) throws InterruptedException {

        List<Country> decreaseSorted = player.getCountriesOwned().stream()
                .sorted((c1, c2) -> {
                    if (c2.getArmies() - c1.getArmies() > 0 ) return 1;
                    else if (c2.getArmies() - c1.getArmies() == 0) return 0;
                    else return -1; })
                .collect(Collectors.toList());

        for (int i = 0; i < decreaseSorted.size(); i++) {
            for (int j = 1; j < decreaseSorted.size(); j++) {

                Country c1 = decreaseSorted.get(i);
                Country c2 = decreaseSorted.get(j);

                if (player.isConnected(c1, c2)){

                    // re-allocated armies
                    c1.setArmies(c1.getArmies() + c2.getArmies());
                    // TODO: Replace prints with logging
                    c2.setArmies(0);

                    showNextPhaseButton();
                    return;
                }
            }
        }
    }
}
