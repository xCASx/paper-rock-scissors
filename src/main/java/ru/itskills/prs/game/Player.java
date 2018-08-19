package ru.itskills.prs.game;

import ru.itskills.prs.game.choice.ChoiceStrategy;

/**
 * Represents a game player
 */
public class Player  {

    private final String name;
    private final ChoiceStrategy choiceStrategy;

    public Player(String name, ChoiceStrategy choiceStrategy) {
        this.name = name;
        this.choiceStrategy = choiceStrategy;
    }

    /**
     * Forces the player to make a move
     *
     * @return chosen shape
     */
    public Shape makeMove() {
        return choiceStrategy.chooseAShape();
    }

    /**
     * Returns player's name
     *
     * @return player's name
     */
    public String getName() {
        return name;
    }
}
