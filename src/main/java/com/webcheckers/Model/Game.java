package com.webcheckers.Model;

import com.webcheckers.appl.PlayerServices;

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
    private Color currentPlayerColor;

    private int numRedPieces = 1;
    private int numWhitePieces = 1;


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

    public int numRedPieces(){
        return numRedPieces;
    }

    public int numWhitePieces(){
        return numWhitePieces;
    }

    public void setCurrentPlayerColor(Color color){
        currentPlayerColor = color;
    }

    public synchronized void makeMove(Move move, boolean multiJump){
        Space toMoveFrom = board.getAtPosition(move.getStart());
        Piece toMove = toMoveFrom.removeOccupant();
        Space toMoveTo = board.getAtPosition(move.getEnd());

        if(Math.abs(move.getDistance()) == 2){
            int mrow = move.getStart().getRow() + (move.getEnd().getRow() - move.getStart().getRow())/2;
            int mcell = move.getStart().getCell() + (move.getEnd().getCell() - move.getStart().getCell())/2;
            Space midPoint = board.getAtPosition(mrow, mcell);
            Piece removed = midPoint.removeOccupant();
            if(removed.getColor() == Color.RED){
                numRedPieces--;
            }else{
                numWhitePieces--;
            }
        }
        // change board state
        toMoveTo.setOccupant(toMove);
        toMoveFrom.setOccupant(null);


        //kinging
        if(currentPlayerColor == Color.WHITE && move.getEnd().getRow() == 0){
            toMove.kingMe();
        }else if(currentPlayerColor == Color.RED && move.getEnd().getRow() == 7){
            toMove.kingMe();
        }


        // swap turns
        if(!(Math.abs(move.getDistance()) == 2 && multiJump)) {
            if (currentPlayerColor == Color.RED) {
                setCurrentPlayerColor(Color.WHITE);
            } else if (currentPlayerColor == Color.WHITE) {
                setCurrentPlayerColor(Color.RED);
            }
        }
    }

}