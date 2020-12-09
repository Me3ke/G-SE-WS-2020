package de.techfak.se.mmoebius;

public class InvalidTurn extends Exception {
    private static final long serialVersionUID = 2338640972176893593L;

    /**
     *
     * @param message
     */
    public InvalidTurn(String message) {
        super(message);
    }
}
