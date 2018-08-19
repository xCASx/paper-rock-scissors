package ru.itskills.prs.game;

import java.util.Optional;

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
     * @return {@link Optional} wrapping the winner player or an empty {@link Optional} in case of draw
     * @throws UnsupportedOperationException in case of unexpected {@link Shape} or {@link Shape.Result}
     */
    Optional<Player> getWinner(final Player playerA, final Shape shapeA,
                               final Player playerB, final Shape shapeB) {
        final var result = shapeA.beats(shapeB);
        switch (result) {
            case DRAW:     return Optional.empty();
            case WINS:     return Optional.of(playerA);
            case LOSES:    return Optional.of(playerB);
            default:
                throw new UnsupportedOperationException("Unexpected result of evaluation function: " + result);
        }
    }
}
