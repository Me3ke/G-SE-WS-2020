package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a status change is invalid.
 */
public class InvalidStatusChangeException extends RuntimeException {

    private static final long serialVersionUID = 2646816182251811555L;

    @Override
    public String getMessage() {
        return "Der Statuswechsel ist ungültig.";
    }

}
