package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Row {

    private int index ;
    private ArrayList<Space> spaces = new ArrayList<>();

    public Row(int index) {
        this.index = index;
    }

    public void addSpacePiece(boolean valid, int index, boolean color){
        Piece piece = new Piece(color);
        Space space = new Space(valid, index, piece);
        spaces.add(space);
    }

    public void addEmptySpace(boolean valid, int index){
        Space space = new Space(valid, index, null);
        spaces.add(space);
    }

    public Space getSpace(int index){
        return spaces.get(index);
    }

    public ArrayList<Space> iterator(){
        return spaces;
    }


}
