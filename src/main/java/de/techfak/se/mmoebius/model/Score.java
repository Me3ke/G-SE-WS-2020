package de.techfak.se.mmoebius.model;

/**
 *
 */
public class Score {
    private static final int WIN_THRESHOLD = 2;
    private static final int POINTS_FOR_COLOR = 5;
    private static final int POINTS_FOR_AO = 5;
    private static final int POINTS_FOR_BCD_LMN = 3;
    private static final int POINTS_FOR_EFG_IJK = 2;
    private static final int POINTS_FOR_H = 1;
    private static final int ROW_SUM = 7;
    private static final int COL_A = 0;
    private static final int COL_O = 15;
    private static final int COL_B = 1;
    private static final int COL_D = 3;
    private static final int COL_L = 12;
    private static final int COL_N = 14;
    private static final int COL_E = 4;
    private static final int COL_G = 6;
    private static final int COL_I = 8;
    private static final int COL_K = 11;
    private static final int COL_H = 7;

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
                if (i == COL_A || i == COL_O) {
                    points += POINTS_FOR_AO;
                }
                if (i >= COL_B && i <= COL_D || i >= COL_L && i <= COL_N) {
                    points += POINTS_FOR_BCD_LMN;
                }
                if (i >= COL_E && i <= COL_G || i >= COL_I && i <= COL_K) {
                    points += POINTS_FOR_EFG_IJK;
                }
                if (i == COL_H) {
                    points += POINTS_FOR_H;
                }
            }
            counter = 0;
        }
        if (colorCount(board, javafx.scene.paint.Color.RED) == colorCountCrossed(board, javafx.scene.paint.Color.RED)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, javafx.scene.paint.Color.BLUE) == colorCountCrossed(board, javafx.scene.paint.Color.BLUE)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, javafx.scene.paint.Color.GREEN) == colorCountCrossed(board, javafx.scene.paint.Color.GREEN)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, javafx.scene.paint.Color.ORANGE) == colorCountCrossed(board, javafx.scene.paint.Color.ORANGE)) {
            points += POINTS_FOR_COLOR;
        }
        if (colorCount(board, javafx.scene.paint.Color.YELLOW) == colorCountCrossed(board, javafx.scene.paint.Color.YELLOW)) {
            points += POINTS_FOR_COLOR;
        }
        return points;
    }

    /**
     * The testIfFinished method, tests if two (-> documentation) colors are completely ticked.
     * (winning condition)
     * @param board the board on which it should be tested.
     * @return returns true if at least two colors are completely ticked.
     */
    public boolean testIfFinished(Board board) {
        int counter = 0;
        if (colorCount(board, javafx.scene.paint.Color.RED) == colorCountCrossed(board, javafx.scene.paint.Color.RED)) {
            counter++;
        }
        if (colorCount(board, javafx.scene.paint.Color.BLUE) == colorCountCrossed(board, javafx.scene.paint.Color.BLUE)) {
            counter++;
        }
        if (colorCount(board, javafx.scene.paint.Color.GREEN) == colorCountCrossed(board, javafx.scene.paint.Color.GREEN)) {
            counter++;
        }
        if (colorCount(board, javafx.scene.paint.Color.ORANGE) == colorCountCrossed(board, javafx.scene.paint.Color.ORANGE)) {
            counter++;
        }
        if (colorCount(board, javafx.scene.paint.Color.YELLOW) == colorCountCrossed(board, javafx.scene.paint.Color.YELLOW)) {
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


