package de.techfak.se.multiplayer.server.request_body;

/**
 * The request to register to the game.
 */
public class RegisterBody {

    private String name;

    public RegisterBody() {
        super();
    }

    public RegisterBody(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
