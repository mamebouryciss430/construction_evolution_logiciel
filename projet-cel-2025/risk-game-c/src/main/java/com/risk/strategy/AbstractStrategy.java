package com.risk.strategy;

import com.risk.common.Action;
import com.risk.model.Phase;
import com.risk.model.Player;

import static java.lang.Thread.sleep;

public abstract class AbstractStrategy implements PlayerBehaviorStrategy {

    protected Player player;

    protected AbstractStrategy(Player player) {
        this.player = player;
    }

    protected void updatePhase(String phaseName) {
        Phase.getInstance().setCurrentPhase(phaseName);
        Phase.getInstance().update();
    }

    protected void showNextPhaseButton() throws InterruptedException {
        Phase.getInstance().setActionResult(Action.SHOW_NEXT_PHASE_BUTTON);
        Phase.getInstance().update();
    }
}

