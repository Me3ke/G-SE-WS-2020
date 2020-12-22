package de.techfak.se.mmoebius.application;

import de.techfak.se.mmoebius.model.Game;
import de.techfak.se.mmoebius.view.GUI;

import java.io.IOException;

/**
 * The main class, contains just the main method to start the application.
 */

public final class EncoreGUI {

    private EncoreGUI() {
    }

    /**
     * The main method creates a game, a board and starts the game after with a GUI.
     * @param args the program arguments which include a filepath of a playing field
     */
    public static void main(final String... args) {
        Game game = new Game(args);
        game.createBoard();
        GUI.launch(GUI.class, args);
        game.play();
    }
}

