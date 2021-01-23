package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.response_body.BoardResponse;
import io.javalin.http.Context;

/**
 * Handler to get the board configuration.
 */
public class GetBoardHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetBoardHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName player) {
        final BoardResponse response = new BoardResponse(game.getBoard());
        ctx.status(STATUS_OK).json(response);
    }
}
