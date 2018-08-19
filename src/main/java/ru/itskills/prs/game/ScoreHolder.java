package ru.itskills.prs.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Encapsulates scoring results
 */
public class ScoreHolder {
    private final Map<Player, AtomicInteger> scores;

    public ScoreHolder(Player... players) {
        var baseMap = Arrays.stream(players)
                .collect(Collectors.toMap(Function.identity(), v -> new AtomicInteger()));
        this.scores = Collections.unmodifiableMap(baseMap);
    }

    /**
     * Increments player's score
     *
     * @param player whose score should be increased
     * @return boolean true if score for the player was increased, false if player wasn't found
     */
    public boolean incrementScore(Player player) {
        AtomicInteger score = scores.get(player);
        if (score == null) return false;

        score.incrementAndGet();
        return true;
    }

    /**
     * Returns the score of particular player
     *
     * @param player whose score need to be returned
     * @return optional player's score
     */
    public Optional<Integer> getScore(Player player) {
        AtomicInteger score = scores.get(player);
        return score == null ? Optional.empty() : Optional.of(score.get());
    }

    /**
     * Returns the score of particular player
     *
     * @param playerName whose score need to be returned
     * @return optional player's score
     */
    public Optional<Integer> getScore(String playerName) {
        return scores.keySet().stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny()
                .map(p -> scores.get(p).get());
    }
}
