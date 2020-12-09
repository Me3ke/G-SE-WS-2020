package de.techfak.se.mmoebius;

import java.util.Scanner;

public class Player {
    int playerNumber;
    Board board;
    int points;

    public Player(int playerNumber,Board board) {
        this.playerNumber = playerNumber;
        this.board = board;
        points = 0;
    }

    public int playMove() {
        Scanner playMove = new Scanner(System.in);
        /* printIndex is used to show the necessity of a print after a move. (if format is wrong for example)
            1 means there is no need for printing the board
            2 means there were updates on the board which are not shown yet -> print
            0 means the game was terminated */
        int printIndex = 1;
        String input = playMove.nextLine();
        if(input.equals("")){
            printIndex = 0;
            System.out.println("Terminating program...");
        } else {
            try {
                String[] inputArr = input.split(",");
                int[] col = new int[inputArr.length];
                int[] row = new int[inputArr.length];
                for (int i = 0; i < inputArr.length; i++) {
                    if (inputArr[i].length() != 2) {
                        throw new InvalidInput("InvalidInputException: Wrong input format. Try something like H4,H5,G5,G4");
                    } else {
                        col[i] = (int) inputArr[i].charAt(0) - 65;
                        row[i] = inputArr[i].charAt(1) - 49;
                        if (col[i] < 0 || col[i] > 14 || row[i] < 0 || row[i] > 6) {
                            throw new InvalidInput("InvalidInputException: Unknown Symbol. Try something like H4,H5,G5,G4");
                        }
                    }
                }
                if (board.validate(row, col)) {
                    System.out.println("Turn is valid");
                    printIndex++;
                } else {
                    System.out.println("Turn is not valid");
                }
            }catch(InvalidInput e) {
                System.out.println(e);
                return printIndex;
            }
        }
        return printIndex;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
