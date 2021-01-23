package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.game.Round;
import de.techfak.se.multiplayer.server.response_body.RoundResponse;
import io.javalin.http.Context;

/**
 * Handler to get the current round of the game.
 */
public class GetRoundHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetRoundHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName name) {
        final Round round = game.getRound();
        final RoundResponse response = new RoundResponse(round);
        ctx.status(STATUS_OK).json(response);
    }

}
