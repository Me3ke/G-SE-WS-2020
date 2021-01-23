package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a board contains invalid fields.
 */
public class InvalidFieldException extends Exception {

    private static final long serialVersionUID = -7523303781419178341L;

    public InvalidFieldException(final String message) {
        super(message);
    }
}
