package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import static de.techfak.se.multiplayer.server.response_body.ResponseObject.failure;

/**
 * Handler to extract the player name from the query parameters.
 */
public abstract class AbstractPlayerAwareHandler implements Handler {

    private static final int STATUS_FORBIDDEN = 403;

    /**
     * The base game to operate on when handling a request.
     */
    protected final BaseGame game;

    /**
     * Initializes the Handler with given base game.
     *
     * @param baseGame the game to play on
     */
    public AbstractPlayerAwareHandler(final BaseGame baseGame) {
        this.game = baseGame;
    }

    @Override
    public final void handle(@NotNull final Context ctx) {
        final PlayerName playerName = new PlayerName(ctx.queryParam("name"));
        if (!game.containsPlayer(playerName)) {
            ctx.status(STATUS_FORBIDDEN).json(failure("Der Spieler ist nicht angemeldet."));
            return;
        }
        handle(ctx, playerName);
    }

    protected abstract void handle(final Context ctx, final PlayerName player);

}
