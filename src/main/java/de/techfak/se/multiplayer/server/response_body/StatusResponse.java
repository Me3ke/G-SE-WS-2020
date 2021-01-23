package de.techfak.se.multiplayer.server.response_body;

import de.techfak.se.multiplayer.game.GameStatus;

/**
 * The status response of the game.
 */
public class StatusResponse extends ResponseObject {

    private GameStatus status;

    public StatusResponse() {
        super();
    }

    /**
     * Initializes the StatusResponse by using information from given game status.
     *
     * @param status the game status
     */
    public StatusResponse(final GameStatus status) {
        super(true, "Die Anfrage war erfolgreich.");
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(final GameStatus status) {
        this.status = status;
    }
}
