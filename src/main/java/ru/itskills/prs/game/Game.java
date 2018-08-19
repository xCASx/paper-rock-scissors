package ru.itskills.prs.game;

import ru.itskills.prs.ui.UserInterface;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The main class of the game.
 * Responsible for running the game rounds and results processing.
 */
public class Game {

    private final UserInterface ui;
    private final EvaluationStrategy evaluationStrategy;
    private final Player ai;
    private final Player human;
    private final ScoreHolder scores;

    @Inject
    public Game(EvaluationStrategy evaluationStrategy, UserInterface ui,
                @Named("AI") Player ai, @Named("Human") Player human) {
        this.evaluationStrategy = evaluationStrategy;
        this.ui = ui;
        this.ai = ai;
        this.human = human;
        scores = new ScoreHolder(ai, human);
    }

    /**
     * Entry point to the game.
     * Runs defined number of rounds and publishes final results.
     *
     * @return score holder with results of the game
     */
    public ScoreHolder start() {
        ui.sendMessage("A new Paper-Rock-Scissor game is started");
        final int numberOfRounds = getNumberOfRounds();

        for (int roundNumber = 1; roundNumber <= numberOfRounds; roundNumber++) {
            var gameRound = new GameRound(ai, human, evaluationStrategy, ui);
            var roundWinner = gameRound.play(roundNumber);
            roundWinner.ifPresent(scores::incrementScore);
        }

        publishResults(numberOfRounds);
        return scores;
    }

    ScoreHolder getScores() {
        return scores;
    }

    private Integer getNumberOfRounds() {
        ui.sendMessage("Please chose how many rounds you want to play: ");
        var numberOfRounds = ui.getNumberOfRounds();
        while (!numberOfRounds.isPresent()) {
            ui.sendMessage("Please input a valid integer value");
            numberOfRounds = ui.getNumberOfRounds();
        }
        return numberOfRounds.get();
    }

    private void publishResults(final int numberOfRounds) {
        ui.sendMessage("################################################");
        ui.sendMessage(String.format("### The game is over after %d rounds ", numberOfRounds));

        Optional<Integer> aiScoreOptional = scores.getScore(ai);
        Optional<Integer> humanScoreOptional = scores.getScore(human);
        if (!(aiScoreOptional.isPresent() && humanScoreOptional.isPresent())) {
            throw new RuntimeException("Scores for players are not found");
        }

        int aiScore = aiScoreOptional.get();
        int humanScore = humanScoreOptional.get();

        if (aiScore == humanScore) {
            ui.sendMessage(String.format("### with a draw score %d:%d", aiScore, humanScore));
        } else if (aiScore > humanScore) {
            ui.sendMessage(getWinnerResults(ai, aiScore, humanScore));
        } else {
            ui.sendMessage(getWinnerResults(human, humanScore, aiScore));
        }

        ui.sendMessage("################################################");
    }

    private String getWinnerResults(final Player winner, final int winnerScore, final int looserScore) {
        return String.format("### with the victory of %s with the score %d:%d",
                winner.getName(), winnerScore, looserScore);
    }
}
