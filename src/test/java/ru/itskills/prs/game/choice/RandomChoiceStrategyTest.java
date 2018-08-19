package ru.itskills.prs.game.choice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itskills.prs.game.Shape;

import java.util.Random;

import static org.testng.Assert.assertEquals;
import static ru.itskills.prs.game.Shape.PAPER;
import static ru.itskills.prs.game.Shape.ROCK;
import static ru.itskills.prs.game.Shape.SCISSORS;

public class RandomChoiceStrategyTest {

    @DataProvider(name = "fixedSeedDataProvider")
    Object[][] fixedSeedDataProvider() {
        return new Object[][]{
                {PAPER,     1L},
                {ROCK,      2L},
                {SCISSORS,  3L}
        };
    }

    @Test(dataProvider = "fixedSeedDataProvider")
    public void shouldCheckValueForFixedSeed(Shape expectedShape, long seed) {
        var fixture = new RandomChoiceStrategy(new Random(seed));

        var shape = fixture.chooseAShape();

        assertEquals(shape, expectedShape,
                "Shape for random choice strategy with fixed seed should be deterministic");
    }
}