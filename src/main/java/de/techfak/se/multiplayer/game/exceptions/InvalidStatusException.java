package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a game status is invalid.
 */
public class InvalidStatusException extends RuntimeException {

    private static final long serialVersionUID = 6411889891631423173L;

    public InvalidStatusException() {
        super();
    }

    public InvalidStatusException(final Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Der angegebene Status ist ungültig.";
    }

}
