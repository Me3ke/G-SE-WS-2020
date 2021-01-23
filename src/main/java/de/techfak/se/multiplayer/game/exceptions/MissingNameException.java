package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a request is missing a player name.
 */
public class MissingNameException extends RuntimeException {

    private static final long serialVersionUID = -5093699320039626639L;

    public MissingNameException() {
        super();
    }

    public MissingNameException(final Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Du musst einen Namen angeben.";
    }

}
