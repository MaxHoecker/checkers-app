package com.webcheckers.Model;

public class Space {
    private Piece piece;
    private int cellIdx;
    private boolean isValid;

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

    public int getCellIdx(boolean flip)
    {
        if(flip){
            return 8-cellIdx;
        }
        return cellIdx;
    }
    public int getCellIdx()
    {
        return cellIdx;
    }

    public void moveTo(Piece p){
        piece = p;
    }

    public Piece removeOccupant(){
        Piece p = piece;
        piece = null;
        return p;
    }

    @Override
    public String toString() {
        if(piece!= null){
            return piece.toString();
        }
        else{
            if(isValid){
                return "1";
            }
            else {
                return "0";
            }
        }

    }
}