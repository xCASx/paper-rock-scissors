package ru.itskills.prs.game.choice;

import ru.itskills.prs.game.Shape;

/**
 * Pluggable choice strategy for choosing {@link Shape}
 */
public interface ChoiceStrategy {
    /**
     * @return a {@link Shape} chosen by inner algorithm
     */
    Shape chooseAShape();
}
