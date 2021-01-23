package de.techfak.se.multiplayer.server.handlers;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.PlayerName;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import static de.techfak.se.multiplayer.server.response_body.ResponseObject.failure;
import static de.techfak.se.multiplayer.server.response_body.ResponseObject.successful;

/**
 * Handler to register a new player.
 */
public class PostPlayerHandler implements Handler {

    private static final int STATUS_OK = 200;
    private static final int STATUS_CONFLICT = 409;

    private final BaseGame game;

    public PostPlayerHandler(final BaseGame game) {
        this.game = game;
    }

    @Override
    public void handle(@NotNull final Context ctx) throws Exception {
        final RegisterBody registerBody = ctx.bodyAsClass(RegisterBody.class);
        final PlayerName playerName = new PlayerName(registerBody.getName());
        final boolean added = game.addPlayer(playerName);
        if (!added) {
            ctx.status(STATUS_CONFLICT).json(failure("Der Name ist bereits vergeben."));
            return;
        }
        ctx.status(STATUS_OK).json(successful("Du kannst mitspielen!"));
    }

}
