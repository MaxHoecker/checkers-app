package com.webcheckers.Model;
/**
 * The Space object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 */
public class Space {
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
     * remove peice on square
     * @return removed piece
     */
    public Piece removeOccupant(){
        Piece p = piece;
        piece = null;
        return p;
    }

    /**
     * creates string that is created when tostring is called on space
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