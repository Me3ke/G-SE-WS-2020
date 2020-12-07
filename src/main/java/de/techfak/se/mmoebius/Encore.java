package de.techfak.se.mmoebius;

import java.io.*;

/**
 * The main class, contains just the main method to start the application.
 */

//TODO for Task #19799 create Classfile Board and other
    //TODO test if file throws exception if parameter is digit or symbol
public final class Encore {

    /* default */

    private Encore() {
    }

    public static void main(final String... args) {
        Game game = new Game(args);
        game.play();
    }
}

