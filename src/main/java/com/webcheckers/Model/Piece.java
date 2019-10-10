package com.webcheckers.Model;

public class Piece {
    private String type; // "SINGLE" or "KING"
    // true will be red false will be white(done so that we can easily do this outside of class)
    private String color;

    // constructor to initialize pieces
    public Piece(String c){
        color = c;
        type = "SINGLE";
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * reassign type to king
     */
    public void kingMe(){
        type = "KING";
    }

    // modify toString so that it returns R or W depending on color of piece
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