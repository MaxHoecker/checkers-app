package com.webcheckers.Model;

public class Piece {
    private boolean type; //boolean for isKinged
    // true will be red false will be white(done so that we can easily do this outside of class)
    private boolean color;


    Piece(boolean c){
        color = c;
        type = false;
    }

    public boolean type() {
        return type;
    }

    public boolean getColor() {
        return color;
    }

    public void kingMe(){
        type = true;
    }
}