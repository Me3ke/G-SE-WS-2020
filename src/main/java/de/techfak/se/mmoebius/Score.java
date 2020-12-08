package de.techfak.se.mmoebius;

public class Score {
    int points;
    Player player;

    public Score(Player player) {
        points = 0;
        this.player = player;
    }


    public void calculatePoints(Board board) {
        int counter = 0;
        for(int i = 0; i < board.colCount; i++) {
            for (int j = 0; j < board.rowCount; j++) {
                if (board.floor[j][i].isCrossed) {
                    counter++;
                }
            }
            if (counter == 7) {
                if(i == 0 || i == 15) {points += 5;}
                if(i >= 1 && i <= 3 || i >= 12 && i <= 14) {points += 3;}
                if(i >= 4 && i <= 6 || i >= 8 && i <= 11) {points += 2;}
                if(i == 7) {points += 1;}
                counter = 0;
            }
        }
    }
}


