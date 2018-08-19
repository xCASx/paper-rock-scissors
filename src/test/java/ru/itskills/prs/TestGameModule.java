package ru.itskills.prs;

import com.google.inject.name.Names;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;
import ru.itskills.prs.game.choice.ChoiceStrategy;
import ru.itskills.prs.game.choice.CliChoiceStrategy;
import ru.itskills.prs.game.choice.RandomChoiceStrategy;
import ru.itskills.prs.ui.UserInterface;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.fail;

public class TestGameModule extends GameModule {

    private static final long RANDOM_SEED = 0L;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final Stubber PLAYER_INPUT_STUBBER =
            doReturn(
                    letterByte("p"),
                    letterByte("l"),
                    letterByte("a"),
                    letterByte("y"),
                    letterByte("e"),
                    letterByte("r"),
                    letterByte("\r"),
                    letterByte("\n"),
                    -1)
            .doReturn(
                    letterByte("3"),
                    letterByte("\r"),
                    letterByte("\n"),
                    -1)
            .doReturn(
                    letterByte("r"),
                    letterByte("o"),
                    letterByte("c"),
                    letterByte("k"),
                    letterByte("\r"),
                    letterByte("\n"),
                    -1)
            .doReturn(
                    letterByte("p"),
                    letterByte("a"),
                    letterByte("p"),
                    letterByte("e"),
                    letterByte("r"),
                    letterByte("\r"),
                    letterByte("\n"),
                    -1)
            .doReturn(
                    letterByte("p"),
                    letterByte("a"),
                    letterByte("p"),
                    letterByte("e"),
                    letterByte("r"),
                    letterByte("\r"),
                    letterByte("\n"),
                    -1);

    private final InputStream is = mock(InputStream.class);

    private static int letterByte(String p) {
        return (int) p.getBytes(UTF8)[0];
    }

    @Override
    protected void configure() {
        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("RandomChoice"))
                .toInstance(new RandomChoiceStrategy(new Random(RANDOM_SEED)));

        bind(ChoiceStrategy.class)
                .annotatedWith(Names.named("CliChoice"))
                .to(CliChoiceStrategy.class);

        try {
            PLAYER_INPUT_STUBBER.when(is).read();

            var answer = new Answer<>() {
                @Override
                public Integer answer(InvocationOnMock invocation) throws Throwable {
                    byte[] b = invocation.getArgument(0);
                    int off = invocation.getArgument(1);
                    int len = invocation.getArgument(2);
                    return read(b, off, len);
                }
            };
            doAnswer(answer).when(is).read(any(byte[].class), anyInt(), anyInt());
        } catch (IOException e) {
            fail("Exception during input string manipulation", e);
        }

        bind(UserInterface.class)
                .toInstance(new UserInterface(is, System.out));
    }

    /**
     * A copy of {@link InputStream#read(byte[], int, int)} method
     */
    private int read(byte[] b, int off, int len) throws IOException {
        Objects.requireNonNull(b);
        Objects.checkFromIndexSize(off, len, b.length);
        if (len == 0) {
            return 0;
        }

        int c = is.read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = is.read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ee) {
        }
        return i;
    }
}
