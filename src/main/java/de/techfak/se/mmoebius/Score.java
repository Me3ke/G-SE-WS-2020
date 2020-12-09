package de.techfak.se.mmoebius;

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

    private Player player;

    /**
     *
     * @param player
     */
    public Score(Player player) {
        this.player = player;
    }

    /**
     *
     * @param board
     * @param color
     * @return
     */
    private int colorCount(Board board, Color color) {
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
     *
     * @param board
     * @param color
     * @return
     */
    private int colorCountCrossed(Board board, Color color) {
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
     *
     * @param board
     * @return
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
                if (i == COL_A || i == COL_O) { points += POINTS_FOR_AO; }
                if (i >= COL_B && i <= COL_D || i >= COL_L && i <= COL_N) { points += POINTS_FOR_BCD_LMN; }
                if (i >= COL_E && i <= COL_G || i >= COL_I && i <= COL_K) { points += POINTS_FOR_EFG_IJK; }
                if (i == COL_H) { points += POINTS_FOR_H; }
            }
            counter = 0;
        }
        if (colorCount(board, Color.RED) == colorCountCrossed(board, Color.RED)) { points += POINTS_FOR_COLOR; }
        if (colorCount(board, Color.BLUE) == colorCountCrossed(board, Color.BLUE)) { points += POINTS_FOR_COLOR; }
        if (colorCount(board, Color.GREEN) == colorCountCrossed(board, Color.GREEN)) { points += POINTS_FOR_COLOR; }
        if (colorCount(board, Color.ORANGE) == colorCountCrossed(board, Color.ORANGE)) { points += POINTS_FOR_COLOR; }
        if (colorCount(board, Color.YELLOW) == colorCountCrossed(board, Color.YELLOW)) { points += POINTS_FOR_COLOR; }
        return points;
    }

    /**
     *
     * @param board
     * @return
     */
    public boolean testIfFinished(Board board) {
        int counter = 0;
        if (colorCount(board, Color.RED) == colorCountCrossed(board, Color.RED)) { counter++; }
        if (colorCount(board, Color.BLUE) == colorCountCrossed(board, Color.BLUE)) { counter++; }
        if (colorCount(board, Color.GREEN) == colorCountCrossed(board, Color.GREEN)) { counter++; }
        if (colorCount(board, Color.ORANGE) == colorCountCrossed(board, Color.ORANGE)) { counter++; }
        if (colorCount(board, Color.YELLOW) == colorCountCrossed(board, Color.YELLOW)) { counter++; }
        if (counter >= WIN_THRESHOLD) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void printPoints() {
        System.out.print("Player" + player.getPlayerNumber());
        System.out.println(" your current Score is: " + player.getPoints());
    }

    /**
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}


