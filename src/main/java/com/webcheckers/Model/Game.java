package com.webcheckers.Model;


/**
 * The game object
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
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

    private int numRedPieces = 12;
    private int numWhitePieces = 12;


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


    /**
     * Get the board this game is being played on
     * @return a Board
     */
    public Board getBoard(){
        return board;
    }


    /**
     * Get the player of the given color
     * @param color the color of the player to be gotten
     * @return a Player
     */
    public Player getPlayer(Color color){
        if(color == Color.WHITE){
            return white;
        }else{
            return red;
        }
    }

    /**
     * Get the color of the player whose turn it is
     * @return a Color
     */
    public Color getCurrentPlayerColor(){
        if(currentPlayerColor == Color.WHITE){
            return Color.WHITE;
        }else{
            return Color.RED;
        }
    }

    /**
     * Get the opponent of the player passed in
     * @param player the player whose opponent you want to find
     * @return a Player
     */
    public Player getOpponent(Player player){
        if(player.getColor() == Color.WHITE){
            return red;
        }else if(player.getColor() == Color.RED){
            return white;
        }
        return null;
    }


    /**
     * Assign a player to a game based on color
     * @param color the color we want to assign this player
     * @param player the player we want to assign
     */
    public void setPlayer(Color color, Player player){
        if(color == Color.RED){
            this.red = player;
        }else if(color == Color.WHITE){
            this.white = player;
        }else{
            System.err.println("Invalid Color");
        }
    }

    /**
     * Get the number of red pieces left in the game
     * @return an int
     */
    public int numRedPieces(){
        return numRedPieces;
    }

    /**
     * Get the number of white pieces left in the game
     * @return an int
     */
    public int numWhitePieces(){
        return numWhitePieces;
    }

    /**
     * Set the color of the player whose turn it is
     * @param color the color of the player whose turn it is
     */
    public void setCurrentPlayerColor(Color color){
        currentPlayerColor = color;
    }

    /**
     * Modifies the board state when a user makes a move
     * @param move the move a player made
     * @param multiJump a boolean that is true if the player just made a jump and can make another
     */
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