package de.techfak.se.mmoebius.util;

/**
 * The InvalidInput class is an Exception which is thrown if
 * a player creates and input that is formally wrong. (only used in terminal application)
 */
public class InvalidInput extends Exception {
    private static final long serialVersionUID = -1593586720091762555L;

    public InvalidInput(String message) {
        super(message);
    }
}
