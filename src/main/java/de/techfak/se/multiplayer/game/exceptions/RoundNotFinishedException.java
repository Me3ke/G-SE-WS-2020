package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception if round is not finished.
 */
public class RoundNotFinishedException extends RuntimeException {

    private static final long serialVersionUID = 6154519552819526785L;

    public RoundNotFinishedException() {
        super();
    }

    public RoundNotFinishedException(final String message) {
        super(message);
    }

    public RoundNotFinishedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoundNotFinishedException(final Throwable cause) {
        super(cause);
    }
}
