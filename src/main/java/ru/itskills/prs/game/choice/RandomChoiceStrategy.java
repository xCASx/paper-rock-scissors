package ru.itskills.prs.game.choice;

import ru.itskills.prs.game.Shape;
import ru.itskills.prs.ui.UserInterface;

import java.util.Random;

/**
 * The strategy utilizes provided {@link Random} to get pseudorandom choice of {@link Shape}
 */
public class RandomChoiceStrategy implements ChoiceStrategy {

    private final Random random;

    public RandomChoiceStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Shape chooseAShape() {
        int choice = random.nextInt(Shape.values().length);
        return Shape.values()[choice];
    }
}
