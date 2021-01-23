package de.techfak.se.multiplayer.game;

import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * The board parser implementation, which loads board configurations from files or strings.
 */
public class BoardParserImpl implements BoardParser {

    private static final int LENGTH_X = 15;
    private static final int LENGTH_Y = 7;
    private static final Set<Character> COLOR_CHARS = Set.of('r', 'g', 'b', 'y', 'o');

    /**
     * Initializes a new board.
     *
     * @param file the file that keeps the information about the board.
     * @return the parsed board
     * @throws InvalidBoardLayoutException if the field is larger or smaller than the expected 15x7 tiles
     * @throws InvalidFieldException       if the field contains invalid tile colors
     * @throws IOException                 if there is an issue with the file
     */
    @Override
    public Board parse(final File file) throws InvalidBoardLayoutException, InvalidFieldException, IOException {
        if (file.isDirectory()) {
            throw new IOException("File should not be a folder!");
        }
        return parse(Files.readAllLines(Paths.get(file.getPath())));
    }

    private Board parse(final List<String> lines) throws InvalidBoardLayoutException, InvalidFieldException {
        if (lines.size() != LENGTH_Y) {
            throw new InvalidBoardLayoutException("Wrong board size. Y value is not equal to " + LENGTH_Y);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String line : lines) {
            stringBuilder.append(parseLine(line)).append('\n');
        }
        return new Board(stringBuilder.toString());
    }

    private String parseLine(final String line) throws InvalidBoardLayoutException, InvalidFieldException {
        if (line.length() != LENGTH_X) {
            throw new InvalidBoardLayoutException("Wrong board size. X value is not equal to " + LENGTH_X);
        }
        for (final char character : line.toCharArray()) {
            if (!COLOR_CHARS.contains(Character.toLowerCase(character))) {
                throw new InvalidFieldException("Char not found " + character);
            }
        }
        return line;
    }
}
