package com.webcheckers.Model;

public class Space {
    private Piece occupant;
    //x and y coordinates
    private int[] cellidx = new int[2];
    private boolean isValid;

    public Space(boolean v, int[] coords){
        isValid = v;
        cellidx = coords;
    }

    //no need for v since if there is a piece there it must be valid
    public Space(int[] coords, Piece p){
        isValid = true;
        cellidx = coords;
        occupant = p;
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

    public void moveto(Piece p){
        occupant = p;
    }

    public Piece removeOccupant(){
        Piece p = occupant;
        occupant = null;
        return p;
    }

}