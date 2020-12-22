package de.techfak.se.mmoebius.application;

import de.techfak.se.mmoebius.controller.Controller;
import de.techfak.se.mmoebius.model.Board;
import de.techfak.se.mmoebius.model.Game;
import de.techfak.se.mmoebius.view.GUI;

import java.io.IOException;

/**
 * The main class, contains just the main method to start the application.
 */

public final class EncoreGUI {

    private static final int SYS_EXIT_FAILED = 100;

    private EncoreGUI() {
    }

    /**
     * The main method creates a game, a board and starts the game after with a GUI.
     * Additionally provides the global row and column count to the application.
     * @param args the program arguments which include a filepath of a playing field
     */
    public static void main(final String... args) {
        Game game = new Game(args);
        int indicator = game.createBoard();
        if (indicator == -1) {
            System.exit(SYS_EXIT_FAILED);
        }
        String[] arguments = new String[2];
        arguments[0] = String.valueOf(game.getBoard().getRowCount());
        arguments[1] = String.valueOf(game.getBoard().getColCount());
        GUI.launch(GUI.class, arguments);
        game.play();
    }
}

