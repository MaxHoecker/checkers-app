package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Row object
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class Row {

    public int index;
    private ArrayList<Space> spaces = new ArrayList<>();

    // constructor for row
    public Row(int index) {
        this.index = index;
    }

    /**
     * @param valid, if the space is valid
     * @param index index of the space
     * @param color color of the piece
     * adds space with a piece
     */
    public void addSpacePiece(boolean valid, int index, String color){
        Piece piece = new Piece(color);
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

    // creats string for row when called by tostring
    public String toString(){
        return String.format("[%d] %s", index, spaces.toString());
    }
}
