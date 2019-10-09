package com.webcheckers.Model;

public class Board {
    private Space[][] spaces = new Space[8][8];
    public Board() {
        for (int i = 0; i < spaces.length; i++) {
            for (int x = 0; x < spaces[i].length; i++) {
                int [] coords = {i, x};
                if((i+x)%2 == 0) {
                    spaces[i][x] = new Space(false, coords);
                }
                else{
                    spaces[i][x] = new Space(true, coords);
                }
            }
        }
    }

}