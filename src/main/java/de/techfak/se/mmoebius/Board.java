package de.techfak.se.mmoebius;


//TODO class description
/**
 *
 */
public class Board {

    private static final int COLUMN_H = 7;
    private static final int ASCII_CODE_CONST_CHAR = 65;
    private static final int ASCII_CODE_CONST_INT = 49;
    private static final String SPACE = " ";

    /**
     * Board attributes:
     * floor: the actual board.
     * rowCount: the amount of rows.
     * colCount: the amount of columns.
     */
    public final Tile[][] floor;
    private int rowCount;
    private int colCount;

    /**
     * The Board constructor creates a 2-dim array of tiles (floor) from a given 2-dim
     * char array (map) and assigns colors depending on the characters in the char array.
     * Additionally edge fields are set with the missing of a specific neighbour field.
     * E.g. The field H1 has no field above, so hasUpNeighbour is set false.
     * @throws InvalidField Exception if the map contains a non valid character
     * @param map the map is a 2-dim char array which represents the map
     *              as characters depending on their color.
     */
    public Board(char[][] map) {
        rowCount = map.length;
        colCount = map[0].length;
        floor = new Tile[rowCount][colCount];
        for (int rowNr = 0; rowNr < rowCount; rowNr++) {
            String mapRow = new String(map[rowNr]);
            for (int colNr = 0; colNr < colCount; colNr++) {
                char mapCell = mapRow.charAt(colNr);
                Tile tile;
                if      (mapCell == 'b' || mapCell == 'B') { tile = new Tile(Color.BLUE, rowNr, colNr); }
                else if (mapCell == 'g' || mapCell == 'G') { tile = new Tile(Color.GREEN, rowNr, colNr); }
                else if (mapCell == 'o' || mapCell == 'O') { tile = new Tile(Color.ORANGE, rowNr, colNr); }
                else if (mapCell == 'r' || mapCell == 'R') { tile = new Tile(Color.RED, rowNr, colNr); }
                else if (mapCell == 'y' || mapCell == 'Y') { tile = new Tile(Color.YELLOW, rowNr, colNr); }
                else { throw new InvalidField("Invalid Field <101>"); }
                floor[rowNr][colNr] = tile;
                if (tile.getColNr() == 0) { tile.setHasLeftNeighbour(false); }
                if (tile.getColNr() == colCount - 1) { tile.setHasRightNeighbour(false); }
                if (tile.getRowNr() == 0) { tile.setHasUpNeighbour(false); }
                if (tile.getRowNr() == rowCount - 1) { tile.setHasDownNeighbour(false); }
                if (Character.isUpperCase(mapCell)) { tile.setCrossed(true); }
            }
        }
    }

    /**
     * the printBoard method prints the current board with their current color.
     * If a Tile is ticked it shows the character in upper Case.
     */
    public void printBoard() {
        System.out.println("  A B C D E F G H I J K L M N O");
        for  (int i = 0; i < floor.length; i++) {
            System.out.print((i + 1) + SPACE);
            for (int j = 0; j < floor[0].length; j++) {
                Tile tile = floor[i][j];
                Color color = tile.getColor();
                char current = '#';
                if (color.equals(Color.BLUE)) { current = 'b'; }
                if (color.equals(Color.ORANGE)) { current = 'o'; }
                if (color.equals(Color.GREEN))  { current = 'g'; }
                if (color.equals(Color.RED)) { current = 'r'; }
                if (color.equals(Color.YELLOW)) { current = 'y'; }
                if (tile.isCrossed()) { current = Character.toUpperCase(current); }
                System.out.print(current + SPACE);
            }
            System.out.println();
        }
    }

    /**
     * the updateTile method ticks the given tile and sets their neighbours to
     * have a crossed neighbour.
     * @param row the row of the tile to be ticked.
     * @param col the column of the tile to be ticked.
     */
    public void updateTile(int row, int col) {
        Tile tile = floor[row][col];
        tile.setCrossed(true);
        if (tile.hasLeftNeighbour()) { floor[row][col - 1].setHasCrossedNeighbour(true); }
        if (tile.hasRightNeighbour()) { floor[row][col + 1].setHasCrossedNeighbour(true); }
        if (tile.hasUpNeighbour()) { floor[row - 1][col].setHasCrossedNeighbour(true); }
        if (tile.hasDownNeighbour()) { floor[row + 1][col].setHasCrossedNeighbour(true); }
    }

    /**
     * The validate method consists of two sections. The method gets two arrays of
     * Integers which represent one play move consisting of several inputs.
     * In the first section the method checks if the first entered String
     * (e.g. H4 the tile in Column H and Row 4) is valid.
     * (String is converted to two integers before
     * see class Player, play move method to see how String is converted.)
     * The second section checks if every other Integer in the given input is valid.
     * The tester counts the valid tiles and decides if all Integers are valid.
     * If so, all tiles are updated and validate returns true.
     * To decide if an input is valid or not check Documentation.
     * @throws InvalidTurn The InvalidTurnException is thrown if the given Turn was invalid.
     * @param row The row is an array of integers consisting of all row numbers from the given input.
     * @param col The col is an array of integers consisting of all column numbers from the given input.
     * @return returns true if all inputs are valid and false if at least one input is not valid.
     */
    public boolean validate(int[] row, int[] col) {
        try {
            Tile firstTile = floor[row[0]][col[0]];
            int tester = 0;
            String first;
            String second;
            if (firstTile.isCrossed()) {
                first = Character.toString((char) col[0] + ASCII_CODE_CONST_CHAR);
                second = Character.toString((char) row[0] + ASCII_CODE_CONST_INT);
                throw new InvalidTurn(first + second + " is already ticked.");
            }
            if (firstTile.hasCrossedNeighbour() || firstTile.getColNr() == COLUMN_H) {
                System.out.print(Character.toString((char) col[0] + ASCII_CODE_CONST_CHAR));
                System.out.print(Character.toString((char) row[0] + ASCII_CODE_CONST_INT));
                System.out.println(" is valid, validating other inputs now...");
                Tile[] tileArr = new Tile[row.length];
                tileArr[0] = firstTile;
                tester++;
                for (int i = 1; i < row.length; i++) {
                    Tile tile = floor[row[i]][col[i]];
                    if (tile.isCrossed()) {
                        first = Character.toString((char) col[i] + ASCII_CODE_CONST_CHAR);
                        second = Character.toString((char) row[i] + ASCII_CODE_CONST_INT);
                        throw new InvalidTurn(first + second + " is already ticked");
                    }
                    for (int count = 0; count < tester; count++) {
                        if (tile.equals(tileArr[count])) {
                            throw new InvalidTurn("some crosses were equal");
                        }
                    }
                    for (int count = 0; count < tester; count++) {
                        if (firstTile.getColor().equals(tile.getColor()) && tile.isNeighbourTo(tileArr[count])) {
                            System.out.print(Character.toString((char) col[i] + ASCII_CODE_CONST_CHAR));
                            System.out.print(Character.toString((char) row[i] + ASCII_CODE_CONST_INT));
                            System.out.println(" is valid");
                            tileArr[i] = tile;
                            tester++;
                            break;
                        }
                    }
                }
                if (tester == row.length) {
                    System.out.println("All crosses valid");
                    for (int j = 0; j < tileArr.length; j++) {
                        updateTile(tileArr[j].getRowNr(), tileArr[j].getColNr());
                    }
                    return true;
                } else {
                    throw new InvalidTurn("Some crosses were not valid");
                }
            } else {
                first = Character.toString((char) col[0] + ASCII_CODE_CONST_CHAR);
                second = Character.toString((char) row[0] + ASCII_CODE_CONST_INT);
                throw new InvalidTurn(first + second + " does not have a crossed Neighbour & is not in Column H");
            }
        } catch (InvalidTurn e) {
            System.out.println(e);
            return false;
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }
}
