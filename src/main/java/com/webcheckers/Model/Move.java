package com.webcheckers.Model;

/**
 * Represents a move of a piece on a checker board from a start position to another
 */
public class Move {
    Position start;
    Position end;

    /**
     * Constructor
     * @param start the position on a board that a piece starts in
     * @param end the position on a board that a piece ends in
     */
    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    /**
     * Get the start position
     * @return a Position
     */
    public Position getStart(){
        return start;
    }

    /**
     * Get the end position
     * @return a Position
     */
    public Position getEnd(){
        return end;
    }

    /**
     * We define distance here as the number of spaces a piece moves diagonally. For example, if a piece moves from a
     * position that is up one space and to the right by one space, we define the distance of that move to be 1. If a
     * piece jumps over that space, the distance is 2. If the move is not diagonal, this method returns 0.
     * @return an int: 0, 1, or 2
     */
    public int getDistance(){
        int yDist = 0;
        int xDist = 0;
        int dist = 0;
        yDist = end.getRow() - start.getRow();
        xDist = end.getCell() - start.getCell();

        /*
        if(xDist != yDist){
            dist = 0;
        }
        else{
            dist = xDist;
        }*/
        if(Math.abs(xDist) == Math.abs(yDist)){
            dist = yDist;
        }

        return dist;
    }

    /**
     * Get the position of a space jumped over in a distance 2 jump in this move. Returns null if this move has a distance
     * unequal to 3
     * @return a Position
     */
    public Position getMidPoint(){
        if(Math.abs(getDistance()) != 2){
            return null;
        }
        int mrow = getStart().getRow() + (getEnd().getRow() - getStart().getRow())/2;
        int mcell = getStart().getCell() + (getEnd().getCell() - getStart().getCell())/2;
        return new Position(mrow, mcell);
    }

    /**
     * Encode this object as a string of format "start: {start position}\nend: {end position}"
     * @return a String
     */
    @Override
    public String toString(){
        return "start:" + start + "\nend:" + end;
    }
}
