package de.techfak.se.mmoebius;

import java.util.Scanner;

public class Player {

    private static final int ASCII_CODE_CONST_CHAR = 65;
    private static final int ASCII_CODE_CONST_INT = 49;
    private static final int ASCII_THRESHOLD_COL = 14;
    private static final int ASCII_THRESHOLD_ROW = 6;

    private int playerNumber;
    private Board board;
    private int points;


    /**
     *
     * @param playerNumber
     * @param board
     */
    public Player(int playerNumber, Board board) {
        this.playerNumber = playerNumber;
        this.board = board;
        points = 0;
    }

    /**
     *
     * @return
     */
    public int playMove() {
        Scanner playMove = new Scanner(System.in);
        /* printIndex is used to show the necessity of a print after a move. (if format is wrong for example)
            1 means there is no need for printing the board
            2 means there were updates on the board which are not shown yet -> print
            0 means the game was terminated */
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
                if (board.validate(row, col)) {
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

    /**
     *
     * @return
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     *
     * @param playerNumber
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     *
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
