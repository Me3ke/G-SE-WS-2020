package de.techfak.se.mmoebius.util;

/**
 *
 */
public class InvalidBoardLayout extends RuntimeException {
    private static final long serialVersionUID = -2836015217644277442L;

    public InvalidBoardLayout(String message) {
        super(message);
    }
}
