package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;

import java.io.File;
import java.io.IOException;

/**
 * The interface to parse a board based on a file or a string.
 */
public interface BoardParser {

    Board parse(final File boardFile) throws InvalidBoardLayoutException, InvalidFieldException, IOException;
}
