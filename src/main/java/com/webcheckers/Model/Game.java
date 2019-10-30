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
    private Enum currentPlayerColor;



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
        currentPlayerColor = Color.RED;
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


    public Player getPlayer(Color color){
        if(color == Color.WHITE){
            return white;
        }else{
            return red;
        }
    }

    public Color getCurrentPlayerColor(){
        if(currentPlayerColor == Color.WHITE){
            return Color.WHITE;
        }else{
            return Color.RED;
        }
    }

    public Player getOpponent(Player player){
        if(player.getColor() == Color.WHITE){
            return red;
        }else if(player.getColor() == Color.RED){
            return white;
        }
        return null;
    }


    public void setPlayer(Color color, Player player){
        if(color == Color.RED){
            this.red = player;
        }else if(color == Color.WHITE){
            this.white = player;
        }else{
            System.err.println("Invalid Color");
        }
    }

    public void setCurrentPlayerColor(Color color){
        currentPlayerColor = color;
    }

    public synchronized void makeMove(Move move){
        Space toMoveFrom = board.getAtPosition(move.start);
        Piece toMove = toMoveFrom.removeOccupant();
        Space toMoveTo = board.getAtPosition(move.end);
        toMoveTo.setOccupant(toMove);
    }

}