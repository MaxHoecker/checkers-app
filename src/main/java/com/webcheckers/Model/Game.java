package com.webcheckers.Model;

public class Game {
    private Player red;
    private Player white;
    private int gameid;
    /**
     * @param one, first player
     * @param two, second player
     * @param id, id for the game
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