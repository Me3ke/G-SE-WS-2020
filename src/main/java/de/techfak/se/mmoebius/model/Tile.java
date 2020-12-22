package de.techfak.se.mmoebius.model;

import javafx.scene.paint.Color;

/**
 *
 */
public class Tile {

    /**
     * Tile attributes:
     * color: the color of the tile.
     * rowNr: the row number of the tile.
     * colNr: the column number of the tile.
     * isCrossed: if the tile is crossed.
     * hasCrossedNeighbour: if the tile has a crossed neighbour.
     * hasRightNeighbour: if the tile has a right neighbour.
     * hasLeftNeighbour: if the tile has a left neighbour.
     * hasUpNeighbour: if the tile has a up neighbour.
     * hasDownNeighbour: if the tile has a down neighbour.
     */
    private javafx.scene.paint.Color color;
    private int rowNr;
    private int colNr;
    private boolean isCrossed;
    private boolean hasCrossedNeighbour;
    private boolean hasRightNeighbour;
    private boolean hasLeftNeighbour;
    private boolean hasUpNeighbour;
    private boolean hasDownNeighbour;

    /**
     * Constructor for a tile.
     * In the beginning a Tile is not crossed, has no crossed neighbour
     * and has every neighbour.
     * @param color the color of the tile.
     * @param rowNr the row number of the tile.
     * @param colNr the column number of the tile.
     */
    public Tile(javafx.scene.paint.Color color, int rowNr, int colNr) {
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

    /**
     * The isNeighbourTo method tests if the tile is a neighbour to another tile.
     * @param other the other tile.
     * @return returns true if its a neighbour field and false if not.
     */
    public boolean isNeighbourTo(Tile other) {
        if (other.hasDownNeighbour) {
            if (this.getColNr() == other.getColNr()  && this.getRowNr() == other.getRowNr() + 1) { return true; }
        }
        if (other.hasUpNeighbour) {
            if (this.getColNr() == other.getColNr()  && this.getRowNr() == other.getRowNr() - 1) { return true; }
        }
        if (other.hasLeftNeighbour) {
            if (this.getColNr() == other.getColNr() - 1 && this.getRowNr() == other.getRowNr()) { return true; }
        }
        if (other.hasRightNeighbour) {
            return this.getColNr() == other.getColNr() + 1 && this.getRowNr() == other.getRowNr();
        }
        return false;
    }

    public javafx.scene.paint.Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getRowNr() {
        return rowNr;
    }

    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    public int getColNr() {
        return colNr;
    }

    public void setColNr(int colNr) {
        this.colNr = colNr;
    }

    public boolean isCrossed() {
        return isCrossed;
    }

    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
    }

    public boolean hasCrossedNeighbour() {
        return hasCrossedNeighbour;
    }

    public void setHasCrossedNeighbour(boolean hasCrossedNeighbour) {
        this.hasCrossedNeighbour = hasCrossedNeighbour;
    }

    public boolean hasRightNeighbour() {
        return hasRightNeighbour;
    }

    public void setHasRightNeighbour(boolean hasRightNeighbour) {
        this.hasRightNeighbour = hasRightNeighbour;
    }

    public boolean hasLeftNeighbour() {
        return hasLeftNeighbour;
    }

    public void setHasLeftNeighbour(boolean hasLeftNeighbour) {
        this.hasLeftNeighbour = hasLeftNeighbour;
    }

    public boolean hasUpNeighbour() {
        return hasUpNeighbour;
    }

    public void setHasUpNeighbour(boolean hasUpNeighbour) {
        this.hasUpNeighbour = hasUpNeighbour;
    }

    public boolean hasDownNeighbour() {
        return hasDownNeighbour;
    }

    public void setHasDownNeighbour(boolean hasDownNeighbour) {
        this.hasDownNeighbour = hasDownNeighbour;
    }
}
