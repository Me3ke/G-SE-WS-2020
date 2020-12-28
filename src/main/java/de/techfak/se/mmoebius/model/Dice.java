package de.techfak.se.mmoebius.model;

import javafx.scene.paint.Color;

import java.util.Random;

/**
 *
 */
public class Dice {

    /**
     *
     */
    private Color color;
    private int number;

    /**
     *
     */
    public Dice() {
        Random random = new Random();
        this.number = 1 + random.nextInt(4);
        int temp = random.nextInt(5);
        switch (temp)
        {
            case 1:
                color = Color.ORANGE;
                break;
            case 2:
                color = Color.RED;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.GREEN;
                break;
            default:
                color = Color.BLUE;
                break;
        }
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

}
