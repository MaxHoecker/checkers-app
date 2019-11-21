package com.webcheckers.Model;
/**
 * The Piece Object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 *  * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
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

    /**
     * Creates a copy of this Piece such that no pointers are shared
     * @return a clone of this Piece
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Piece result = new Piece(this.color);
        if(this.type == PieceType.KING){
            result.kingMe();
        }
        return result;
    }

    /**
     * Check the equality of a piece.
     * @param obj the object we are comparing this Piece to
     * @return true if the object is an instance of Piece and has matching fields, false otherwise
     */
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