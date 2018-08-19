package ru.itskills.prs.game;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static ru.itskills.prs.game.Shape.Result.DRAW;
import static ru.itskills.prs.game.Shape.Result.LOSES;
import static ru.itskills.prs.game.Shape.Result.WINS;

/**
 * Represents one of three game's shapes: paper, rock or scissor and defines priorities of shapes
 */
public enum Shape {
    PAPER {
        @Override
        public Result beats(Shape shape) {
            switch (shape) {
                case PAPER:     return DRAW;
                case ROCK:      return WINS;
                case SCISSORS:  return LOSES;
                default:
                    throw new UnsupportedOperationException("Unexpected shape " + shape);
            }
        }
    },
    ROCK {
        @Override
        public Result beats(Shape shape) {
            switch (shape) {
                case PAPER:     return LOSES;
                case ROCK:      return DRAW;
                case SCISSORS:  return WINS;
                default:
                    throw new UnsupportedOperationException("Unexpected shape " + shape);
            }
        }
    },
    SCISSORS {
        @Override
        public Result beats(Shape shape) {
            switch (shape) {
                case PAPER:     return WINS;
                case ROCK:      return LOSES;
                case SCISSORS:  return DRAW;
                default:
                    throw new UnsupportedOperationException("Unexpected shape " + shape);
            }
        }
    };

    public static final List<Shape> SHAPES = List.of(values());

    /**
     * Compares priorities of two shapes
     *
     * @param shape the shape with which to compare
     * @return comparison result
     */
    abstract Result beats(Shape shape);

    /**
     * Looks through all available shapes to find the one with a given name
     *
     * @param name the name of the sought-for shape
     * @return an {@link Optional} result of search
     */
    public static Optional<Shape> find(String name) {
        return stream(values())
                .filter(shape -> shape.toString().equals(name))
                .findAny();
    }

    /**
     * Represents the possible states of a game round
     */
    enum Result {
        WINS,
        LOSES,
        DRAW
    }
}
