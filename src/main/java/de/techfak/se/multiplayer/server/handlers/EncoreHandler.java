package de.techfak.se.multiplayer.server.handlers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

/**
 * Handler to response with plain text "Encore".
 */
public class EncoreHandler implements Handler {
    @Override
    public void handle(@NotNull final Context context) {
        context.result("Encore");
    }
}
