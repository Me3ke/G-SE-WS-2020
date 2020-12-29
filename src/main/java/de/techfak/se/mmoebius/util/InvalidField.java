package de.techfak.se.mmoebius.util;

/**
 * The InvalidField class is an Exception which is thrown if
 * the playing field input file consists of invalid letters.
 */
public class InvalidField extends Exception {
    private static final long serialVersionUID = 8654185494602882245L;

    public InvalidField(String message) {
        super(message);
    }
}
