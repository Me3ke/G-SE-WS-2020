package de.techfak.se.multiplayer.game;

import java.util.Objects;

/**
 * The board class which holds information about the initial board.
 */
public final class Board {

    private final String boardString;

    public Board(final String boardString) {
        this.boardString = boardString;
    }

    public String getBoardString() {
        return boardString;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Board board = (Board) object;
        return boardString.equals(board.boardString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardString);
    }
}
