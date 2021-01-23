package de.techfak.se.multiplayer.game;

/**
 * The Player interface.
 */
public interface Player {

    PlayerPoints getPoints();

    PlayerName getName();

    Round getRound();

}
