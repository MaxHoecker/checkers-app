package com.webcheckers.Model;

public class Piece {
    private boolean isKinged;
    // true will be red false will be white(done so that we can easily do this outside of class)
    private boolean color;
    Piece(boolean c){
        color = c;
        isKinged = false;
    }

    public boolean getisKinged() {
        return isKinged;
    }

    public boolean getColor() {
        return color;
    }

    public void Kingme(){
        isKinged = true;
    }
}