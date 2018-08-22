package ru.itskills.prs.game.player;

import ru.itskills.prs.game.Shape;

public interface Player {
    /**
     * Returns player's name
     *
     * @return player's name
     */
    String getName();

    /**
     * Forces the player to make a move
     *
     * @return chosen shape
     */
    Shape makeMove();

    boolean isUnknown();
}
