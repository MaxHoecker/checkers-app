package com.webcheckers.Model;

public class Space {
    private Piece occupant;
    private int[] cellidx = new int[2];
    private boolean isValid;
    public Space(boolean v, int[] coords){
        isValid = v;
        cellidx = coords;
    }

    public Piece getOccumpant(){
        return occupant;
    }
    
    public int[] getCell() {
        return cellidx;
    }

    public boolean getValid(){
        return isValid;
    }

}