package de.techfak.se.mmoebius;

import java.util.Scanner;

public class Player {
    int playerNumber;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean playmove() {
        Scanner playMove = new Scanner(System.in);
        String input = playMove.next();
        System.out.println(input);
        return false;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
