package de.techfak.se.mmoebius.model;

import javafx.scene.paint.Color;

/**
 *
 */
public class Score {
    private static final int WIN_THRESHOLD = 2;
    private static final int POINTS_FOR_COLOR = 5;
    private static final int ROW_SUM = 7;
    private static final int[] POINT_ARR = {5, 3, 3, 3, 2, 2, 2, 1, 2, 2, 2, 3, 3, 3, 5};

    /**
     * Score attributes:
     * player: is the player which the score belongs to.
     */
    private Player player;

    public Score(Player player) {
        this.player = player;
    }

    /**
     * The colorCount method counts the amount of tiles with the specific color given.
     * @param board the board on which it should be counted.
     * @param color the color of tiles to be counted.
     * @return returns counter, the amount of tiles with the color.
     */
    public int colorCount(Board board, javafx.scene.paint.Color color) {
        int counter = 0;
        for (int i = 0; i < board.getColCount(); i++) {
            for (int j = 0; j < board.getRowCount(); j++) {
                if (board.floor[j][i].getColor().equals(color)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * The colorCountCrossed method counts the amount of ticked tiles with the specific color given.
     * @param board the board on which it should be counted.
     * @param color the color of tiles to be counted.
     * @return returns counter, the amount of ticked tiles with the color.
     */
    public int colorCountCrossed(Board board, javafx.scene.paint.Color color) {
        int counter = 0;
        for (int i = 0; i < board.getColCount(); i++) {
            for (int j = 0; j < board.getRowCount(); j++) {
                if (board.floor[j][i].getColor().equals(color) && board.floor[j][i].isCrossed()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * The calculatePoints method inspects the board and counts all completely ticked columns and
     * distributes points for it. Additionally uses colorCount and colorCountCrossed to calculate
     * points depending if all tiles of one color are ticked (->documentation)
     * @param board the board on which the points should be calculated.
     * @return returns the points calculated.
     */
    public int calculatePoints(Board board) {
        int points = 0;
        int counter = 0;
        for (int i = 0; i < board.getColCount(); i++) {
            for (int j = 0; j < board.getRowCount(); j++) {
                if (board.floor[j][i].isCrossed()) {
                    counter++;
                }
            }
            if (counter == ROW_SUM) {
                points += POINT_ARR[i];
            }
            counter = 0;
        }
        if (colorCount(board, Color.RED) == colorCountCrossed(board, Color.RED)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, Color.BLUE) == colorCountCrossed(board, Color.BLUE)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, Color.GREEN) == colorCountCrossed(board, Color.GREEN)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, Color.ORANGE) == colorCountCrossed(board, Color.ORANGE)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, Color.YELLOW) == colorCountCrossed(board, Color.YELLOW)) {
            points += POINTS_FOR_COLOR;
        }
        return points;
    }

    /**
     *
     * @param board
     * @return
     */
    public int[] getCompleteCols(Board board) {
        int[] completeCols = new int[board.getColCount()];
        int completeColCount = 0;
        int counter = 0;
        for (int i = 0; i < board.getColCount(); i++) {
            for (int j = 0; j < board.getRowCount(); j++) {
                if (board.floor[j][i].isCrossed()) {
                    counter++;
                }
            }
            if (counter == ROW_SUM) {
                completeCols[completeColCount] = i + 1;
                completeColCount++;
            } else {
                counter = 0;
            }

        }
        return completeCols;
    }

    /**
     * The testIfFinished method, tests if two (-> documentation) colors are completely ticked.
     * (winning condition)
     * @param board the board on which it should be tested.
     * @return returns true if at least two colors are completely ticked.
     */
    public boolean testIfFinished(Board board) {
        int counter = 0;
        if (colorCount(board, Color.RED) == colorCountCrossed(board, Color.RED)) {
            counter++;
        }
        if (colorCount(board, Color.BLUE) == colorCountCrossed(board, Color.BLUE)) {
            counter++;
        }
        if (colorCount(board, Color.GREEN) == colorCountCrossed(board, Color.GREEN)) {
            counter++;
        }
        if (colorCount(board, Color.ORANGE) == colorCountCrossed(board, Color.ORANGE)) {
            counter++;
        }
        if (colorCount(board, Color.YELLOW) == colorCountCrossed(board, Color.YELLOW)) {
            counter++;
        }
        return counter >= WIN_THRESHOLD;

    }

    /**
     * prints the points of all players.
     */
    public void printPoints() {
        System.out.print("Player" + player.getPlayerNumber());
        System.out.println(" your current Score is: " + player.getPoints());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}


