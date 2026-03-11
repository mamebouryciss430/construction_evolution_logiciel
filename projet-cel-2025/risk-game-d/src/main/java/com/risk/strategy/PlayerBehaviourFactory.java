package com.risk.strategy;

import com.risk.controller.GamePlayController;
import com.risk.model.IPlayerType;

public final class PlayerBehaviourFactory {

    private PlayerBehaviourFactory() {}

    public static PlayerBehaviour create(String type, GamePlayController controller) {
        switch (type) {
            case IPlayerType.AGGRESSIVE: return new Aggressive(controller);
            case IPlayerType.BENEVOLENT: return new Benevolent(controller);
            case IPlayerType.CHEATER:    return new Cheater(controller);
            case IPlayerType.HUMAN:      return new Human(controller);
            case IPlayerType.RANDOM:     return new Random(controller);
            default: throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }

    public static PlayerBehaviour create(String type) {
        switch (type) {
            case IPlayerType.AGGRESSIVE: return new Aggressive();
            case IPlayerType.BENEVOLENT: return new Benevolent();
            case IPlayerType.CHEATER:    return new Cheater();
            case IPlayerType.RANDOM:     return new Random();
            default: throw new IllegalArgumentException("Unknown player type: " + type);
        }
    }
}
