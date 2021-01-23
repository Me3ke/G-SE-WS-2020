package de.techfak.se.multiplayer.game.exceptions;

/**
 * Exception which is thrown if a player tries to submit their scores twice for the same round.
 */
public class TurnAlreadySubmittedException extends RuntimeException {

    private static final long serialVersionUID = 8484756734604984818L;

    @Override
    public String getMessage() {
        return "Du hast deinen Spielzug bereits abgeschlossen.";
    }

}
