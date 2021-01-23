package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.response_body.DiceResponse;
import io.javalin.http.Context;

/**
 * Handler to get the current rolled dice.
 */
public class GetDiceHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetDiceHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName player) {
        final DiceResponse response = new DiceResponse(game.getDiceResult());
        ctx.status(STATUS_OK).json(response);
    }
}
