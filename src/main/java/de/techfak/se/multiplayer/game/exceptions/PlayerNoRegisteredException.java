package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception when player is not registered.
 */
public class PlayerNoRegisteredException extends RuntimeException {

    private static final long serialVersionUID = -1244960100033283321L;

    public PlayerNoRegisteredException() {
        super();
    }

    public PlayerNoRegisteredException(final String message) {
        super(message);
    }

    public PlayerNoRegisteredException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PlayerNoRegisteredException(final Throwable cause) {
        super(cause);
    }
}
