package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.TurnAlreadySubmittedException;

import java.util.List;

import static de.techfak.se.multiplayer.game.GameStatus.NOT_STARTED;
import static de.techfak.se.multiplayer.game.GameStatus.RUNNING;

/**
 * The BaseGame implementation used within the server.
 * <p>
 * Can be replaced by custom code.
 * It represents the game state and is used to interact with the underlying player data.
 */
public class BaseGameImpl implements BaseGame {

    private final Board board;
    private final Dice dice = new DiceImpl();
    private final Players players = new Players();
    private GameStatus gameStatus = NOT_STARTED;
    private DiceResult diceResult = new DiceResult();
    private Round round = new Round();

    /**
     * Initializes the game with a given board configuration.
     *
     * @param board the board configuration
     */
    public BaseGameImpl(final Board board) {
        this.board = board;
    }

    /**
     * Sets the game status of the game.
     * <p>
     * When the game status is updated to running the game will start.
     * Each player enters the first round of the game and the dice will be rolled.
     *
     * @param gameStatus the game status to update to
     * @implNote The game always starts with game status NOT_STARTED.
     */
    @Override
    public void setGameStatus(final GameStatus gameStatus) {
        this.gameStatus = this.gameStatus.tryAdvanceTo(gameStatus);

        if (this.gameStatus == RUNNING) {
            players.forEach(player -> player.setRound(this.round.first()));
            this.round = this.round.first();
            this.diceResult = dice.roll();
        }
    }

    /**
     * Adds a player name to the player list.
     *
     * @param name the player name to add
     * @return if the player name could be added to the list
     * @implNote A player cant be added when the game is running or finished
     */
    @Override
    public boolean addPlayer(final PlayerName name) {
        gameStatus.requireNotStarted();
        return players.addPlayer(name);
    }

    /**
     * Removes a player name from the player list.
     *
     * @param name the player name to remove
     * @return if the player name could be removed from the list
     * @implNote A player cant be removed when the game is finished
     */
    @Override
    public boolean removePlayer(final PlayerName name) {
        gameStatus.requireNotFinished();
        return players.removePlayer(name);
    }

    /**
     * Ends the current round for the given player name.
     * <p>
     * If each player finished the current round the game will finished the round global and tries to enter
     * the next round.
     *
     * @param name   the name of the player that want to finish their round
     * @param points the final points of the player
     * @implNote A player cant submit the end of a round multiple times and only if the came is not finished yet.
     */
    @Override
    public void endRound(final PlayerName name, final PlayerPoints points) {
        final PlayerImpl player = players.getRegisteredPlayerByName(name);
        if (player.getRound().isFinished()) {
            gameStatus.requireRunning();
            throw new TurnAlreadySubmittedException();
        } else {
            gameStatus.requireStarted();
        }

        player.finishRound(points);
        if (isRoundFinished()) {
            finishRound();
        }
    }

    /**
     * Checks if each player has finished the current round and therefore the round is over.
     *
     * @return if the round is over
     * @implNote The round isn't over, if any player has not finished their round.
     */
    private boolean isRoundFinished() {
        for (final Player player : players) {
            if (!player.getRound().isFinished()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Finishes the current round and entering next round if game is still running.
     * <p>
     * When starting the next round each player needs to enter the next round and the dice roll
     * needs to produce a new dice result for the upcoming round.
     */
    private void finishRound() {
        this.round.finish();
        if (this.gameStatus == RUNNING) {
            this.round = this.round.next();
            players.forEach(PlayerImpl::enterNextRound);
            this.diceResult = dice.roll();
        }
    }

    /**
     * Gets the current round of the game.
     *
     * @return the current round of the game
     * @implNote The game need to be started, otherwise the game isn't in any round at all.
     */
    @Override
    public Round getRound() {
        gameStatus.requireStarted();
        return round;
    }

    /**
     * Gets the current dice result.
     *
     * @return the current round of the game
     * @implNote The game need to be started, otherwise the game has not rolled any dice yet.
     */
    @Override
    public DiceResult getDiceResult() {
        gameStatus.requireStarted();
        return diceResult;
    }

    @Override
    public List<Player> getPlayers() {
        return players.getPlayers();
    }

    @Override
    public boolean containsPlayer(final PlayerName name) {
        return players.containsPlayer(name);
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
