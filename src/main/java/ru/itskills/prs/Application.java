package ru.itskills.prs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.itskills.prs.game.Game;

public class Application {
    public static void main(String[] args) {
        var injector = Guice.createInjector(new GameModule());
        var game = injector.getInstance(Game.class);
        game.start();
    }
}
