package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Row object
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class Row implements Cloneable{

    private int index;
    private ArrayList<Space> spaces;

    // constructor for row
    public Row(int index, ArrayList list) {
        this.index = index;
        spaces = list;
    }

    /**
     * @param valid, if the space is valid
     * @param index index of the space
     * @param color color of the piece
     * adds space with a piece
     */
    public void addSpacePiece(boolean valid, int index, Color color){
        Piece piece = new Piece(color);
        if((color == Color.RED && this.index == 7) || (color == Color.WHITE && this.index == 0)){
            piece.kingMe();
        }
        Space space = new Space(valid, index, piece);
        spaces.add(space);
    }

    /**
     *
     * @param valid if the space is valid
     * @param index index of the space
     * adds an empty space
     */
    public void addEmptySpace(boolean valid, int index){
        Space space = new Space(valid, index, null);
        spaces.add(space);
    }

    /**
     * 
     * @param flip if the board if flipped or not
     * @return index of row
     */
    public int getIndex(boolean flip){
        if(flip){
            return 8-index;
        }
        return index;
    }


    /**
     * 
     * @return index of row
     */
    public int getIndex(){
        return index;
    }


    public void setIndex(int cell, Space space){
        spaces.set(cell, space);
    }

    /**
     * 
     * @param index index of space
     * @return the space with said index
     */
    public Space getSpace(int index){
        return spaces.get(index);
    }

    /**
     * 
     * @return iterator of spaces
     */
    public Iterator<Space> iterator(){
        return spaces.iterator();
    }

    /**
     * Create a clone of this row, and all spaces inside of it
     * @return a clone of this row, including a deep clone of rows
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Row result = new Row(this.index, new ArrayList());
        for(int i = 0; i < this.spaces.size(); i++){
            result.spaces.add((Space)this.spaces.get(i).clone());
        }
        return result;
    }

    /**
     * Check the equality of this instance of row to another
     * @param obj object we are comparing to
     * @return true if obj is a Row instance and has equal contents to this one, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Row)){
            return false;
        }
        Row other = (Row)obj;
        for(int i = 0; i < spaces.size(); i++){
            if(!spaces.get(i).equals(other.spaces.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Encodes this Row as a string
     * @return a string following the format "[{index of this row} {stringified list of spaces in this row}"
     */
    public String toString(){
        return String.format("[%d] %s", index, spaces.toString());
    }
}
