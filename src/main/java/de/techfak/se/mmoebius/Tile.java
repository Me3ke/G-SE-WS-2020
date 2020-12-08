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
        hasRightNeighbour = true;
        hasLeftNeighbour = true;
        hasUpNeighbour = true;
        hasDownNeighbour = true;
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

    public boolean isNeighbourTo(Tile other){
        if(other.hasDownNeighbour) {
            if (this.getColNr() == other.getColNr()  && this.getRowNr() == other.getRowNr() + 1) return true;
        }
        if(other.hasUpNeighbour) {
            if (this.getColNr() == other.getColNr()  && this.getRowNr() == other.getRowNr() - 1) return true;
        }
        if(other.hasLeftNeighbour) {
            if (this.getColNr() == other.getColNr() - 1 && this.getRowNr() == other.getRowNr()) return true;
        }
        if(other.hasRightNeighbour) {
            return this.getColNr() == other.getColNr() + 1 && this.getRowNr() == other.getRowNr() ;
        }
       return false;
    }
}
