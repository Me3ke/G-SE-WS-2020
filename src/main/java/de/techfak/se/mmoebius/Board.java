package de.techfak.se.mmoebius;

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
                Tile tile = null;
                if(mapCell == 'b') tile = new Tile(Color.BLUE,rowNr,colNr);
                else if(mapCell == 'g') tile = new Tile(Color.GREEN,rowNr,colNr);
                else if(mapCell == 'o') tile = new Tile(Color.ORANGE,rowNr,colNr);
                else if(mapCell == 'r') tile = new Tile(Color.RED,rowNr,colNr);
                else if(mapCell == 'y') tile = new Tile(Color.YELLOW,rowNr,colNr);
                else System.out.println("Unexpected Source file Error"); // Exception here Us1
                floor[rowNr][colNr] = tile;
            }
        }
    }

    public void printBoard() {
        System.out.println("  A B C D E F G H I J K L M N O");
        for(int i = 0; i< floor.length; i++) {
            System.out.print(i+ " ");
            for (int j = 0; j < floor[0].length; j++) {
                Color color = floor[i][j].getColor();
                if(color.equals(Color.BLUE)) System.out.print("b ");
                if(color.equals(Color.ORANGE)) System.out.print("o ");
                if(color.equals(Color.GREEN)) System.out.print("g ");
                if(color.equals(Color.RED)) System.out.print("r ");
                if(color.equals(Color.YELLOW)) System.out.print("y ");
            }
            System.out.println();
        }
    }

}
