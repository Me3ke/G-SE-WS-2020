package de.techfak.se.mmoebius.model;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 * The Dice class instantiates dices for the game consisting
 * of one number between one and four and a color
 * (Yellow,Blue,Green,Red,Orange).
 */
public class Dice {

    private static final int NUMBER_THRESHOLD = 5;
    private static final int COLOR_ORANGE = 1;
    private static final int COLOR_RED = 2;
    private static final int COLOR_GREEN = 4;
    private static final int COLOR_YELLOW = 3;

    /**
     * Dice attributes:
     * color: the color of the Dice.
     * number: the number of the Dice.
     */
    private Color color;
    private int number;

    /**
     * Creates a Dice using random numbers.
     */
    public Dice() {
        Random random = new Random();
        this.number = 1 + random.nextInt(NUMBER_THRESHOLD);
        int temp = 1 + random.nextInt(NUMBER_THRESHOLD + 1);
        switch (temp) {
            case COLOR_ORANGE:
                color = Color.ORANGE;
                break;
            case COLOR_RED:
                color = Color.RED;
                break;
            case COLOR_YELLOW:
                color = Color.YELLOW;
                break;
            case COLOR_GREEN:
                color = Color.GREEN;
                break;
            default:
                color = Color.BLUE;
                break;
        }
    }

    public Dice(Color color, int number) {
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

}
