package com.webcheckers.Model;

/**
 * The game object
 * @author <a href='jxw7470@rit.edu'>Joshua Weiss</a>
 */
public class Game {

    /**
     * Attributes
     */
    private static int gameNum = 0;
    private Player red;
    private Player white;
    private Board board;
    private int gameID;
    private Enum currentPlayer;

    /**
     * Enum used to assign a color to a player
     */
    public enum Color {RED, WHITE}


    /**
     * constructor for game object
     * @param red, first player
     * @param white, second player
     */
    public Game(Player red, Player white){
        this.red = red;
        this.white = white;
        gameID = gameNum;
        board = new Board();
        gameNum++;
        currentPlayer = Color.RED;
    }

    /**
     * @return the gameid
     */
    public int getGameID() {
        return gameID;
    }


    public Board getBoard(){
        return board;
    }


    public Player getPlayer(Player.Color color){
        if(color == Player.Color.WHITE){
            return white;
        }else{
            return red;
        }
    }

    public Player getCurrentPlayer(){
        if(currentPlayer == Player.Color.WHITE){
            return white;
        }else{
            return red;
        }
    }

    public Player getOpponent(Player player){
        if(player.getColor() == Player.Color.WHITE){
            return red;
        }else if(player.getColor() == Player.Color.RED){
            return white;
        }
        return null;
    }


    public void setPlayer(Player.Color color, Player player){
        if(color == Player.Color.RED){
            this.red = player;
        }else if(color == Player.Color.WHITE){
            this.white = player;
        }else{
            System.err.println("Invalid Color");
        }
    }

    public void setCurrentPlayer(Player.Color color){
        currentPlayer = color;
    }

}