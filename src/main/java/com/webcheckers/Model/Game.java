package com.webcheckers.Model;

public class Game {

    private static int gameNum = 0;
    private Player red;
    private Player white;
    private int gameID;
    /**
     * @param one, first player
     * @param two, second player
     */
    public Game(Player one, Player two){
        red = one;
        white = two;
        gameID = gameNum;
        gameNum++;
    }

    /**
     * @return the gameid
     */
    public int getGameID() {
        return gameID;
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