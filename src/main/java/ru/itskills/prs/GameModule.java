package ru.itskills.prs;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import ru.itskills.prs.game.Player;
import ru.itskills.prs.game.choice.ChoiceStrategy;
import ru.itskills.prs.game.choice.CliChoiceStrategy;
import ru.itskills.prs.game.choice.RandomChoiceStrategy;
import ru.itskills.prs.ui.UserInterface;

import java.util.Random;

/**
 * Configuration for Guice DI
 */
public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        long seed = System.currentTimeMillis();
        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("RandomChoice"))
                .toInstance(new RandomChoiceStrategy(new Random(seed)));

        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("CliChoice"))
                .to(CliChoiceStrategy.class);

        bind(UserInterface.class)
                .toInstance(new UserInterface(System.in, System.out));
    }

    @Provides
    @Singleton
    @Named("AI")
    public Player newArtificialPlayer(@Named("RandomChoice") ChoiceStrategy strategy) {
        return new Player("r2d2", strategy);
    }

    @Provides
    @Singleton
    @Named("Human")
    public Player newHumanPlayer(UserInterface ui, @Named("CliChoice") ChoiceStrategy strategy) {
        ui.sendMessage("Please input your name to start the game: ");
        var userName = ui.getUserName();
        return new Player(userName, strategy);
    }
}
