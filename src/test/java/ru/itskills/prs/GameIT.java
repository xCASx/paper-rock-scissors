package ru.itskills.prs;

import com.google.inject.Guice;
import org.testng.annotations.Test;
import ru.itskills.prs.game.Game;
import ru.itskills.prs.game.ScoreHolder;

import static org.testng.Assert.*;

public class GameIT {

    @Test
    public void shouldRunTheGameInPredictableManner() {
        var injector = Guice.createInjector(new TestGameModule());
        var game = injector.getInstance(Game.class);
        ScoreHolder scores = game.start();

        int playerScore = scores.getScore("player").orElse(0);
        int aiScore = scores.getScore("r2d2").orElse(0);
        assertEquals(playerScore, 2, "Wrong score for human player");
        assertEquals(aiScore, 1, "Wrong score for ai player");
    }
}