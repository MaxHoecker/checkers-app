package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;

public class PlayerServices {

    //Attributes
    private Gson gson;
    private Player curPlayer = null; //represents this session's player
    private Boolean signedIn = null;
    private PlayerLobby playerLobby;
    private String viewMode = null;
    private Move curMove = null;
    private Boolean wonGame = null;

    //Constants
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String INVALID_NAME_MSG = "Invalid username. Please enter another name.";

    /**
     * Constructs PlayerServices
     * @param playerLobby server's player lobby
     */
    public PlayerServices(final PlayerLobby playerLobby, final Gson gson){
        this.playerLobby = playerLobby;
        this.gson = gson;
    }

    /**
     * Check if current user is signed in
     * @return null if player just connected for the first time, false if signed out, and true if signed in
     */
    public Boolean signedIn(){
        return signedIn;
    }

    /**
     * Set the signed-in status of the current user
     * @param signedIn status (true|false|null)
     */
    public void setSignedIn(Boolean signedIn){
        this.signedIn = signedIn;
    }

    public void setCurMove(Move move){
        this.curMove = move;
    }

    public Move getcurMove(){
        return curMove;
    }

    /**
     * Get current user's Player object
     * @return Player instance
     */
    public Player curPlayer(){
        return curPlayer;
    }

    /**
     * Get current user's opposing user's Player object
     * @return Player instance
     */
    public Player opponent(){
        return curPlayer.game().getOpponent(curPlayer);
    }

    /**
     * Get the board the current user is playing on
     * @return Board instance
     */
    public Board gameBoard(){
        return curPlayer.game().getBoard();
    }

    /**
     * Get the current user's name
     * @return username String
     */
    public String curPlayerName(){
        return curPlayer.getName();
    }

    /**
     * Get the color checker the current user is using
     * @return Color enum
     */
    public Color curPlayerColor(){
        return curPlayer.getColor();
    }

    public void setViewMode(String s){
        this.viewMode = s;
    }

    public String getViewMode(){
        return this.viewMode;
    }

    public Player whitePlayer(){
        return curPlayer.game().getPlayer(Color.WHITE);
    }

    public Player redPlayer(){
        return curPlayer.game().getPlayer(Color.RED);
    }

    /**
     * Add an instance of Player representing the current user to the playerLobby if:
     *  The username is valid
     *          and
     *  There is no other player in the lobby with the same username
     *  If username is null, curPlayer is set to null its opponent's reference
     *  to curPlayer is set to null. This indicates a sign-out
     * @param username the name or the player to be added
     * @return null if the add was successful, error message if unsuccessful
     */
    public String setCurPlayer(String username){
        if(username == null){
            if(curPlayer.game() != null) {
                curPlayer.game().setPlayer(curPlayer.getColor(), null);
            }
            curPlayer = null;
            return null;
        }else if(username.matches("\".*\"") || !username.matches(".*\\w+.*")){
            return INVALID_NAME_MSG;
        }
        Player currentPlayer = new Player(username);
        if(!playerLobby.addPlayer(username, currentPlayer)) {
            return NAME_TAKEN_MSG;
        }else{
            assert curPlayer == null;
            setSignedIn(true);
            curPlayer = currentPlayer;

            return null;
        }
    }

    /**
     * If the Player by the name of opponentName is not in a game, set
     * that player as the opponent attribute of curPlayer and give both
     * the same new instance of Board
     * Otherwise, return false
     * false
     * @param opponentName name of potential opponent
     * @return true if the game is set up successfully, false otherwise
     */
    public boolean setUpGame(String opponentName){
        Player op = playerLobby.getPlayer(opponentName);
        if(op.getColor() != null){
            return false;
        }else{
            op.setColor(Color.WHITE);
            curPlayer.setColor(Color.RED);
            Game game = new Game(curPlayer, op);
            op.setGame(game);
            curPlayer.setGame(game);
            return true;
        }
    }

    public boolean removeFromGame(){

        if(curPlayer.game() == null){
            return false;
        }
        else{

            if(curPlayer.getColor() == Color.RED){
                curPlayer.game().setPlayer(Color.RED, null);
            }
            else{
                curPlayer.game().setPlayer(Color.WHITE, null);
            }
            curPlayer.setColor(null);
            curPlayer.setGame(null);
            viewMode = null;
            curMove = null;
            return true;
        }
    }

    public Boolean getWonGame(){
        return wonGame;
    }

    public void setWonGame(Boolean won){
        wonGame = won;
    }

    /**
     * Alter the board that is shown to curPlayer and their opponent
     * @precondition move has already been validated by PostValidateMoveRoute
     * @param move the start and end positions of the piece to be moved
     */
    public synchronized void makeMove(Move move){
        curPlayer.game().makeMove(move);
    }

    public Message validateMove(String moveJson){
        if(moveJson.contains("null")){
            return Message.error("Invalid Move:null square detected");
        }
        Move move = gson.fromJson(moveJson, Move.class);
        Board board = gameBoard();
        Game game = curPlayer.game();



        Space start = board.getAtPosition(move.getStart().getRow(), move.getStart().getCell() );
        Space end = board.getAtPosition(move.getEnd().getRow(), move.getEnd().getCell());
        int distance = move.getDistance();
        boolean movingBackwards = (game.getCurrentPlayerColor() == Color.RED && distance < 0 && start.getOccupant().getType() != PieceType.KING)
                || (game.getCurrentPlayerColor() == Color.WHITE && distance > 0 && start.getOccupant().getType() != PieceType.KING);

        if( ! start.isValid()) {
                return Message.error("Invalid Move:Starting Square is Invalid");
        }
        else if( ! end.isValid()) {
                return Message.error("Invalid Move:Ending Square is Invalid");
        }
        else if(start.getOccupant() == null)
        {
            return Message.error("Invalid Move:No Piece in Starting Position");
        }
        else if(end.getOccupant() != null)
        {
            return Message.error("Invalid Move:Target position already occupied");
        }
        else if(movingBackwards){
            return Message.error("Invalid Move:Piece cannot move backwards");
        }
        else if(Math.abs(distance) == 1)
        {
            boolean existsValidJump = checkForValidJump(move, game, game.getCurrentPlayerColor());
            if(existsValidJump){
                return Message.error("Invalid Move:Must take available jump");
            }
            setCurMove(move);
            return Message.info("Valid Move");
        }
        else if(Math.abs(distance) == 2)
        {
            int mrow = move.getStart().getRow() + (move.getEnd().getRow() - move.getStart().getRow())/2;
            int mcell = move.getStart().getCell() + (move.getEnd().getCell() - move.getStart().getCell())/2;
            Space mid = board.getAtPosition(mrow, mcell);
            System.err.println(mrow + " " + mcell + " " + mid.toString());
            if(mid.getOccupant() != null && mid.getOccupant().getColor() != curPlayer().getColor()){
                setCurMove(move);
                return Message.info("Valid Move");
            }
            else{
                return Message.error("Invalid move:Jumped piece is wrong color or non-existant");
            }
        }
        else
        {
            return Message.error("Invalid Move: Invalid distance");
        }
    }

    /**
     * Helper method for validate move. Checks to see if it is possible for the piece at the start of a move to make a
     * jump
     * @param move the move being validated
     * @param game the game state
     * @param currentColor the color of the player whose turn it is
     * @return true if there is a possible jump, false otherwise
     */
    private boolean checkForValidJump(Move move, Game game, Color currentColor){
        Board board = game.getBoard();
        Space start = board.getAtPosition(move.getStart());
        int moveStartRow = move.getStart().getRow();
        int moveStartCol = move.getStart().getCell();
        if(start.getOccupant().getType() == PieceType.KING){
            for(int x = -1; x < 2; x+=2){
                for(int y = -1; y < 2; y+=2){
                    if(moveStartCol + x < 0 || moveStartCol + 2*x < 0 || moveStartCol + x > 7 || moveStartCol + 2*x > 7){
                        continue;
                    }
                    if(moveStartRow + y < 0 || moveStartRow + 2*y < 0|| moveStartRow + y > 7 || moveStartRow + 2*y > 7){
                        continue;
                    }
                    Space neighbor = board.getAtPosition(moveStartRow + y, moveStartCol + x);
                    if(neighbor.getOccupant() != null && neighbor.getOccupant().getColor() != currentColor){
                        Space behindNeighbor = board.getAtPosition(moveStartRow + (2*y), moveStartCol + (2*x));
                        if(behindNeighbor.getOccupant() == null){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        int y;
        if(currentColor == Color.RED) {
            y = 1;
        }else {
            y = -1;
        }
        for(int x = -1; x < 2; x+=2){
            if(moveStartCol + x < 0 || moveStartCol + 2*x < 0 || moveStartCol + x > 7 || moveStartCol + 2*x > 7){
                continue;
            }
            if(moveStartRow + y < 0 || moveStartRow + 2*y < 0 || moveStartRow + y > 7 || moveStartRow + 2*y > 7){
                continue;
            }
            Space neighbor = board.getAtPosition(moveStartRow + y, moveStartCol + x);
            if(neighbor.getOccupant() != null && neighbor.getOccupant().getColor() != currentColor){
                Space behindNeighbor = board.getAtPosition(moveStartRow + 2*y, moveStartCol + 2*x);
                if(behindNeighbor.getOccupant() == null){
                    return true;
                }
            }
        }
        return false;
    }
}
