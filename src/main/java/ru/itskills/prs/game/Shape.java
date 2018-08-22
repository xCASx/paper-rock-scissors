package ru.itskills.prs.game;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
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
                default:        return Result.UNKNOWN;
            }
        }

        @Override
        public boolean isUnknown() {
            return false;
        }
    },
    ROCK {
        @Override
        public Result beats(Shape shape) {
            switch (shape) {
                case PAPER:     return LOSES;
                case ROCK:      return DRAW;
                case SCISSORS:  return WINS;
                default:        return Result.UNKNOWN;
            }
        }

        @Override
        public boolean isUnknown() {
            return false;
        }
    },
    SCISSORS {
        @Override
        public Result beats(Shape shape) {
            switch (shape) {
                case PAPER:     return WINS;
                case ROCK:      return LOSES;
                case SCISSORS:  return DRAW;
                default:        return Result.UNKNOWN;
            }
        }

        @Override
        public boolean isUnknown() {
            return false;
        }
    },
    /**
     * Null object for Shape
     */
    UNKNOWN {
        @Override
        Result beats(Shape shape) {
            return Result.UNKNOWN;
        }

        @Override
        public boolean isUnknown() {
            return true;
        }
    };

    /**
     * Contains all the shapes excluding null object
     */
    public static final List<Shape> SHAPES =
            stream(values())
                    .filter(shape -> !shape.isUnknown())
                    .collect(collectingAndThen(toList(), Collections::unmodifiableList));

    /**
     * Is the shape a null object
     * @return {@code true} for null object shape and {@code false} in an opposite case
     */
    public abstract boolean isUnknown();

    /**
     * Compares priorities of two shapes
     *
     * @param shape the shape with which to compare
     * @return comparison result
     * @throws IllegalArgumentException in case of unexpected {@link Shape}
     */
    abstract Result beats(Shape shape);

    /**
     * Looks through all available shapes to find the one with a given name
     *
     * @param name the name of the sought-for shape
     * @return shape of a given name or a {@link Shape#UNKNOWN} object if shape is not found
     */
    public static Shape of(String name) {
        return SHAPES.stream()
                .filter(shape -> shape.toString().equals(name))
                .findAny()
                .orElse(UNKNOWN);
    }

    /**
     * Represents the possible states of a game round
     */
    enum Result {
        WINS,
        LOSES,
        DRAW,

        /**
         * Null object for Result
         */
        UNKNOWN
    }
}
