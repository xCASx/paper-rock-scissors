package ru.itskills.prs.game;

import ru.itskills.prs.game.player.Player;
import ru.itskills.prs.game.player.UnknownPlayer;
import ru.itskills.prs.ui.UserInterface;

/**
 * Represents a single game round
 */
class GameRound {
    private final Player playerA;
    private final Player playerB;
    private final EvaluationStrategy evaluationStrategy;
    private final UserInterface ui;

    GameRound(Player playerA, Player playerB, EvaluationStrategy evaluationStrategy, UserInterface ui) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.evaluationStrategy = evaluationStrategy;
        this.ui = ui;
    }

    /**
     * Plays a single game round and defines the winner
     *
     * @param roundNumber current round number
     * @return the winner player or an {@link UnknownPlayer} in case of a draw round
     */
    Player play(final int roundNumber) {
        ui.sendMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        ui.sendMessage("Round " + roundNumber);
        final var shapeA = playerA.makeMove();
        final var shapeB = playerB.makeMove();

        try {
            return defineWinner(roundNumber, shapeA, shapeB);
        } catch (IllegalArgumentException e) {
            // notify about an error and skip the bad round to not brake the whole game
            ui.sendMessage("Error during winner evaluation, the results of this round will be skipped:\n\t" + e.getMessage());
            return UnknownPlayer.INSTANCE;
        }
    }

    private Player defineWinner(int roundNumber, Shape shapeA, Shape shapeB) {
        final var winner = evaluationStrategy.getWinner(playerA, shapeA, playerB, shapeB);
        if (winner.isUnknown()) {
            ui.sendMessage("The round ended in a draw");
        } else {
            ui.sendMessage(String.format("%s chose %s, %s chose %s", playerA.getName(), shapeA, playerB.getName(), shapeB));
            ui.sendMessage(String.format("The %d round's winner is %s", roundNumber, winner.getName()));
        }
        return winner;
    }
}
