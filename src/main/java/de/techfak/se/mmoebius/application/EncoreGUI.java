package de.techfak.se.mmoebius.application;

import de.techfak.se.mmoebius.view.GUI;


/**
 * The main class, contains just the main method to start the application.
 */

public final class EncoreGUI {

    private static final int SYS_EXIT_FAILED = 100;
    private static final int SYS_EXIT_SUCCESS = 1;

    private EncoreGUI() {
    }

    /**
     * The main method launches the GUI.
     * @param args the program arguments which include a filepath of a playing field
     */
    public static void main(final String... args) {
            GUI.launch(GUI.class, args);
    }
}

