package ru.itskills.prs.game.player;

import ru.itskills.prs.game.Shape;

import static ru.itskills.prs.game.Shape.UNKNOWN;

/**
 * Null object for Player
 */
public final class UnknownPlayer implements Player {

    public static final Player INSTANCE = new UnknownPlayer();

    private UnknownPlayer() {}

    @Override
    public String getName() {
        return "UNKNOWN";
    }

    @Override
    public Shape makeMove() {
        return UNKNOWN;
    }

    @Override
    public boolean isUnknown() {
        return true;
    }
}
