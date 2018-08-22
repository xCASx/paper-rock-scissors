package ru.itskills.prs.game;

import org.testng.annotations.Test;
import ru.itskills.prs.game.choice.ChoiceStrategy;
import ru.itskills.prs.game.player.GamePlayer;
import ru.itskills.prs.game.player.Player;
import ru.itskills.prs.ui.UserInterface;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static ru.itskills.prs.game.Shape.ROCK;
import static ru.itskills.prs.game.Shape.SCISSORS;

public class GameTest {

    @Test
    public void shouldDoCorrectNumberOfRoundsWithCorrectScores() {
        int numberOfRounds = 5;
        var fixture = new Fixture().givenRounds(numberOfRounds);

        fixture.game.start();

        int playerAScore = fixture.game.getScores().getScore(fixture.playerA);
        int playerBScore = fixture.game.getScores().getScore(fixture.playerB);
        assertEquals(playerAScore, 1);
        assertEquals(playerBScore, numberOfRounds - 1);
        verify(fixture.strategy, times(numberOfRounds)).getWinner(
                any(Player.class), any(Shape.class),
                any(Player.class), any(Shape.class)
        );
    }

    private static final class Fixture {
        Player playerA = givenPlayer("playerA", ROCK, SCISSORS);
        Player playerB = givenPlayer("playerB", SCISSORS, ROCK);

        UserInterface ui = mock(UserInterface.class);
        EvaluationStrategy strategy = spy(new EvaluationStrategy());

        Game game = spy(new Game(strategy, ui, playerA, playerB));

        Fixture givenRounds(int n) {
            doNothing().when(ui).sendMessage(anyString());
            doReturn(n).when(ui).getNumberOfRounds();
            return this;
        }

        Player givenPlayer(String name, Shape... shapes) {
            var strategy = mock(ChoiceStrategy.class);
            doReturn(shapes[0], shapes[1]).when(strategy).chooseAShape();
            return new GamePlayer(name, strategy);
        }
    }
}