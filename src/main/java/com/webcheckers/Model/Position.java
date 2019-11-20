package com.webcheckers.Model;

/**
 * Represents a location on a 2D board
 */
public class Position {
    private int row;
    private int cell;

    /**
     * Constructor
     * @param row row of position
     * @param cell column of position
     */
    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    /**
     * Get the row of this position
     * @return an Integer
     */
    public Integer getRow(){
        return row;
    }

    /**
     * Get the column of this position
     * @return an Integer
     */
    public Integer getCell(){
        return cell;
    }

    /**
     * Check that this position is equal to another
     * @param other the object we are comparing
     * @return true if other is an Integer and has equal values in its fields as this position, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Position)){
            return false;
        }
        Position o = (Position)other;
        return this.getRow().equals(o.getRow()) && this.getCell().equals(getCell());
    }

    /**
     * Encode this position as a string
     * @return a string following the format of "[{row #},{col #}]"
     */
    @Override
    public String toString(){
        return "["+row+","+cell+"]";
    }
}
