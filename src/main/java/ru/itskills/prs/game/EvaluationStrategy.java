package ru.itskills.prs.game;

import ru.itskills.prs.game.player.Player;
import ru.itskills.prs.game.player.UnknownPlayer;

/**
 * Pluggable evaluation strategy.
 * Besides making game more extensible improves testability.
 */
class EvaluationStrategy {
    /**
     * Evaluates the winner based on chosen shapes
     *
     * @param playerA the first player
     * @param shapeA the shape chosen by the first player
     * @param playerB the second player
     * @param shapeB the shape chosen by the second player
     * @return the winner player or an {@link UnknownPlayer} in case of draw
     * @throws IllegalArgumentException in case of unexpected {@link Shape} or {@link Shape.Result}
     */
    Player getWinner(final Player playerA, final Shape shapeA, final Player playerB, final Shape shapeB) {
        final var result = shapeA.beats(shapeB);
        switch (result) {
            case DRAW:     return UnknownPlayer.INSTANCE;
            case WINS:     return playerA;
            case LOSES:    return playerB;
            default:
                throw new IllegalArgumentException(
                        String.format("Unexpected result [%s] of evaluation function for shapes [%s] and [%s]",
                                result, shapeA, shapeB));
        }
    }
}
