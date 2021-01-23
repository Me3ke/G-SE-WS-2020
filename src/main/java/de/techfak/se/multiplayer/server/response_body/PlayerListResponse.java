package de.techfak.se.multiplayer.server.response_body;

import java.util.List;

/**
 * The response that contains a list of players including the according points.
 */
public class PlayerListResponse extends ResponseObject {

    private List<PlayerResponse> players;

    public PlayerListResponse() {
        super();
    }

    public PlayerListResponse(final List<PlayerResponse> players) {
        super(true, "Die Anfrage war erfolgreich.");
        this.players = players;
    }

    public void setPlayers(final List<PlayerResponse> players) {
        this.players = players;
    }

    public List<PlayerResponse> getPlayers() {
        return players;
    }
}
