package de.techfak.se.mmoebius;

public class Score {
    Player player;

    public Score(Player player) {
        this.player = player;
    }

    private int colorCount(Board board, Color color){
        int counter = 0;
        for(int i = 0; i < board.colCount; i++){
            for(int j = 0; j < board.rowCount; j++){
                if(board.floor[j][i].getColor().equals(color)){
                    counter++;
                }
            }
        }
        return counter;
    }

    private int colorCountCrossed(Board board, Color color) {
        int counter = 0;
        for(int i = 0; i < board.colCount; i++){
            for(int j = 0; j < board.rowCount; j++){
                if(board.floor[j][i].getColor().equals(color) && board.floor[j][i].isCrossed){
                    counter++;
                }
            }
        }
        return counter;
    }

    public int calculatePoints(Board board) {
        int points = 0;
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
            }
            counter = 0;
        }
        if(colorCount(board,Color.RED) == colorCountCrossed(board,Color.RED)) points += 5;
        if(colorCount(board,Color.BLUE) == colorCountCrossed(board,Color.BLUE)) points += 5;
        if(colorCount(board,Color.GREEN) == colorCountCrossed(board,Color.GREEN)) points += 5;
        if(colorCount(board,Color.ORANGE) == colorCountCrossed(board,Color.ORANGE)) points += 5;
        if(colorCount(board,Color.YELLOW) == colorCountCrossed(board,Color.YELLOW)) points += 5;
        return points;
    }

    public boolean testIfFinished(Board board) {
        int counter = 0;
        if(colorCount(board,Color.RED) == colorCountCrossed(board,Color.RED)) counter++;
        if(colorCount(board,Color.BLUE) == colorCountCrossed(board,Color.BLUE)) counter++;
        if(colorCount(board,Color.GREEN) == colorCountCrossed(board,Color.GREEN)) counter++;
        if(colorCount(board,Color.ORANGE) == colorCountCrossed(board,Color.ORANGE)) counter++;
        if(colorCount(board,Color.YELLOW) == colorCountCrossed(board,Color.YELLOW)) counter++;
        if(counter >= 2) {
            return true;
        }
        return false;
    }

    public void printPoints() {
        System.out.print("Player" + player.getPlayerNumber());
        System.out.println(" your current Score is: " + player.getPoints());
    }
}


