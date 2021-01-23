package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a game is already finished.
 */
public class GameAlreadyFinishedException extends RuntimeException {

    private static final long serialVersionUID = 6161381917968554545L;

    @Override
    public String getMessage() {
        return "Das Spiel wurde bereits beendet.";
    }

}
