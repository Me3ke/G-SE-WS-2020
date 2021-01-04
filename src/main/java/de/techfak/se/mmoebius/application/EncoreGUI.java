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
        int indicator = game.createBoard(0, 0);
        if (indicator != 1) {
            System.exit(indicator);
        }
        String[] argv = new String[args.length + 2];
        for (int i = 0; i < args.length; i++) {
            argv[i] = args[i];
        }
        argv[args.length] = String.valueOf(game.getRowCount());
        argv[args.length + 1] = String.valueOf(game.getColCount());
        GUI.launch(GUI.class, argv);
    }
}

