package ru.itskills.prs.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itskills.prs.game.player.Player;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.itskills.prs.game.Shape.PAPER;
import static ru.itskills.prs.game.Shape.ROCK;
import static ru.itskills.prs.game.Shape.SCISSORS;
import static ru.itskills.prs.game.Shape.SHAPES;

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
        var data = new Object[SHAPES.size()][];
        for (int i = 0; i < SHAPES.size(); i++) {
            data[i] = new Object[] {SHAPES.get(i), SHAPES.get(i)};
        }
        return data;
    }

    @Test(dataProvider = "winnerDataProvider")
    public void shouldProperlyDefineWinner(String expectedWinnerName, Shape shapeA, Shape shapeB) {
        var fixture = new Fixture();

        var winner = fixture.getWinner(shapeA, shapeB);

        assertFalse(winner.isUnknown(), "Winner should be present in non-draw round");
        assertEquals(winner.getName(), expectedWinnerName, "Wrong winner");
    }

    @Test(dataProvider = "drawDataProvider")
    public void shouldReturnNoWinnerForDrawCase(Shape shapeA, Shape shapeB) {
        var fixture = new Fixture();

        var winner = fixture.getWinner(shapeA, shapeB);

        assertTrue(winner.isUnknown(), "There should not be a winner in a draw round");
    }

    private static final class Fixture {
        EvaluationStrategy strategy = new EvaluationStrategy();

        Player givenPlayer(String name) {
            var mockPlayer = mock(Player.class);
            doReturn(name).when(mockPlayer).getName();
            return mockPlayer;
        }

        Player getWinner(Shape shapeA, Shape shapeB) {
            var firstPlayer = givenPlayer("firstPlayer");
            var secondPlayer = givenPlayer("secondPlayer");
            return strategy.getWinner(firstPlayer, shapeA, secondPlayer, shapeB);
        }
    }
}