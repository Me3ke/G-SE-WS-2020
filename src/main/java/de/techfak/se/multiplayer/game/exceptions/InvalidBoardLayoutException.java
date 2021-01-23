package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a board layout is invalid.
 */
public class InvalidBoardLayoutException extends Exception {

    private static final long serialVersionUID = 7250134072307999749L;

    public InvalidBoardLayoutException(final String message) {
        super(message);
    }
}
