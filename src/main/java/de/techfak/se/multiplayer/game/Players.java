package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.MissingNameException;
import de.techfak.se.multiplayer.game.exceptions.PlayerNoRegisteredException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * A collection of players.
 */
/* default */ class Players implements Iterable<PlayerImpl> {

    private final List<PlayerImpl> playerList = new ArrayList<>();

    public List<Player> getPlayers() {
        return List.copyOf(playerList);
    }

    public PlayerImpl getRegisteredPlayerByName(final PlayerName playerName) {
        return playerList.stream().filter(player -> player.getName().equals(playerName))
            .findFirst()
            .orElseThrow(PlayerNoRegisteredException::new);
    }

    public boolean addPlayer(final PlayerName name) {
        if (containsPlayer(name)) {
            return false;
        }
        return playerList.add(new PlayerImpl(name));
    }

    public boolean containsPlayer(final PlayerName name) {
        if (name == null) {
            throw new MissingNameException();
        }
        return playerList.stream().anyMatch(player -> player.getName().equals(name));
    }

    public boolean removePlayer(final PlayerName name) {
        return playerList.removeIf(player -> player.getName().equals(name));
    }

    @NotNull
    @Override
    public Iterator<PlayerImpl> iterator() {
        return playerList.iterator();
    }

    @Override
    public void forEach(final Consumer<? super PlayerImpl> action) {
        playerList.forEach(action);
    }
}
