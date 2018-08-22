package ru.itskills.prs.game.choice;

import ru.itskills.prs.game.Shape;
import ru.itskills.prs.ui.UserInterface;

import javax.inject.Inject;

import static ru.itskills.prs.game.Shape.SHAPES;

/**
 * The strategy utilizes CLI via {@link UserInterface} to get user's choice of {@link Shape}
 */
public class CliChoiceStrategy implements ChoiceStrategy {

    private final UserInterface ui;

    @Inject
    public CliChoiceStrategy(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public Shape chooseAShape() {
        ui.sendMessage(String.format("Please make your choice %s:", SHAPES));
        var shape = Shape.of(ui.getUserChoice());
        while (shape.isUnknown()) {
            ui.sendMessage(String.format("Please chose a correct shape %s:", SHAPES));
            shape = Shape.of(ui.getUserChoice());
        }
        return shape;
    }
}
