package de.techfak.se.mmoebius.application;

import de.techfak.se.mmoebius.model.Game;
import de.techfak.se.mmoebius.view.GUI;

/**
 * The main class, contains just the main method to start the application.
 */

public final class EncoreGUI {

    private EncoreGUI() {
    }

    /**
     * The main method launches the GUI.
     * @param args the program arguments which include a filepath of a playing field
     */
    public static void main(final String... args) {
        Game game = new Game(args);
        int indicator = game.createBoard();
        if (indicator != 1) {
            System.exit(indicator);
        }
        GUI.launch(GUI.class, args);
        System.exit(0);
    }
}

