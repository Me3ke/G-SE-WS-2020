package de.techfak.se.mmoebius;

import java.util.Scanner;

public class Player {
    int playerNumber;
    Board board;

    public Player(int playerNumber,Board board) {
        this.playerNumber = playerNumber;
        this.board = board;
    }

    public int playMove() {
        Scanner playMove = new Scanner(System.in);
        int first, second;
        /* printIndex is used to show the necessity of a print after a move. (if format is wrong for example)
            1 means there is no need for printing the board
            <= 2 means there were updates on the board which are not shown yet -> print
            0 means the game was terminated */
        int printIndex = 1;
        String input = playMove.nextLine();
        if(input.equals("")){
            printIndex = 0;
            System.out.println("Terminating program...");
        } else {
            String[] inputArr = input.split(",");
            for (int i = 0; i < inputArr.length; i++) {
                if (inputArr[i].length() != 2) {
                    System.out.println("Wrong input format. Try e.g. H4,H5,G5,G4");
                    break;
                } else {
                    first = (int) inputArr[i].charAt(0) - 65;
                    second = inputArr[i].charAt(1) - 49;
                    if (first < 0 || first > 14 || second < 0 || second > 6) {
                        System.out.println("Unknown Symbol. Try e.g. H4,H5,G5,G4");
                        break;
                    }
                    printIndex += board.update(first,second);
                }
            }
        }
        return printIndex;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
