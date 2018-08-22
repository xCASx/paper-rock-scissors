package ru.itskills.prs.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.itskills.prs.game.Shape.PAPER;
import static ru.itskills.prs.game.Shape.ROCK;
import static ru.itskills.prs.game.Shape.Result.DRAW;
import static ru.itskills.prs.game.Shape.Result.LOSES;
import static ru.itskills.prs.game.Shape.Result.WINS;
import static ru.itskills.prs.game.Shape.SCISSORS;

public class ShapeTest {

    @DataProvider(name = "rulesProvider")
    Object[][] rulesProvider() {
        return new Object[][] {
                {PAPER,     PAPER,      DRAW},
                {ROCK,      ROCK,       DRAW},
                {SCISSORS,  SCISSORS,   DRAW},
                {PAPER,     SCISSORS,   LOSES},
                {PAPER,     ROCK,       WINS},
                {ROCK,      SCISSORS,   WINS},
        };
    }

    @Test(dataProvider = "rulesProvider")
    public void shouldCheckCorrectnessOfRules(Shape shapeA, Shape shapeB, Shape.Result result) {
        assertEquals(shapeA.beats(shapeB), result, "Broken rule");
    }
}