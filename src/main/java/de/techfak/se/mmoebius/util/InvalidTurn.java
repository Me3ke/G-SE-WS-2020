package de.techfak.se.mmoebius.util;

/**
 * The InvalidTurn class is an Exception which is thrown if
 * the player creates an input which is not allowed due to the rules.
 * (->Documentation).
 */
public class InvalidTurn extends Exception {
    private static final long serialVersionUID = 2338640972176893593L;

    public InvalidTurn(String message) {
        super(message);
    }
}
