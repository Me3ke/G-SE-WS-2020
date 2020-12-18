package de.techfak.se.mmoebius.model;

import de.techfak.se.mmoebius.util.InvalidBoardLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 */
public class Game {

    private static final int SYS_EXIT_FAILED = 100;
    private static final int ROW_COUNT = 7;
    private static final int COL_COUNT = 15;
    private static final char[][] MAP = new char[ROW_COUNT][COL_COUNT];

    /**
     * Game attributes:
     * args: program arguments.
     * board: the board to be played on.
     */
    private String[] args;
    private Board board;

    /**
     * Constructor for a game.
     * @param args the program arguments which should include a path to a playing field.
     */
    public Game(String... args) {
        this.args = args;
    }

    /**
     * The createBoard method uses the program arguments to read in the given file
     * and creates a 2-dim char array of it. Then a board is created from it an printed.
     * If the parameters are not valid, the program exits with code 100.
     * @throws InvalidBoardLayout if the file does not correspond to the specifications.
     */
    public void createBoard() {
        System.out.println("Welcome to encore");
        if (args.length == 0) {
            System.out.println("<100> No program arguments given. Type -f <filename>");
        } else if (args[0].equals("-f")) {
            File file = new File(args[1]);
            if (file.isFile() && file.canRead()) {
                String line;
                int rowCount = 0;
                int colCount = 0;
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    while ((line = reader.readLine()) != null) {
                        if (line.length() != COL_COUNT) {
                            throw new InvalidBoardLayout("Invalid Board Layout <101> one line is too long/short");
                        }
                        for (colCount = 0; colCount < line.length(); colCount++) {
                            MAP[rowCount][colCount] = line.charAt(colCount);
                        }
                        rowCount++;
                    }
                    System.out.println("Row Count: " + rowCount);
                    System.out.println("Column Count: " + colCount);
                    System.out.println("The size of the board is therefore valid");
                } catch (IOException e) {
                    System.out.println("Source file is not valid. Check documentation for further information");
                } catch (IndexOutOfBoundsException e) {
                    throw new InvalidBoardLayout("Invalid Board Layout <101> row or column not suitable");
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Board board = new Board(MAP);
                this.board = board;
                board.printBoard();
                return;
            }
            System.out.println("<100> no valid file found with filename: " + file.getName());
        } else {
            System.out.println("<100> unknown program argument. Type -f <filename>");
        }
        System.exit(SYS_EXIT_FAILED);
    }

    /**
     * The play method is the basic loop to be played in.
     * The play method asks for inputs and processes them.
     * - validate, update, print, set points and test if finished.
     * The escParameter is an indicator if the game has been canceled by user.
     */
    public void play() {
        Player playerOne = new Player(1, board);
        Score scoreOne = new Score(playerOne);
        int escParameter;
        do {
            System.out.println("Type in your play move Player" + playerOne.getPlayerNumber() + ": ");
            escParameter = playerOne.playMove();
            if (escParameter > 1) {
                board.printBoard();
                playerOne.setPoints(scoreOne.calculatePoints(board));
                scoreOne.printPoints();
                if (scoreOne.testIfFinished(board)) {
                    System.out.println("Game is over. Final Scores:");
                    scoreOne.printPoints();
                    break;
                }
            }
        } while (escParameter != 0);
    }
}

