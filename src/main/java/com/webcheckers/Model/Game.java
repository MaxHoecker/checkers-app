package com.webcheckers.Model;

public class Game {
    public Player red;
    public Player white;
    private int gameid;
    /**
     * @param Player one, first player
     * @param Player two, second player
     * @param int id, id for the game
     */
    public Game(Player one, Player two, int id){
        red = one;
        white = two;
        gameid = id;
    }

    /**
     * @return the gameid
     */
    public int getGameid() {
        return gameid;
    }

    /**
     * @return the red
     */
    public Player getRed() {
        return red;
    }
    
    /**
     * @return the white
     */
    public Player getWhite() {
        return white;
    }

}