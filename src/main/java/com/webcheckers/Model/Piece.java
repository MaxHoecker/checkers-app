package com.webcheckers.Model;
/**
 * The Piece Object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 */
public class Piece {
    private PieceType type;
    // true will be red false will be white(done so that we can easily do this outside of class)
    private Color color;

    // constructor to initialize pieces
    public Piece(Color c){
        color = c;
        type = PieceType.SINGLE;
    }

    /**
     * @return type
     */
    public PieceType getType() {
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
        type = PieceType.KING;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Piece)){
            return false;
        }
        Piece other = (Piece)obj;
        boolean eType = this.type == other.type;
        boolean eColor = this.color == other.color;
        return eType && eColor;
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