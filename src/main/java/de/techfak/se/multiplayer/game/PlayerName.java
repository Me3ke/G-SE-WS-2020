package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.MissingNameException;

import java.util.Objects;

/**
 * The player name.
 */
public final class PlayerName {

    private final String name;

    /**
     * Initializes a new player name.
     *
     * @param name the player's name
     */
    public PlayerName(final String name) {
        if (name == null || name.isBlank()) {
            throw new MissingNameException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final PlayerName that = (PlayerName) object;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
