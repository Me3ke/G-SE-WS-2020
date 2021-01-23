package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.game.PlayerPoints;
import de.techfak.se.multiplayer.server.request_body.EndRoundBody;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import static de.techfak.se.multiplayer.server.response_body.ResponseObject.failure;
import static de.techfak.se.multiplayer.server.response_body.ResponseObject.successful;

/**
 * Handler to end the round of a player.
 */
public class PostRoundHandler implements Handler {

    private static final int STATUS_OK = 200;
    private static final int STATUS_FORBIDDEN = 403;

    private final BaseGame game;

    public PostRoundHandler(final BaseGame game) {
        this.game = game;
    }

    @Override
    public void handle(final Context ctx) {
        final EndRoundBody endRoundBody = ctx.bodyAsClass(EndRoundBody.class);
        final PlayerName playerName = new PlayerName(endRoundBody.getName());
        if (!game.containsPlayer(playerName)) {
            ctx.status(STATUS_FORBIDDEN).json(failure("Der Spieler ist nicht angemeldet."));
            return;
        }

        final PlayerPoints points = new PlayerPoints(endRoundBody.getFinalPoints());
        game.endRound(playerName, points);
        ctx.status(STATUS_OK).json(successful("Die Anfrage war erfolgreich."));
    }
}
