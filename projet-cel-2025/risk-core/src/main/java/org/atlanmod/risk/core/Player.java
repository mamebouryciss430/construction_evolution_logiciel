package org.atlanmod.risk.core;

import org.jetbrains.annotations.Contract;

/**
 * Represents a Risk Game Player
 */
public interface Player {

    /**
     * Accessor method for the name of the Player
     *
     * @return the name of the Player
     */
    @Contract(pure = true)
    String getName();
}
