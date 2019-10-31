package com.webcheckers.Model;
/**
 * The Piece Object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 */
public class Piece {
    private String type; // "SINGLE" or "KING"
    // true will be red false will be white(done so that we can easily do this outside of class)
    private Color color;

    // constructor to initialize pieces
    public Piece(Color c){
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
    public Color getColor() {
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
        if (color.equals(Color.RED)){
            return"R";
        }
        else{
            return "W";
        }
    }
}