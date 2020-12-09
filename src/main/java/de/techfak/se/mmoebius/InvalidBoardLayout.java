package de.techfak.se.mmoebius;


public class InvalidBoardLayout extends RuntimeException {
    private static final long serialVersionUID = -2836015217644277442L;

    /**
     *
     * @param message
     */
    public InvalidBoardLayout(String message) {
        super(message);
    }
}
