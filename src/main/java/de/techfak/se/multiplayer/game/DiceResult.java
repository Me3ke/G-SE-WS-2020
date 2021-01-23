package de.techfak.se.multiplayer.game;

import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

/**
 * Represents a dice roll result.
 * <p>
 * Contains the rolled numbers as well as the rolled colors.
 */
public class DiceResult {

    private final List<Number> rolledNumbers;

    private final List<Color> rolledColors;

    public DiceResult() {
        this(List.of(), List.of());
    }

    public DiceResult(final List<Number> rolledNumbers, final List<Color> rolledColors) {
        this.rolledNumbers = unmodifiableList(rolledNumbers);
        this.rolledColors = unmodifiableList(rolledColors);
    }

    public List<Number> getNumbers() {
        return rolledNumbers;
    }

    public List<Color> getColors() {
        return rolledColors;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final DiceResult that = (DiceResult) other;
        return Objects.equals(rolledNumbers, that.rolledNumbers) && Objects.equals(rolledColors, that.rolledColors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rolledNumbers, rolledColors);
    }
}
