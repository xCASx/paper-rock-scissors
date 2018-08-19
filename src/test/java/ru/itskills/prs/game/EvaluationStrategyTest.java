package ru.itskills.prs.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.itskills.prs.game.Shape.PAPER;
import static ru.itskills.prs.game.Shape.ROCK;
import static ru.itskills.prs.game.Shape.SCISSORS;

public class EvaluationStrategyTest {

    @DataProvider(name = "winnerDataProvider")
    Object[][] winnerDataProvider() {
        return new Object[][] {
                {"firstPlayer",     ROCK,       SCISSORS},
                {"firstPlayer",     SCISSORS,   PAPER},
                {"firstPlayer",     PAPER,      ROCK},
                {"secondPlayer",    SCISSORS,   ROCK},
                {"secondPlayer",    PAPER,      SCISSORS},
                {"secondPlayer",    ROCK,       PAPER}
        };
    }

    @DataProvider(name = "drawDataProvider")
    Object[][] drawDataProvider() {
        var shapes = Shape.values();
        var data = new Object[shapes.length][];
        for (int i = 0; i < shapes.length; i++) {
            data[i] = new Object[] {shapes[i], shapes[i]};
        }
        return data;
    }

    @Test(dataProvider = "winnerDataProvider")
    public void shouldProperlyDefineWinner(String expectedWinnerName, Shape shapeA, Shape shapeB) {
        var fixture = new Fixture();

        var winner = fixture.getWinner(shapeA, shapeB);

        assertTrue(winner.isPresent(), "Winner should be present in non-draw game");
        assertEquals(winner.get().getName(), expectedWinnerName, "Wrong winner");
    }

    @Test(dataProvider = "drawDataProvider")
    public void shouldReturnEmptyOptionalForDrawCase(Shape shapeA, Shape shapeB) {
        var fixture = new Fixture();

        var winner = fixture.getWinner(shapeA, shapeB);

        assertFalse(winner.isPresent(), "There should not be winner in draw case");
    }

    private static final class Fixture {
        EvaluationStrategy strategy = new EvaluationStrategy();

        Player givenPlayer(String name) {
            var mockPlayer = mock(Player.class);
            doReturn(name).when(mockPlayer).getName();
            return mockPlayer;
        }

        Optional<Player> getWinner(Shape shapeA, Shape shapeB) {
            var firstPlayer = givenPlayer("firstPlayer");
            var secondPlayer = givenPlayer("secondPlayer");
            return strategy.getWinner(firstPlayer, shapeA, secondPlayer, shapeB);
        }
    }
}