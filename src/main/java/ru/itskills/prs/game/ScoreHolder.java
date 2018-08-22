package ru.itskills.prs.game;

import ru.itskills.prs.game.player.Player;
import ru.itskills.prs.util.NumberUtil;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;
import static ru.itskills.prs.util.NumberUtil.UNKNOWN;

/**
 * Encapsulates scoring results
 */
public class ScoreHolder {
    private final Map<Player, AtomicInteger> scores;

    public ScoreHolder(Player... players) {
        this.scores = stream(players)
                .collect(collectingAndThen(toMap(identity(), v -> new AtomicInteger()), Collections::unmodifiableMap));
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
     * @return player's score or {@link NumberUtil#UNKNOWN} if player was not found
     */
    public int getScore(Player player) {
        AtomicInteger score = scores.get(player);
        return score == null ? UNKNOWN : score.get();
    }

    /**
     * Returns the score of particular player
     *
     * @param playerName whose score need to be returned
     * @return player's score or {@link NumberUtil#UNKNOWN} if player was not found
     */
    public int getScore(String playerName) {
        return scores.keySet().stream()
                .filter(p -> p.getName().equals(playerName))
                .findAny()
                .map(p -> scores.get(p).get())
                .orElse(UNKNOWN);
    }
}
