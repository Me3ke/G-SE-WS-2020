package de.techfak.se.mmoebius.model;

import de.techfak.se.mmoebius.util.InvalidBoardLayout;
import de.techfak.se.mmoebius.util.InvalidField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The game class is where the game runs in a loop. It is
 * instantiated first and instantiates all other components
 * such as Player, Board or Score. The game will be started
 * and terminated in this class.
 */
@SuppressWarnings("PMD.CloseResource")
public class Game {

    private static final int ROW_COUNT = 7;
    private static final int COL_COUNT = 15;
    private static final int MAX_ROW_COL_COUNT = 100;
    private static final int DICE_COUNT = 3;
    private static final int SYS_EXIT_INVALID_SOURCE = 100;
    private static final int SYS_EXIT_INVALID_FILE = 101;

    /**
     * Game attributes:
     * args: program arguments.
     * board: the board to be played on.
     */
    private final String[] args;
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
     * @throws InvalidField if the file does not correspond to the specifications.
     * @return returns a value to indicate if creatingBoard was successful
     */
    @SuppressWarnings({"PMD.AvoidFileStream", "PMD.PreserveStackTrace"})
    public int createBoard() {
        try {
            System.out.println("Welcome to encore");
            if (args.length == 0) {
                System.out.println("<100> No program arguments given. Type -f <filename>");
                return SYS_EXIT_INVALID_SOURCE;
            } else if (args[0].equals("-f")) {
                File file = new File(args[1]);
                if (file.isFile() && file.canRead()) {
                    String line;
                    System.out.println("Type in the number of rows in the given playing field: ");
                    int rowCount = readRow();
                    System.out.println("Type in the number of columns in the given playing field: ");
                    int colCount = readCol();
                    char[][] map = new char[rowCount][colCount];
                    int rowCountCounter = 0;
                    int colCountCounter = 0;
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(file));
                        while ((line = reader.readLine()) != null) {
                            if (line.length() != colCount) {
                                throw new InvalidBoardLayout("Invalid Board Layout <101> one line is too long/short");
                            }
                            for (colCountCounter = 0; colCountCounter < line.length(); colCountCounter++) {
                                map[rowCountCounter][colCountCounter] = line.charAt(colCountCounter);
                            }
                            rowCountCounter++;
                        }
                        System.out.println("Checking Data from input file...");
                        System.out.println("Row Count: " + rowCountCounter);
                        System.out.println("Column Count: " + colCountCounter);
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
                    Board board = new Board(map);
                    if (board.getFloor() == null) {
                        throw new InvalidField("Unexpected Character found in input file");
                    }
                    this.board = board;
                    board.printBoard();
                    return 1;
                }
                System.out.println("<100> no valid file found with filename: " + file.getName());
                return SYS_EXIT_INVALID_SOURCE;
            } else {
                System.out.println("<100> unknown program argument. Type -f <filename>");
                return SYS_EXIT_INVALID_SOURCE;
            }
        } catch (InvalidBoardLayout | InvalidField e) {
            System.out.println(e);
            return SYS_EXIT_INVALID_FILE;
        }
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
            System.out.println("Your Dices: ");
            Dice[] dices = new Dice[DICE_COUNT];
            for (int i = 0; i < DICE_COUNT; i++) {
                dices[i] = new Dice();
                System.out.print(dices[i].getNumber());
                printColor(dices[i].getColor());
            }
            System.out.println("Type in your play move Player" + playerOne.getPlayerNumber() + ": ");
            escParameter = playerOne.playMove(dices);
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

    /**
     * The printColor method prints the name of the color
     * object in parameter for debugging purposes.
     * @param color The color which name should be printed out.
     */
    private void printColor(Color color) {
        if (color.equals(Color.ORANGE)) {
            System.out.println(" orange");
        } else if (color.equals(Color.GREEN)) {
            System.out.println(" green");
        } else if (color.equals(Color.BLUE)) {
            System.out.println(" blue");
        } else if (color.equals(Color.RED)) {
            System.out.println(" red");
        } else if (color.equals(Color.YELLOW)) {
            System.out.println(" yellow");
        } else {
            System.out.println(" unknown");
        }
    }

    /**
     *  reads the number of columns from the console.
     * @return the number of columns
     */
    private int readCol() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty() || input.length() > MAX_ROW_COL_COUNT) {
            System.out.println("column input is empty or invalid, taking default value");
            return COL_COUNT;
        } else {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("column input is not valid, taking default value");
                return COL_COUNT;
            }
        }
    }

    /**
     * reads the number of rows from the console.
     * @return the number of rows
     */
    private int readRow() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty() || input.length() > MAX_ROW_COL_COUNT) {
            System.out.println("row input is empty or invalid, taking default value");
            return ROW_COUNT;
        } else {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" row input is not valid, taking default value");
                return ROW_COUNT;
            }
        }
    }

    public Board getBoard() {
        return board;
    }
}

