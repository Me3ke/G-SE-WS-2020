package de.techfak.se.multiplayer.server.request_body;

import de.techfak.se.multiplayer.game.Player;

/**
 * The request to end the current round for a player.
 */
public class EndRoundBody {
    private String name;
    private int finalPoints;

    public EndRoundBody() {
        super();
    }

    public EndRoundBody(final String name, final int finalPoints) {
        this.name = name;
        this.finalPoints = finalPoints;
    }

    public EndRoundBody(final Player player) {
        this.name = player.getName().getName();
        this.finalPoints = player.getPoints().getValue();
    }

    public String getName() {
        return name;
    }

    public int getFinalPoints() {
        return finalPoints;
    }
}
