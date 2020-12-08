package de.techfak.se.mmoebius;

public class Tile {
    public Color color;
    public int rowNr, colNr;
    public boolean isCrossed;

    public Tile(Color color,int rowNr,int colNr) {
        this.color = color;
        this.rowNr = rowNr;
        this.colNr = colNr;
        isCrossed = false;
    }

    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
    }

    public Color getColor() {
        return color;
    }

    public int getRowNr() {
        return rowNr;
    }

    public int getColNr() {
        return colNr;
    }
}
