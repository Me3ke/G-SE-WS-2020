package de.techfak.se.multiplayer.server.response_body;

import de.techfak.se.multiplayer.game.Round;

/**
 * The response containing information about a round.
 */
public class RoundResponse extends ResponseObject {

    private int round;

    public RoundResponse() {
        super();
    }

    public RoundResponse(final Round round) {
        super(true, "Die Anfrage war erfolgreich.");
        this.round = round.getRound();
    }

    public int getRound() {
        return round;
    }

    public void setRound(final int round) {
        this.round = round;
    }
}
