package de.techfak.se.mmoebius.util;

/**
 * The InvalidBoardLayout class is an Exception which is thrown if
 * the Board Layout is not how it should be. (-> Documentation).
 */
public class InvalidBoardLayout extends RuntimeException {
    private static final long serialVersionUID = -2836015217644277442L;

    public InvalidBoardLayout(String message) {
        super(message);
    }
}
