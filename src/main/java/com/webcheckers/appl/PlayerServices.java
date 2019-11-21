package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;
import org.eclipse.jetty.io.ssl.ALPNProcessor;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Handles all functionality of individual users
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class PlayerServices {

    //Attributes
    private Gson gson;
    private Player curPlayer = null; //represents this session's player
    private Boolean signedIn = null;
    private PlayerLobby playerLobby;
    private String viewMode = null;
    private Boolean wonGame = null;
    private Message gameEndMessage;

    private ArrayList<Move> curMoveSequence = new ArrayList<>();

    //for spectators only
    private Board lastKnown = null;

    //for the replay feature
    private Game savedInitialGame;
    private Player redCopy;
    private Player whiteCopy;
    private ArrayList<Move> moveList = new ArrayList<>();
    private ArrayList<Move> moveListSaved = new ArrayList<>();
    private Boolean visitReplayPage = false;
    private int moveIndex = 0;

    //Constants
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String INVALID_NAME_MSG = "Invalid username. Please enter another name.";
    private final static String VALID_NAME_REGEX = "[a-zA-Z\\d ]+";

    /**
     * Constructs PlayerServices
     * @param playerLobby server's player lobby
     */
    public PlayerServices(final PlayerLobby playerLobby, final Gson gson){
        this.playerLobby = playerLobby;
        this.gson = gson;
    }


    /**
     * ==========================================================================================
     *                                     REPLAY METHODS
     * ==========================================================================================
     */

    /**
     *lets the player enter the saved game by cloning the saved game's initial state
     *and setting it as the current game
     */
    public void enterReplay(){
        try{
            curPlayer.setGame((Game)savedInitialGame.clone());
        }
        catch (CloneNotSupportedException e){
            System.err.println(e);
        }
    }

    /**
     * gets whether the current session needs to visit the save replay page
     * @return true if they need to visit, otherwise false
     */
    public Boolean getVisitReplayPage(){
        return visitReplayPage;
    }

    /**
     * sets whether the current session needs to visit the save replay page
     * @param visitReplayPage true if the game ends and the player needs to visit the replay page
     */
    public void setVisitReplayPage(Boolean visitReplayPage) {
        this.visitReplayPage = visitReplayPage;
    }

    /**
     * gets whether a game has been saved or not
     * @return true if there's a saved game false otherwise
     */
    public boolean hasSaved(){
        if(moveListSaved.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * gets whether there's a next move in the saved move list
     * @return true is there is, false otherwise
     */
    public boolean hasNext(){
        moveIndex ++;
        if(moveIndex > moveListSaved.size()){
            moveIndex --;
            return false;
        }
        moveIndex --;
        return true;
    }

    /**
     * gets whether there's a previous move in the saved move list
     * @return true is there is, false otherwise
     */
    public boolean hasPrevious(){
        moveIndex --;
        if(moveIndex < 0){
            moveIndex ++;
            return false;
        }
        moveIndex ++;
        return true;
    }

    /**
     * if there's moves left, gets the next move in the saved move list and then makes the move in the game
     * @return true if it made the move successfully, false otherwise
     */
    public boolean setNextMove(){
        if(hasNext()){
            makeMove(moveListSaved.get(moveIndex));
            moveIndex ++;
            return true;
        }
        return false;
    }

    /**
     * if the game's not on the initial board state,
     * reset the board to the initial state
     * and makes moves from the Saved move list up until the move before what state the game was at
     *
     * @return true if it made the move successfully, false otherwise
     */
    public boolean setPreviousMove(){
        if (hasPrevious()){
            moveIndex --;
            try{
                curPlayer.setGame((Game)savedInitialGame.clone());
            }
            catch (CloneNotSupportedException e){
                System.err.println(e);
            }
            for(int i = 0; i < moveIndex; i++){
                makeMove(moveListSaved.get(i));
            }
            return true;
        }
        return false;
    }

    /**
     * makes copy of the initial board state from the game just played
     * and then saves the list of moves that were made in the game
     * @return true if successfully saved
     */
    public boolean saveReplay(){
            try{
                savedInitialGame = new Game(redCopy.clone(), whiteCopy.clone());
            }
            catch (CloneNotSupportedException e){
                System.err.println(e);
            }
            moveListSaved = moveList;
            return true;
    }

    /**
     * saves a clone of the red player for if the player wants to save the replay
     * @return true if the player was successfully cloned, false otherwise
     */
    public boolean saveRed(){
        if ( curPlayer.game() != null) {
            try {
                if (curPlayer.getColor() == Color.RED) {
                    redCopy = curPlayer.clone();
                } else {
                    redCopy = redPlayer().clone();
                }
            } catch (CloneNotSupportedException e) {
                System.err.println(e);
            }
            return true;
        }
        return false;
    }

    /**
     * saves a clone of the white player for if the player wants to save the replay
     * @return true if the player was successfully cloned, false otherwise
     */
    public boolean saveWhite(){
        if ( curPlayer.game() != null) {
            try {
                if (curPlayer.getColor() == Color.WHITE) {
                    whiteCopy = curPlayer.clone();
                } else {
                    whiteCopy = whitePlayer().clone();
                }
            } catch (CloneNotSupportedException e) {
                System.err.println(e);
            }
            return true;
        }
        return false;
    }

    /**
     * =================================================================================
     *
     * ==================================================================================
     */



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

    /**
     * Remove the most recent move added to the move sequence for this turn
     */
    public void removeLastMove(){
        curMoveSequence.remove(curMoveSequence.size()-1);
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

    /**
     * Set the view mode for the current user
     * @param s String enum of PLAY | SPECTATOR | REPLAY
     */
    public void setViewMode(String s){
        this.viewMode = s;
    }

    /**
     * Get the view mode for the current user
     * @return String enum of PLAY | SPECTATOR | REPLAY
     */
    public String getViewMode(){
        return this.viewMode;
    }

    /**
     * Get the white player for the game the current user is interacting with
     * @return a player
     */
    public Player whitePlayer(){
        return curPlayer.game().getPlayer(Color.WHITE);
    }

    /**
     * Get the red player for the game that the current user is interacting with
     * @return a player
     */
    public Player redPlayer(){
        return curPlayer.game().getPlayer(Color.RED);
    }

    /**
     * Returns the color of the player whose turn it is in the game that curPlayer is interacting with
     * @return Color.RED | Color.WHITE
     */
    public Color curTurnColor(){
        return curPlayer.game().getCurrentPlayerColor();
    }


    /**
     * Configure this instance of PlayerServices to accommodate the current player becoming a spectator
     * @param toSpectateId the name of the player whose game curPlayer wishes to spectate
     */
    public void becomeSpectator(String toSpectateId){
        Player toSpectate = playerLobby.getPlayer(toSpectateId);
        curPlayer.setGame(toSpectate.game());
        try {
            lastKnown = (Board)toSpectate.game().getBoard().clone();
        }catch(CloneNotSupportedException e){
            System.err.println("PlayerServices.java:131: " + e.getMessage());
        }
    }

    /**
     * Used by spectators to see if the state of the game board has changed at all. If it has,
     * returns a message that tells the client side whether or not to refresh that page
     * @return an affirmative message if the current game state does not match the last known game state or a negative
     *          message otherwise
     */
    public Message spectatorCheckTurn(){
        if(curPlayer.game().getBoard().equals(lastKnown)){
            return Message.info("false");
        }else{
            try {
                lastKnown = (Board) curPlayer.game().getBoard().clone();
            }catch(CloneNotSupportedException e){
                System.err.println("PlayerServices.java:144: " + e.getMessage());
            }
            return Message.info("true");
        }
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
        }else if(!username.matches(VALID_NAME_REGEX)){
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
        }
        else {
            op.setColor(Color.WHITE);
            curPlayer.setColor(Color.RED);
            Game game = new Game(curPlayer, op);
            op.setGame(game);
            curPlayer.setGame(game);
            return true;
        }
    }

    /**
     * Erase all data related to a game that curPlayer is interacting with
     * @return true if successful, false if there is no game to begin with
     */
    public boolean removeFromGame(){


        if(curPlayer.game() == null){
            return false;
        }
        else{
            moveList = curPlayer.game().getMoveList();
            moveIndex = 0;
            visitReplayPage = true;

            if(curPlayer.getColor() == Color.RED){
                curPlayer.game().setPlayer(Color.RED, null);
            }
            else if(curPlayer.getColor() == Color.WHITE){
                curPlayer.game().setPlayer(Color.WHITE, null);
            }
            curPlayer.setColor(null);
            curPlayer.setGame(null);
            viewMode = null;
            curMoveSequence.clear();
            lastKnown = null;

            return true;
        }
    }

    /**
     * Check if the game is over and curPlayer won the game (used for users playing a game)
     * @return a Boolean
     */
    public Boolean getWonGame(){
        return wonGame;
    }

    /**
     * Set how the game ended for curPlayer (used for users playing a game)
     * @param won true if curPlayer won the game, false otherwise
     * @param winMssg message for the player based on the game's outcome
     */
    public void setWonGame(Boolean won, Message winMssg){
        wonGame = won;
        gameEndMessage = winMssg;
    }

    /**
     * Called when the game ends for unknown reasons/in error
     * @param won true if curPlayer won the game, false otherwise
     */
    public void setWonGame(Boolean won){
        wonGame = won;
        gameEndMessage = new Message("Game ended for unknown reasons", Message.Type.ERROR);
    }

    /**
     * Get the end game message
     * @return a Message
     */
    public Message getGameEndMessage() {
        return gameEndMessage;
    }

    /**
     * Check if the user resigned or not
     * @return a Boolean
     */
    public Boolean getResigned(){
        return curPlayer.getResigned();
    }

    /**
     * Set the current user's resignation status
     * @param b a Boolean
     */
    public void setResigned(Boolean b){
        curPlayer.setResigned(b);
    }

    /**
     * Alter the board that is shown to curPlayer and their opponent
     * @precondition move has already been validated by PostValidateMoveRoute
     * @param move the start and end positions of the piece to be moved
     */
    public synchronized void makeMove(Move move){
        Game game = curPlayer.game();
        Board board = game.getBoard();
        Piece toMove = board.getAtPosition(move.getStart()).getOccupant();
        PieceType type = toMove.getType();
        boolean multiJump = Math.abs(move.getDistance()) == 2 && checkForValidJump(move.getEnd(), type, game.getCurrentPlayerColor());
        curPlayer.game().makeMove(move, multiJump);
    }

    /**
     * Return a message for the client side on whether or not a given move is valid. Returns a message of type error if
     * the move is invalid, and a message of type info if valid. Validity is based on American checkers rules.
     * @param moveJson a JSON string for a move object
     * @return a Message
     */
    public Message validateMove(String moveJson){
        if(moveJson.contains("null")){
            return Message.error("Invalid Move:null square detected");
        }
        Move move = gson.fromJson(moveJson, Move.class);
        Board board = gameBoard();
        Game game = curPlayer.game();


        Space start = board.getAtPosition(move.getStart().getRow(), move.getStart().getCell() );
        Space end = board.getAtPosition(move.getEnd().getRow(), move.getEnd().getCell());
        PieceType type;
        if(curMoveSequence.size() == 0){
            type = board.getAtPosition(move.getStart()).getOccupant().getType();
        }else{
            Position initPos = curMoveSequence.get(0).getStart();
            type = board.getAtPosition(initPos).getOccupant().getType();
        }
        int distance = move.getDistance();
        boolean movingBackwards = (game.getCurrentPlayerColor() == Color.RED && distance < 0 && type != PieceType.KING)
                || (game.getCurrentPlayerColor() == Color.WHITE && distance > 0 && type != PieceType.KING);

        if( ! start.isValid()) {
                return Message.error("Invalid Move:Starting Square is Invalid");
        }
        else if( ! end.isValid()) {
                return Message.error("Invalid Move:Ending Square is Invalid");
        }
        else if(start.getOccupant() == null)
        {
            if (Math.abs(curMoveSequence.get(0).getDistance()) == 2) {
                if (!movingBackwards && Math.abs(move.getDistance()) == 2) {
                    if(getMidPoints().contains(move.getMidPoint())){
                        return Message.error("Invalid Move:Already jumped this piece");
                    }
                    curMoveSequence.add(move);
                    return Message.info("Multi-jump in progress");
                }
            }
            return Message.error("Invalid Move:No piece on start");
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
            for(int x = 0; x < 8; x++) {
                for(int y = 0; y < 8; y++) {
                    Space curSpace = game.getBoard().getAtPosition(x, y);
                    if(curSpace.getOccupant() != null && curSpace.getOccupant().getColor() == game.getCurrentPlayerColor()) {
                        boolean existsValidJump = checkForValidJump(new Position(x,y), curSpace.getOccupant().getType(), game.getCurrentPlayerColor());
                        if (existsValidJump) {
                            return Message.error("Invalid Move:Must take available jump");
                        }
                    }
                }
            }
            curMoveSequence.add(move);
            return Message.info("Valid Move");
        }
        else if(Math.abs(distance) == 2)
        {
            int mrow = move.getStart().getRow() + (move.getEnd().getRow() - move.getStart().getRow())/2;
            int mcell = move.getStart().getCell() + (move.getEnd().getCell() - move.getStart().getCell())/2;
            Space mid = board.getAtPosition(mrow, mcell);
            Position midPos = new Position(mrow, mcell);
            if(mid.getOccupant() != null && mid.getOccupant().getColor() != curPlayer().getColor()){
                curMoveSequence.add(move);
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

    public Message submitTurn(){
        Move lastMove = curMoveSequence.get(curMoveSequence.size()-1);
        Position initialPos = curMoveSequence.get(0).getStart();
        Space initSpace = gameBoard().getAtPosition(initialPos);
        PieceType toMove = initSpace.getOccupant().getType();
        if(Math.abs(lastMove.getDistance()) == 2 && checkForValidJump(lastMove.getEnd(), toMove, curTurnColor())){
            return Message.error("Must complete multi-jump");
        }
        while(curMoveSequence.size() != 0){
            Move nextMove = curMoveSequence.remove(0);
            makeMove(nextMove);
            curPlayer.game().addMove(nextMove);
        }

        return Message.info("Success");
    }

    /**
     * Helper method for validate move. Checks to see if it is possible for the piece at a position to make a
     * jump
     * @param pos we check for valid jumps from here
     * @param type type of the piece we want to see if it can make a jump
     * @param currentColor the color of the player whose turn it is
     * @return true if there is a possible jump, false otherwise
     */
    private boolean checkForValidJump(Position pos, PieceType type, Color currentColor){
        Game game = curPlayer().game();
        Board board = game.getBoard();
        int moveStartRow = pos.getRow();
        int moveStartCol = pos.getCell();
        if(type == PieceType.KING){
            for(int x = -1; x < 2; x+=2){
                for(int y = -1; y < 2; y+=2){
                    if(moveStartCol + x < 0 || moveStartCol + 2*x < 0 || moveStartCol + x > 7 || moveStartCol + 2*x > 7){
                        continue;
                    }
                    if(moveStartRow + y < 0 || moveStartRow + 2*y < 0|| moveStartRow + y > 7 || moveStartRow + 2*y > 7){
                        continue;
                    }
                    Position neighborPos = new Position(moveStartRow+y, moveStartCol+x);
                    if(getMidPoints().contains(neighborPos)){
                        continue;
                    }
                    Space neighbor = board.getAtPosition(neighborPos);
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

    /**
     * Get the midpoints for all jumps made in the move sequence for the current turn
     * @return an ArrayList of the midpoints represented by Position objects
     */
    private ArrayList<Position> getMidPoints(){
        ArrayList<Position> result = new ArrayList<>();
        for(int i = 0; i < curMoveSequence.size(); i++){
            Move curMove = curMoveSequence.get(i);
            Position mid = curMove.getMidPoint();
            if(mid != null){
                result.add(mid);
            }
        }
        return result;
    }
}
