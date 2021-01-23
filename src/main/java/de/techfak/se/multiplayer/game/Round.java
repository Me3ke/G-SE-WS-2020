package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.RoundNotFinishedException;

import java.util.Objects;

/**
 * The round the game and players are in.
 */
public final class Round {
    private final int roundNumber;
    private boolean finished;

    public Round() {
        this(0, false);
    }

    private Round(final int round, final boolean finished) {
        this.roundNumber = round;
        this.finished = finished;
    }

    public void finish() {
        this.finished = true;
    }

    public Round first() {
        return new Round(1, false);
    }

    public Round next() {
        this.requireRoundFinished();
        return new Round(this.roundNumber + 1, false);
    }

    public int getRound() {
        return roundNumber;
    }

    public boolean isFinished() {
        return finished;
    }

    private void requireRoundFinished() {
        if (!finished) {
            throw new RoundNotFinishedException();
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Round round = (Round) object;
        return roundNumber == round.roundNumber && finished == round.finished;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundNumber, finished);
    }
}
