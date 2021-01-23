package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a game is not yet running.
 */
public class GameNotYetRunningException extends RuntimeException {

    private static final long serialVersionUID = 3282852149889549823L;

    @Override
    public String getMessage() {
        return "Das Spiel läuft noch nicht.";
    }

}
