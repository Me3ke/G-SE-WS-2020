package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.request_body.StatusBody;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import static de.techfak.se.multiplayer.server.response_body.ResponseObject.failure;

/**
 * Handler to update the game status.
 */
public class PostStatusHandler implements Handler {

    private static final int STATUS_OK = 200;
    private static final int STATUS_FORBIDDEN = 403;

    private final BaseGame game;

    public PostStatusHandler(final BaseGame game) {
        this.game = game;
    }

    @Override
    public void handle(final Context ctx) {
        final StatusBody statusBody = ctx.bodyAsClass(StatusBody.class);
        if (!game.containsPlayer(new PlayerName(statusBody.getName()))) {
            ctx.status(STATUS_FORBIDDEN).json(failure("Der Spieler ist nicht angemeldet."));
            return;
        }

        game.setGameStatus(statusBody.getStatus());
        final StatusResponse response = new StatusResponse(game.getGameStatus());
        ctx.status(STATUS_OK).json(response);
    }
}
