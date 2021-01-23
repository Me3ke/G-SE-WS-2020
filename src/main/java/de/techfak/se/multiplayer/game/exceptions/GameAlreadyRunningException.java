package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a game is already running.
 */
public class GameAlreadyRunningException extends RuntimeException {

    private static final long serialVersionUID = 4036774360579318138L;

    @Override
    public String getMessage() {
        return "Das Spiel läuft bereits.";
    }

}
