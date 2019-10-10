package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The board object that contains row objects
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
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
                        rows.get(i).addSpacePiece(true, j, "RED");
                    }else if(i >= 5){
                        rows.get(i).addSpacePiece(true, j, "WHITE");
                    }else{
                        rows.get(i).addEmptySpace(true, j);
                    }
                }else{
                    rows.get(i).addEmptySpace(false, j);
                }
            }
        }
    }

    /**
     *
     * @return an iterable collection for the game.ftl file to use
     */
    public Iterator<Row> iterator(){
        return rows.iterator();
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