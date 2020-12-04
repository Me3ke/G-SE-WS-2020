package de.techfak.se.mmoebius;

public class Tile {
    public Color color;
    public int rowNr, colNr;

    public Tile(Color color,int rowNr,int colNr) {
        this.color = color;
        this.rowNr = rowNr;
        this.colNr = colNr;
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
