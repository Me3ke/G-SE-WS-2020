package de.techfak.se.multiplayer.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Representation of the Dice.
 * <p>
 * Used to roll the six dice (color and number) for a game round.
 */
public class DiceImpl implements Dice {

    private static final int ROLLS_PER_TYP = 3;
    private final Random random;

    public DiceImpl() {
        this.random = new Random();
    }

    /**
     * Creates a new Dice with given seed for the random.
     *
     * @param seed for the random
     */
    public DiceImpl(final long seed) {
        this.random = new Random(seed);
    }

    /**
     * Rolls the dice.
     *
     * @return the dice result of rolled numbers and colors.
     */
    @Override
    public DiceResult roll() {
        final List<Color> rolledColors = new ArrayList<>();
        final List<Number> rolledNumbers = new ArrayList<>();
        final List<Color> colors = Arrays.asList(Color.values());
        final List<Number> numbers = Arrays.asList(Number.values());
        for (int i = 0; i < ROLLS_PER_TYP; i++) {
            rolledColors.add(colors.get(random.nextInt(colors.size())));
            rolledNumbers.add(numbers.get(random.nextInt(numbers.size())));
        }
        return new DiceResult(rolledNumbers, rolledColors);
    }
}
