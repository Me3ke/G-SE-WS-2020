package de.techfak.se.mmoebius;

public class Tile {
    private Color color;
    private int rowNr;
    private int colNr;
    private boolean isCrossed;
    private boolean hasCrossedNeighbour;
    private boolean hasRightNeighbour;
    private boolean hasLeftNeighbour;
    private boolean hasUpNeighbour;
    private boolean hasDownNeighbour;

    /**
     *
     * @param color
     * @param rowNr
     * @param colNr
     */
    public Tile(Color color, int rowNr, int colNr) {
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
     *
     * @param other
     * @return
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

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public int getRowNr() {
        return rowNr;
    }

    /**
     *
     * @param rowNr
     */
    public void setRowNr(int rowNr) {
        this.rowNr = rowNr;
    }

    /**
     *
     * @return
     */
    public int getColNr() {
        return colNr;
    }

    /**
     *
     * @param colNr
     */
    public void setColNr(int colNr) {
        this.colNr = colNr;
    }

    /**
     *
     * @return
     */
    public boolean isCrossed() {
        return isCrossed;
    }

    /**
     *
     * @param crossed
     */
    public void setCrossed(boolean crossed) {
        isCrossed = crossed;
    }

    /**
     *
     * @return
     */
    public boolean hasCrossedNeighbour() {
        return hasCrossedNeighbour;
    }

    /**
     *
     * @param hasCrossedNeighbour
     */
    public void setHasCrossedNeighbour(boolean hasCrossedNeighbour) {
        this.hasCrossedNeighbour = hasCrossedNeighbour;
    }

    /**
     *
     * @return
     */
    public boolean hasRightNeighbour() {
        return hasRightNeighbour;
    }

    /**
     *
     * @param hasRightNeighbour
     */
    public void setHasRightNeighbour(boolean hasRightNeighbour) {
        this.hasRightNeighbour = hasRightNeighbour;
    }

    /**
     *
     * @return
     */
    public boolean hasLeftNeighbour() {
        return hasLeftNeighbour;
    }

    /**
     *
     * @param hasLeftNeighbour
     */
    public void setHasLeftNeighbour(boolean hasLeftNeighbour) {
        this.hasLeftNeighbour = hasLeftNeighbour;
    }

    /**
     *
     * @return
     */
    public boolean hasUpNeighbour() {
        return hasUpNeighbour;
    }

    /**
     *
     * @param hasUpNeighbour
     */
    public void setHasUpNeighbour(boolean hasUpNeighbour) {
        this.hasUpNeighbour = hasUpNeighbour;
    }

    /**
     *
     * @return
     */
    public boolean hasDownNeighbour() {
        return hasDownNeighbour;
    }

    /**
     *
     * @param hasDownNeighbour
     */
    public void setHasDownNeighbour(boolean hasDownNeighbour) {
        this.hasDownNeighbour = hasDownNeighbour;
    }
}
