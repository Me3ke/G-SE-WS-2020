package de.techfak.se.mmoebius;

/**
 * The main class, contains just the main method to start the application.
 */


//TODO test if file throws exception if parameter is digit or symbol
public final class Encore {

    /* default */

    private Encore() {
    }

    public static void main(final String... args) {
        Game game = new Game(args);
        game.createBoard();
        game.play();
    }
}

