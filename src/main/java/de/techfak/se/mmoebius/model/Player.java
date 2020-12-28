package de.techfak.se.mmoebius.model;

import de.techfak.se.mmoebius.util.InvalidInput;
import javafx.scene.paint.Color;

import java.util.Scanner;

/**
 *
 */
public class Player {

    private static final int ASCII_CODE_CONST_CHAR = 65;
    private static final int ASCII_CODE_CONST_INT = 49;
    private static final int ASCII_THRESHOLD_COL = 14;
    private static final int ASCII_THRESHOLD_ROW = 6;

    /**
     * Player attributes:
     * playerNumber: the specific number of a player.
     * board: the board on which the player plays.
     * points: the points of the player.
     */
    private int playerNumber;
    private Board board;
    private int points;


    /**
     * Constructor for a Player.
     * Every player starts with 0 points.
     * @param playerNumber the playerNumber is set
     * @param board the board is set
     */
    public Player(int playerNumber, Board board) {
        this.playerNumber = playerNumber;
        this.board = board;
        points = 0;
    }

    /**
     * The playMove method reads the user input and checks it.
     * The input is checked only on format. The validate method is called after
     * to validate the move if there is no error in format.
     * Also splits the input (e.g. H4,H5,G5,G4) into a String array (e.g. {H4,H5,G5,G4})
     * and then turns them into two integer arrays for row and column
     * (e.g. col = {7,7,6,6} row = {4,5,4,5}).
     * An empty input terminates the program.
     * @throws InvalidInput Exception if input has wrong format.
     *             Check documentation to see the format rules.
     * @return returns printIndex which is an Indicator.
     *             printIndex is used to show the necessity of a print after a move. (if format is wrong for example)
     *             1 means there is no need for printing the board.
     *             2 means there were updates on the board which are not shown yet -> print.
     *             0 means the game was terminated.
     */
    public int playMove(Dice[] dices) {
        int[] numbers = new int[dices.length];
        Color[] colors = new Color[dices.length];
        for (int i = 0; i < dices.length; i++) {
            numbers[i] = dices[i].getNumber();
            colors[i] = dices[i].getColor();
        }
        Scanner playMove = new Scanner(System.in);
        int printIndex = 1;
        String input = playMove.nextLine();
        if (input.equals("")) {
            printIndex = 0;
            System.out.println("Terminating program...");
        } else {
            try {
                String[] inputArr = input.split(",");
                int[] col = new int[inputArr.length];
                int[] row = new int[inputArr.length];
                for (int i = 0; i < inputArr.length; i++) {
                    if (inputArr[i].length() != 2) {
                        throw new InvalidInput("InvalidInputException: Wrong input format. Try e.g. H4,H5,G5,G4");
                    } else {
                        col[i] = (int) inputArr[i].charAt(0) - ASCII_CODE_CONST_CHAR;
                        row[i] = inputArr[i].charAt(1) - ASCII_CODE_CONST_INT;
                        if (col[i] < 0 || col[i] > ASCII_THRESHOLD_COL || row[i] < 0 || row[i] > ASCII_THRESHOLD_ROW) {
                            throw new InvalidInput("InvalidInputException: Unknown Symbol. Try e.g. H4,H5,G5,G4");
                        }
                    }
                }
                if (board.validate(row, col, numbers, colors)) {
                    System.out.println("Turn is valid");
                    printIndex++;
                } else {
                    System.out.println("Turn is not valid");
                }
            } catch (InvalidInput e) {
                System.out.println(e);
                return printIndex;
            }
        }
        return printIndex;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
