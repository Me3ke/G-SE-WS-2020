package de.techfak.se.mmoebius.application;

import de.techfak.se.mmoebius.model.Game;

/**
 * The main class, contains just the main method to start the application.
 */

public final class Encore {

    private Encore() {
    }

    /**
     * The main method creates a game, a board and starts the game after.
     * @param args the program arguments which include a filepath of a playing field
     */
    public static void main(final String... args) {
        Game game = new Game(args);
        int indicator = game.createBoard(0, 0);
        if (indicator != 1) {
            System.exit(indicator);
        }
        game.play();
    }
}

