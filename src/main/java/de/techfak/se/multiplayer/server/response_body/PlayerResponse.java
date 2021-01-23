package de.techfak.se.multiplayer.server.response_body;

import de.techfak.se.multiplayer.game.Player;

/**
 * The response containing a single player.
 */
public class PlayerResponse {
    private String name;
    private int points;
    private boolean finishedRound;

    public PlayerResponse() {
        super();
    }

    /**
     * Initializes the PlayerResponse by using information from given player.
     *
     * @param player the player to build a response from
     */
    public PlayerResponse(final Player player) {
        this.name = player.getName().getName();
        this.points = player.getPoints().getValue();
        this.finishedRound = player.getRound().isFinished();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(final int points) {
        this.points = points;
    }

    public boolean isFinishedRound() {
        return finishedRound;
    }

    public void setFinishedRound(final boolean finishedRound) {
        this.finishedRound = finishedRound;
    }
}
