package de.techfak.se.mmoebius;

public class InvalidField extends RuntimeException {
    private static final long serialVersionUID = 8654185494602882245L;

    /**
     *
     * @param message
     */
    public InvalidField(String message) {
        super(message);
    }
}
