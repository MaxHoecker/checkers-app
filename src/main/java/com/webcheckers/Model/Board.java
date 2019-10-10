package com.webcheckers.Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {

    private ArrayList<Row> rows = new ArrayList<>(); //used an arraylist because it has an iterator() method
    //all boards will be set up like this to start, 
    public Board() {

        for(int i = 0; i < 8; i++){
            rows.add(new Row(i));
            for(int j = 0; j < 8; j++){
                if((i+j)%2 != 0){
                    if(i <= 2){
                        rows.get(i).addSpacePiece(true, j, true);
                    }else if(i >= 5){
                        rows.get(i).addSpacePiece(true, j, false);
                    }else{
                        rows.get(i).addEmptySpace(true, j);
                    }
                }else{
                    rows.get(i).addEmptySpace(false, j);
                }
            }
        }
    }

    public ArrayList<Row> iterator(){
        return rows;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < 8; i++){
            result += "\n" + rows.get(i).toString();
        }
        return result;
    }
}