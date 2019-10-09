package com.webcheckers.Model;

import java.util.ArrayList;

public class Board {
    //private Space[][] spaces = new Space[8][8];
    private ArrayList<ArrayList<Space>> spaces = new ArrayList<>(); //used an arraylist because it has an iterator() method
    //all boards will be set up like this to start, 
    public Board() {
        /*for (int i = 0; i < spaces.length; i++) {
            for (int x = 0; x < spaces[i].length; x++) {
                int [] coords = {i, x};
                if((i+x)%2 == 0) {
                    if(i <= 2 ){
                        spaces[i][x] = new Space(coords, new Piece(true));
                    }
                    else if(i >= 5){
                        spaces[i][x] = new Space(coords, new Piece(false));
                    }
                    else{
                        spaces[i][x] = new Space(true, coords);
                    }
                    
                }
                else{
                    spaces[i][x] = new Space(false, coords);
                }
            }
         }
        */
        for(int i = 0; i < 8; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < 8; j++){
                int[] coords = {i, j};
                if((i+j)%2 == 0){
                    if(i <= 2){
                        spaces.get(i).add(new Space(coords, new Piece(true)));
                    }else if(i >= 5){
                        spaces.get(i).add(new Space(coords, new Piece(false)));
                    }else{
                        spaces.get(i).add(new Space(true, coords));
                    }
                }else{
                    spaces.get(i).add(new Space(false, coords));
                }
            }
        }
    }

    public ArrayList<ArrayList<Space>> iterator(){
        return spaces;
    }

}