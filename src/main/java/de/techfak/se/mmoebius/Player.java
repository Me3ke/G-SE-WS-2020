package de.techfak.se.mmoebius;

import java.util.Scanner;

public class Player {
    int playerNumber;
    Board board;

    public Player(int playerNumber,Board board) {
        this.playerNumber = playerNumber;
        this.board = board;
    }

    public boolean playmove() {
        Scanner playMove = new Scanner(System.in);
        String input = playMove.next();
        System.out.println(input);
        String[] inputArr = input.split(",");
        int first,second;
        for(int i = 0; i < inputArr.length; i++) {
            first = (int) inputArr[i].charAt(0) - 65;
            second = inputArr[i].charAt(1) - 49;
            System.out.println(first + " " + second);
            if(inputArr[i].length() != 2){
                System.out.println("Ungültige Eingabe");
            }
            if (first < 0 || first > 14 || second < 0 || second > 6) {
                System.out.println("Ungültige Eingabe");
            }
            board.update(first,second);

        }
        return false;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
