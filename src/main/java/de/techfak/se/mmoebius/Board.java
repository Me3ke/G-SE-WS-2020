package de.techfak.se.mmoebius;


//TODO show tile which is not valid
public class Board {
    public int rowCount,colCount;
    public final Tile[][] floor;

    public Board(char[][] map) {
        rowCount = map.length;
        colCount = map[0].length;
        floor = new Tile [rowCount][colCount];
        for(int rowNr = 0; rowNr < rowCount; rowNr++){
            String mapRow = new String(map[rowNr]);
            for(int colNr = 0;colNr < colCount; colNr++){
                char mapCell = mapRow.charAt(colNr);
                Tile tile;
                if(mapCell == 'b' || mapCell == 'B') tile = new Tile(Color.BLUE,rowNr,colNr);
                else if(mapCell == 'g'|| mapCell == 'G') tile = new Tile(Color.GREEN,rowNr,colNr);
                else if(mapCell == 'o'|| mapCell == 'O') tile = new Tile(Color.ORANGE,rowNr,colNr);
                else if(mapCell == 'r'|| mapCell == 'R') tile = new Tile(Color.RED,rowNr,colNr);
                else if(mapCell == 'y'|| mapCell == 'Y') tile = new Tile(Color.YELLOW,rowNr,colNr);
                else throw new InvalidField("Invalid Field <101>");
                floor[rowNr][colNr] = tile;
                if(tile.getColNr() == 0) tile.hasLeftNeighbour = false;
                if(tile.getColNr() == colCount-1) tile.hasRightNeighbour = false;
                if(tile.getRowNr() == 0) tile.hasUpNeighbour = false;
                if(tile.getRowNr() == rowCount-1) tile.hasDownNeighbour = false;
            }
        }
    }

    public void printBoard() {
        System.out.println("  A B C D E F G H I J K L M N O");
        for(int i = 0; i< floor.length; i++) {
            System.out.print((i+1)+ " ");
            for (int j = 0; j < floor[0].length; j++) {
                Tile tile = floor[i][j];
                Color color = tile.getColor();
                char current = '#';
                if(color.equals(Color.BLUE)) current = 'b';
                if(color.equals(Color.ORANGE)) current = 'g';
                if(color.equals(Color.GREEN)) current = 'o';
                if(color.equals(Color.RED)) current = 'r';
                if(color.equals(Color.YELLOW)) current = 'y';
                if(tile.isCrossed) current = Character.toUpperCase(current);
                System.out.print(current + " ");
            }
            System.out.println();
        }
    }

    public void updateTile(int row, int col) {
        Tile tile = floor[row][col];
        tile.setCrossed(true);
        if(tile.hasLeftNeighbour) floor[row][col-1].setHasCrossedNeighbour(true);
        if(tile.hasRightNeighbour) floor[row][col+1].setHasCrossedNeighbour(true);
        if(tile.hasUpNeighbour) floor[row-1][col].setHasCrossedNeighbour(true);
        if(tile.hasDownNeighbour) floor[row+1][col].setHasCrossedNeighbour(true);
    }

    public boolean validate(int[] row, int[] col) {
        try {
            Tile firstTile = floor[row[0]][col[0]];
            int tester = 0;
            String c1,c2;
            if (firstTile.isCrossed) {
                c1 = Character.toString((char) col[0] + 65);
                c2 = Character.toString((char) row[0] + 49);
                throw new InvalidTurn("InvalidTurnException: "+c1+c2+" is already ticked");
            }
            if (firstTile.hasCrossedNeighbour || firstTile.getColNr() == 7) {
                System.out.print(Character.toString((char) col[0] + 65));
                System.out.print(Character.toString((char) row[0] + 49));
                System.out.println(" is in Column H or has crossed Neighbour first cross is valid, validating other inputs now...");
                Tile[] tileArr = new Tile[row.length];
                tileArr[0] = firstTile;
                tester++;
                for (int i = 1; i < row.length; i++) {
                    Tile tile = floor[row[i]][col[i]];
                    if (tile.isCrossed) {
                        c1 = Character.toString((char) col[i] + 65);
                        c2 = Character.toString((char) row[i] + 49);
                        throw new InvalidTurn("InvalidTurnException: "+c1+c2+" is already ticked");
                    }
                    for (int count = 0; count < tester; count++) {
                        if (firstTile.getColor().equals(tile.getColor()) && tile.isNeighbourTo(tileArr[count])) {
                            System.out.print(Character.toString((char) col[i] + 65));
                            System.out.print(Character.toString((char) row[i] + 49));
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
                    throw new InvalidTurn("InvalidTurnException: Some crosses were not valid");
                }
            } else {
                c1 = Character.toString((char) col[0] + 65);
                c2 = Character.toString((char) row[0] + 49);
                throw new InvalidTurn("InvalidTurnException: "+c1+c2+" does not have a crossed Neighbour and is not in Column H");
            }
        } catch (InvalidTurn e) {
            System.out.println(e);
            return false;
        }
    }
}
