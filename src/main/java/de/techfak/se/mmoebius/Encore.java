package de.techfak.se.mmoebius;

/**
 * The main class, contains just the main method to start the application.
 */
public final class Encore {

    private Encore() {
    }

    public static void main(final String... args) {
        Game g = new Game();
        g.play();
    }

}
