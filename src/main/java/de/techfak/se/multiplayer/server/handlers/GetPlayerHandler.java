package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.Player;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.response_body.PlayerListResponse;
import de.techfak.se.multiplayer.server.response_body.PlayerResponse;
import de.techfak.se.multiplayer.server.response_body.ResponseObject;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler to list all players that are playing.
 */
public class GetPlayerHandler extends AbstractPlayerAwareHandler {

    private static final int STATUS_OK = 200;

    public GetPlayerHandler(final BaseGame baseGame) {
        super(baseGame);
    }

    @Override
    protected void handle(final Context ctx, final PlayerName playerName) {
        final List<PlayerResponse> players = new ArrayList<>();
        for (final Player player : game.getPlayers()) {
            players.add(new PlayerResponse(player));
        }
        final ResponseObject response = new PlayerListResponse(players);
        ctx.json(response).status(STATUS_OK);
    }

}
