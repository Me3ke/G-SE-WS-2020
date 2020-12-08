package de.techfak.se.mmoebius;

public class Tile {
    public Color color;
    public int rowNr, colNr;
    public boolean isCrossed;
    public boolean hasCrossedNeighbour;
    public boolean hasRightNeighbour;
    public boolean hasLeftNeighbour;
    public boolean hasUpNeighbour;
    public boolean hasDownNeighbour;

    public Tile(Color color,int rowNr,int colNr) {
        this.color = color;
        this.rowNr = rowNr;
        this.colNr = colNr;
        isCrossed = false;
        hasCrossedNeighbour = false;
    }

    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
    }

    public void setHasCrossedNeighbour(boolean hasCrossedNeighbour) {
        this.hasCrossedNeighbour = hasCrossedNeighbour;
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
