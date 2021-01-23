package de.techfak.se.multiplayer.game;

import java.util.List;

/**
 * Wraps a BaseGame to be synchronized to prevent race conditions.
 */
public class SynchronizedGame implements BaseGame {

    private final Object lock = new Object();
    private final BaseGame baseGame;

    public SynchronizedGame(final Board board) {
        this.baseGame = new BaseGameImpl(board);
    }

    /**
     * Initializes a new synchronized game and wraps a game.
     *
     * @param baseGame the game instance used
     */
    public SynchronizedGame(final BaseGame baseGame) {
        this.baseGame = baseGame;
    }

    @Override
    public Board getBoard() {
        synchronized (lock) {
            return baseGame.getBoard();
        }
    }

    @Override
    public GameStatus getGameStatus() {
        synchronized (lock) {
            return baseGame.getGameStatus();
        }
    }

    @Override
    public void setGameStatus(final GameStatus gameStatus) {
        synchronized (lock) {
            baseGame.setGameStatus(gameStatus);
        }
    }

    @Override
    public List<Player> getPlayers() {
        synchronized (lock) {
            return List.copyOf(baseGame.getPlayers());
        }
    }

    @Override
    public boolean containsPlayer(final PlayerName name) {
        synchronized (lock) {
            return baseGame.containsPlayer(name);
        }
    }

    @Override
    public boolean addPlayer(final PlayerName name) {
        synchronized (lock) {
            return baseGame.addPlayer(name);
        }
    }

    @Override
    public boolean removePlayer(final PlayerName name) {
        synchronized (lock) {
            return baseGame.removePlayer(name);
        }
    }

    @Override
    public Round getRound() {
        synchronized (lock) {
            return baseGame.getRound();
        }
    }

    @Override
    public void endRound(final PlayerName name, final PlayerPoints points) {
        synchronized (lock) {
            baseGame.endRound(name, points);
        }
    }

    @Override
    public DiceResult getDiceResult() {
        synchronized (lock) {
            return baseGame.getDiceResult();
        }
    }
}
