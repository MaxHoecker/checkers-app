package com.webcheckers.Model;

public class Space {
    private Piece piece;
    //x and y coordinates
    private int cellIdx;
    private boolean isValid;


    //no need for v since if there is a piece there it must be valid
    public Space(boolean v, int index, Piece p ){
        isValid = v;
        cellIdx = index;
        piece = p;
    }

    public Piece getOccupant(){
        return piece;
    }
    
    public int getCell() {
        return cellIdx;
    }

    public boolean isValid(){
        return isValid;
    }

    public void moveTo(Piece p){
        piece = p;
    }

    public Piece removeOccupant(){
        Piece p = piece;
        piece = null;
        return p;
    }

}