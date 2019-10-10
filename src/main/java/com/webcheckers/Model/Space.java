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
    * @param v assigns is valid
    * @param index index for the space
    * @param p peice on the space
    */
    public Space(boolean v, int index, Piece p ){
        isValid = v;
        cellIdx = index;
        piece = p;
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
     * @param flip 
     * @return cellIdx
     */
    public int getCellIdx(boolean flip)
    {
        if(flip){
            return 8-cellIdx;
        }
        return cellIdx;
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
    public void moveTo(Piece p){
        piece = p;
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