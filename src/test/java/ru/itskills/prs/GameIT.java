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

        assertEquals(scores.getScore("player"), 2, "Wrong score for human player");
        assertEquals(scores.getScore("r2d2"), 1, "Wrong score for ai player");
    }
}