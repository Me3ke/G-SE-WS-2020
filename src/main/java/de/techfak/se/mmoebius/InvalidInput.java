package de.techfak.se.mmoebius;

public class InvalidInput extends Exception {
    private static final long serialVersionUID = -1593586720091762555L;

    /**
     *
     * @param message
     */
    public InvalidInput(String message) {
        super(message);
    }
}
