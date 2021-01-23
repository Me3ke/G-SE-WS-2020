package de.techfak.se.multiplayer.server.response_body;

import de.techfak.se.multiplayer.game.Color;
import de.techfak.se.multiplayer.game.DiceResult;
import de.techfak.se.multiplayer.game.Number;

import java.util.List;

/**
 * The response containing dice.
 */
public class DiceResponse extends ResponseObject {
    private List<Color> colors;
    private List<Number> numbers;

    public DiceResponse() {
        super();
    }

    /**
     * Initializes a server response containing the dice result.
     *
     * @param diceResult the result of the dice
     */
    public DiceResponse(final DiceResult diceResult) {
        super(true, "Alea iacta est.");
        this.colors = diceResult.getColors();
        this.numbers = diceResult.getNumbers();
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setColors(final List<Color> colors) {
        this.colors = colors;
    }

    public void setNumbers(final List<Number> numbers) {
        this.numbers = numbers;
    }
}
