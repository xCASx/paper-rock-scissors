package ru.itskills.prs.game.choice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.itskills.prs.game.Shape;
import ru.itskills.prs.ui.UserInterface;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static ru.itskills.prs.game.Shape.ROCK;

public class CliChoiceStrategyTest {

    @DataProvider(name = "expectedInputDataProvider")
    Object[][] expectedInputDataProvider() {
        var shapes = Shape.values();
        var data = new Object[shapes.length][];
        for (int i = 0; i < shapes.length; i++) {
            data[i] = new Object[] {shapes[i], shapes[i].toString()};
        }
        return data;
    }

    @Test(dataProvider = "expectedInputDataProvider")
    public void shouldCheckAllExpectedInputs(Shape expectedShape, String input) {
        var fixture = new Fixture().givenUserChoice(input);

        var shape = fixture.strategy.chooseAShape();

        assertEquals(shape, expectedShape, "Shape for cli choice strategy should be correct");
    }

    @Test
    public void shouldReceiveCorrectShapeEventually() {
        var fixture = new Fixture().givenUserChoices();

        var shape = fixture.strategy.chooseAShape();

        assertEquals(shape, ROCK, "Correct input should be received eventually");
    }

    private static final class Fixture {
        UserInterface uiMock = mock(UserInterface.class);

        CliChoiceStrategy strategy;

        Fixture() {
            strategy = new CliChoiceStrategy(uiMock);
        }

        Fixture givenUserChoice(String input) {
            doReturn(input).when(uiMock).getUserChoice();
            return this;
        }

        Fixture givenUserChoices() {
            when(uiMock.getUserChoice()).thenReturn("", "abc", "123", ROCK.toString());
            return this;
        }
    }
}