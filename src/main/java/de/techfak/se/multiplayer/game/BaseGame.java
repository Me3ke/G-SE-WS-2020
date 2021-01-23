package de.techfak.se.multiplayer.game;

import java.util.List;

/**
 * The interface which defines all actions required for playing the game.
 */
public interface BaseGame {
    Board getBoard();

    GameStatus getGameStatus();

    void setGameStatus(final GameStatus gameStatus);

    List<Player> getPlayers();

    boolean containsPlayer(PlayerName name);

    boolean addPlayer(PlayerName name);

    boolean removePlayer(PlayerName name);

    Round getRound();

    void endRound(PlayerName name, PlayerPoints points);

    DiceResult getDiceResult();
}
