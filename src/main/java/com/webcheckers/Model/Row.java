package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row {

    public int index;
    private ArrayList<Space> spaces = new ArrayList<>();

    public Row(int index) {
        this.index = index;
    }

    public void addSpacePiece(boolean valid, int index, String color){
        Piece piece = new Piece(color);
        Space space = new Space(valid, index, piece);
        spaces.add(space);
    }

    public void addEmptySpace(boolean valid, int index){
        Space space = new Space(valid, index, null);
        spaces.add(space);
    }

    public int getIndex(){
        return index;
    }

    public Space getSpace(int index){
        return spaces.get(index);
    }

    public Iterator<Space> iterator(){
        return spaces.iterator();
    }

    public String toString(){
        return String.format("[%d] %s", index, spaces.toString());
    }
}
