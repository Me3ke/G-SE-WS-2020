package de.techfak.se.mmoebius;

@SuppressWarnings("serial")
public class InvalidBoardLayout extends RuntimeException {
    public InvalidBoardLayout(String message) {
        super(message);
    }
}
