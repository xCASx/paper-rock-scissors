package ru.itskills.prs;

import com.google.inject.name.Names;
import ru.itskills.prs.game.choice.ChoiceStrategy;
import ru.itskills.prs.game.choice.CliChoiceStrategy;
import ru.itskills.prs.game.choice.RandomChoiceStrategy;
import ru.itskills.prs.ui.UserInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import static org.testng.Assert.fail;

public class TestGameModule extends GameModule {

    private static final long RANDOM_SEED = 0L;

    @Override
    protected void configure() {
        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("RandomChoice"))
                .toInstance(new RandomChoiceStrategy(new Random(RANDOM_SEED)));

        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("CliChoice"))
                .to(CliChoiceStrategy.class);

        try {
            var fis = new FileInputStream("src/test/resources/user_input.txt");
            bind(UserInterface.class)
                    .toInstance(new UserInterface(fis, System.out));
        } catch (FileNotFoundException e) {
            fail("User input file not found");
        }
    }
}
