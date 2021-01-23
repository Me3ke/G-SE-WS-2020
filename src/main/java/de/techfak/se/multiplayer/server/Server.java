package de.techfak.se.multiplayer.server;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.exceptions.GameAlreadyFinishedException;
import de.techfak.se.multiplayer.game.exceptions.GameAlreadyRunningException;
import de.techfak.se.multiplayer.game.exceptions.GameNotYetRunningException;
import de.techfak.se.multiplayer.game.exceptions.InvalidStatusChangeException;
import de.techfak.se.multiplayer.game.exceptions.InvalidStatusException;
import de.techfak.se.multiplayer.game.exceptions.MissingNameException;
import de.techfak.se.multiplayer.game.exceptions.TurnAlreadySubmittedException;
import de.techfak.se.multiplayer.server.handlers.DeletePlayerHandler;
import de.techfak.se.multiplayer.server.handlers.EncoreHandler;
import de.techfak.se.multiplayer.server.handlers.GetBoardHandler;
import de.techfak.se.multiplayer.server.handlers.GetDiceHandler;
import de.techfak.se.multiplayer.server.handlers.GetPlayerHandler;
import de.techfak.se.multiplayer.server.handlers.GetRoundHandler;
import de.techfak.se.multiplayer.server.handlers.GetStatusHandler;
import de.techfak.se.multiplayer.server.handlers.PostPlayerHandler;
import de.techfak.se.multiplayer.server.handlers.PostRoundHandler;
import de.techfak.se.multiplayer.server.handlers.PostStatusHandler;
import de.techfak.se.multiplayer.server.response_body.ResponseObject;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;

/**
 * Server for the Encore multiplayer.
 */
public class Server {

    private static final int STATUS_BAD_REQUEST = 400;
    private static final int STATUS_CONFLICT = 409;

    private static final String URI_ENCORE = "/";
    private static final String URI_PLAYERS = "/api/game/players";
    private static final String URI_DICE = "/api/game/dice";
    private static final String URI_BOARD = "/api/game/board";
    private static final String URI_STATUS = "/api/game/status";
    private static final String URI_ROUND = "/api/game/round";

    private final Javalin app = Javalin.create();

    private final BaseGame baseGame;

    /**
     * Initializes the server with given base game.
     *
     * @param baseGame the game to play on
     */
    public Server(final BaseGame baseGame) {
        this.baseGame = baseGame;
        //register http method handlers
        app.get(URI_ENCORE, new EncoreHandler());
        app.get(URI_PLAYERS, new GetPlayerHandler(baseGame));
        app.post(URI_PLAYERS, new PostPlayerHandler(baseGame));
        app.delete(URI_PLAYERS, new DeletePlayerHandler(baseGame));
        app.get(URI_DICE, new GetDiceHandler(baseGame));
        app.get(URI_BOARD, new GetBoardHandler(baseGame));
        app.get(URI_STATUS, new GetStatusHandler(baseGame));
        app.post(URI_STATUS, new PostStatusHandler(baseGame));
        app.get(URI_ROUND, new GetRoundHandler(baseGame));
        app.post(URI_ROUND, new PostRoundHandler(baseGame));

        //register exception handlers
        app.exception(MissingNameException.class,
            (e, ctx) -> ctx.status(STATUS_BAD_REQUEST).json(ResponseObject.failure(e)));
        app.exception(GameNotYetRunningException.class,
            (e, ctx) -> ctx.status(STATUS_BAD_REQUEST).json(ResponseObject.failure(e)));
        app.exception(InvalidStatusException.class,
            (e, ctx) -> ctx.status(STATUS_BAD_REQUEST).json(ResponseObject.failure(e)));
        app.exception(TurnAlreadySubmittedException.class,
            (e, ctx) -> ctx.status(STATUS_BAD_REQUEST).json(ResponseObject.failure(e)));
        app.exception(BadRequestResponse.class,
            (e, ctx) -> ctx.status(STATUS_BAD_REQUEST).json(ResponseObject.failure("Der Reuqest war fehlerhaft!")));

        app.exception(GameAlreadyFinishedException.class,
            (e, ctx) -> ctx.status(STATUS_CONFLICT).json(ResponseObject.failure(e)));
        app.exception(GameAlreadyRunningException.class,
            (e, ctx) -> ctx.status(STATUS_CONFLICT).json(ResponseObject.failure(e)));
        app.exception(InvalidStatusChangeException.class,
            (e, ctx) -> ctx.status(STATUS_CONFLICT).json(ResponseObject.failure(e)));
    }

    public void start(final int port) {
        app.start(port);
    }

    public void stop() {
        app.stop();
    }

    public int getListeningPort() {
        return app.port();
    }

    public BaseGame getGame() {
        return baseGame;
    }
}
