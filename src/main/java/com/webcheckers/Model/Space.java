package com.webcheckers.Model;
/**
 * The Space object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class Space implements Cloneable{
    private Piece piece;
    private int cellIdx;
    private boolean isValid;
    
    /**
    * 
    * @param isValid assigns is valid
    * @param index index for the space
    * @param piece peice on the space
    */
    public Space(boolean isValid, int index, Piece piece ){
        this.isValid = isValid;
        cellIdx = index;
        this.piece = piece;
    }

    /**
     * @return piece
     */
    public Piece getOccupant(){
        return piece;
    }
    
    /**
     * @return cellIdx
     */
    public int getCell() {
        return cellIdx;
    }

    /**
     * @return isValid
     */
    public boolean isValid(){
        return isValid;
    }

    /**
     * 
     * @return CellIdx
     */
    public int getCellIdx()
    {
        return cellIdx;
    }

    /**
     * 
     * @param p peice being moved onto square
     */
    public void setOccupant(Piece p){
        if(isValid){
            piece = p;
        }
    }

    /**
     * remove piece on square
     * @return removed piece
     */
    public Piece removeOccupant(){
        Piece p = piece;
        piece = null;
        return p;
    }

    /**
     * Make a clone of this space such that no pointers are shared between this and its clone
     * @return a clone if this Space
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException{
        if(this.piece != null) {
            return new Space(this.isValid, this.cellIdx, (Piece) this.piece.clone());
        }
        return new Space(this.isValid, this.cellIdx, null);
    }

    /**
     * Check the equality of this Space instance to another
     * @param obj the object we are comparing to
     * @return true if obj is a Space instance and has equal contents to this one, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Space)){
            return false;
        }
        Space other = (Space)obj;
        boolean ePiece = (piece == null && other.piece == null) || ((piece != null && other.piece != null) && this.piece.equals(other.piece));
        boolean eCellIdx = this.cellIdx == other.cellIdx;
        boolean eIsValid = this.isValid == other.isValid;
        return ePiece && eCellIdx && eIsValid;
    }

    /**
     * creates string that is created when tostring is called on space
     * @return the string representation of piece if piece exists, otherwise "1" if this space is valid, and "0" otherwise
     */
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