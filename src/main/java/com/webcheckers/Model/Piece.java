package com.webcheckers.Model;

public class Piece {
    private String type; // "SINGLE" or "KING"
    // true will be red false will be white(done so that we can easily do this outside of class)
    private String color;

    public Piece(String c){
        color = c;
        type = "SINGLE";
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public void kingMe(){
        type = "KING";
    }

    @Override
    public String toString() {
        if (color.equals("RED")){
            return"R";
        }
        else{
            return "W";
        }
    }
}