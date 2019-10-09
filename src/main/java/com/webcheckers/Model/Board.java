package com.webcheckers.Model;

public class Board {
    private Space[][] spaces = new Space[8][8];
    //all boards will be set up like this to start, 
    public Board() {
        for (int i = 0; i < spaces.length; i++) {
            for (int x = 0; x < spaces[i].length; i++) {
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
    }

}