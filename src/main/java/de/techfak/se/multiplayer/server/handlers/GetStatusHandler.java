package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;
import io.javalin.http.Context;

/**
 * Handler to get the current status of the game.
 */
public class GetStatusHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetStatusHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName name) {
        final StatusResponse response = new StatusResponse(game.getGameStatus());
        ctx.status(STATUS_OK).json(response);
    }
}
