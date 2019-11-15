package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The board object that contains row objects
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class Board {

    //an array of row objects
    private ArrayList<Row> rows = new ArrayList<>(); //used an arraylist because it has an iterator() method

    /**
     * board constructor that makes the 8x8 board filled in with pieces where they should be
     */
    public Board() {
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i));
            for(int j = 0; j < 8; j++){
                if((i+j)%2 != 0){
                    if(i <= 2){
                        rows.get(i).addSpacePiece(true, j, Color.RED);
                    }else if(i >= 5){
                        rows.get(i).addSpacePiece(true, j, Color.WHITE);
                    }else{
                        rows.get(i).addEmptySpace(true, j);
                    }
                }else{
                    rows.get(i).addEmptySpace(false, j);
                }
            }
        }
    }
    /*
    //multi-jump test
    public Board(){
        for(int i = 0; i < 8; i++){
            rows.add(new Row(i));
            for(int j = 0; j < 8; j++){
                if((i+j)%2 != 0){
                    if(i == 7 && j == 0){
                        rows.get(i).addSpacePiece(true, 0, Color.RED);
                    }else if(i == 6 && j == 1) {
                        rows.get(i).addSpacePiece(true, 1, Color.WHITE);
                    }else if(i == 4 && j == 3) {
                        rows.get(i).addSpacePiece(true, 3, Color.WHITE);
                    }else{
                        rows.get(i).addEmptySpace(true, j);
                    }
                }else{
                    rows.get(i).addEmptySpace(false, j);
                }
            }
        }
    }*/

    public synchronized Space getAtPosition(Position pos){
        return getAtPosition(pos.getRow(), pos.getCell());
    }

    public synchronized Space getAtPosition(int row, int col){
        return rows.get(row).getSpace(col);
    }

    /**
     *
     * @return an iterable collection for the game.ftl file to use
     */
    public Iterator<Row> iterator(){
        return rows.iterator();
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Board)){
            return false;
        }
        Board other = (Board)obj;
        for(int i = 0; i < 8; i++){
            if(!this.rows.get(i).equals(other.rows.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * create string to print when toString is called for board
      */
    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < 8; i++){
            result += "\n" + rows.get(i).toString();
        }
        return result;
    }
}