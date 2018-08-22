package ru.itskills.prs.game.player;

import ru.itskills.prs.game.Shape;
import ru.itskills.prs.game.choice.ChoiceStrategy;

/**
 * Represents a game player
 */
public class GamePlayer implements Player {

    private final String name;
    private final ChoiceStrategy choiceStrategy;

    public GamePlayer(String name, ChoiceStrategy choiceStrategy) {
        this.name = name;
        this.choiceStrategy = choiceStrategy;
    }

    @Override
    public Shape makeMove() {
        return choiceStrategy.chooseAShape();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }
}
