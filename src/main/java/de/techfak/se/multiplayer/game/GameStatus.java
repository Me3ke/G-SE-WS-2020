package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.GameAlreadyFinishedException;
import de.techfak.se.multiplayer.game.exceptions.GameAlreadyRunningException;
import de.techfak.se.multiplayer.game.exceptions.GameNotYetRunningException;
import de.techfak.se.multiplayer.game.exceptions.InvalidStatusChangeException;

/**
 * The game status the game can be in.
 */
public enum GameStatus {
    /**
     * The game is not yet started.
     */
    NOT_STARTED {
        @Override
        public GameStatus tryAdvanceTo(final GameStatus gameStatus) {
            if (gameStatus != RUNNING) {
                throw new InvalidStatusChangeException();
            }
            return RUNNING;
        }
    },
    /**
     * The game is currently running.
     */
    RUNNING {
        @Override
        public GameStatus tryAdvanceTo(final GameStatus gameStatus) {
            if (gameStatus != FINISHED) {
                throw new InvalidStatusChangeException();
            }
            return FINISHED;
        }
    },
    /**
     * The game is finished.
     */
    FINISHED {
        @Override
        public GameStatus tryAdvanceTo(final GameStatus gameStatus) {
            throw new InvalidStatusChangeException();
        }
    };

    /**
     * Ensures the game status is running or finished.
     */
    public void requireStarted() {
        if (this == NOT_STARTED) {
            throw new GameNotYetRunningException();
        }
    }

    /**
     * Ensures the game status is Running.
     */
    public void requireRunning() {
        if (this == NOT_STARTED) {
            throw new GameNotYetRunningException();
        }
        if (this == FINISHED) {
            throw new GameAlreadyFinishedException();
        }
    }

    /**
     * Ensures the game status is not started.
     */
    public void requireNotStarted() {
        if (this == GameStatus.RUNNING) {
            throw new GameAlreadyRunningException();
        }
        if (this == GameStatus.FINISHED) {
            throw new GameAlreadyFinishedException();
        }
    }

    /**
     * Ensures the game status is not finished.
     */
    public void requireNotFinished() {
        if (this == FINISHED) {
            throw new GameAlreadyFinishedException();
        }
    }

    /**
     * Checks, if the current status can advance to the new status.
     * @param gameStatus the new status
     * @throws InvalidStatusChangeException if the status change would be illegal
     * @return the new status
     */
    public abstract GameStatus tryAdvanceTo(final GameStatus gameStatus);
}
