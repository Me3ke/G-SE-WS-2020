package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import io.javalin.http.Context;

import static de.techfak.se.multiplayer.server.response_body.ResponseObject.failure;
import static de.techfak.se.multiplayer.server.response_body.ResponseObject.successful;

/**
 * Handler to delete a player by name.
 */
public class DeletePlayerHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;
    private static final int STATUS_FORBIDDEN = 403;

    public DeletePlayerHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName playerName) {
        final boolean removed = game.removePlayer(playerName);
        if (removed) {
            ctx.status(STATUS_OK).json(successful("Du hast dich erfolgreich abgemeldet."));
        } else {
            ctx.status(STATUS_FORBIDDEN).json(failure("Der Spieler ist nicht angemeldet."));
        }
    }

}
