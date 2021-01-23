package de.techfak.se.multiplayer.game;

/**
 * The Player implementation.
 */
public class PlayerImpl implements Player {

    private final PlayerName name;

    private PlayerPoints points;
    private Round round;

    /**
     * Initializes a new player.
     *
     * @param name the player's name
     */
    public PlayerImpl(final PlayerName name) {
        this.name = name;
        this.points = new PlayerPoints(0);
        this.round = new Round();
    }

    @Override
    public PlayerPoints getPoints() {
        return points;
    }

    @Override
    public PlayerName getName() {
        return name;
    }

    @Override
    public Round getRound() {
        return round;
    }

    public void setRound(final Round round) {
        this.round = round;
    }

    /* default */ void finishRound(final PlayerPoints points) {
        this.points = points;
        this.round.finish();
    }

    /* default */ void enterNextRound() {
        this.round = this.round.next();
    }
}
