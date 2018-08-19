package ru.itskills.prs.game;

import org.testng.annotations.Test;
import ru.itskills.prs.ui.UserInterface;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static ru.itskills.prs.game.Shape.ROCK;

public class GameRoundTest {

    @Test
    public void shouldSkipBadRound() {
        var fixture = new Fixture();

        var play = fixture.gameRound.play(1);

        assertFalse(play.isPresent());
    }

    private static final class Fixture {
        GameRound gameRound;

        Fixture() {
            var mockPlayer = mock(Player.class);
            doReturn(ROCK).when(mockPlayer).makeMove();

            var mockStrategy = mock(EvaluationStrategy.class);
            doThrow(UnsupportedOperationException.class).when(mockStrategy)
                    .getWinner(any(Player.class), any(Shape.class), any(Player.class), any(Shape.class));

            var mockUi = mock(UserInterface.class);

            gameRound = new GameRound(mockPlayer, mockPlayer, mockStrategy, mockUi);
        }
    }
}