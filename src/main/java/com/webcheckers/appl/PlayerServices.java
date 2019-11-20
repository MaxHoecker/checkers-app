package com.webcheckers.appl;

import com.google.gson.Gson;
import com.webcheckers.Model.*;
import com.webcheckers.util.Message;
import org.eclipse.jetty.io.ssl.ALPNProcessor;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

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

    public void enterReplay(){
        curPlayer.setGame(savedInitialGame);
    }

    public Boolean getVisitReplayPage(){
        return visitReplayPage;
    }

    public void setVisitReplayPage(Boolean visitReplayPage) {
        this.visitReplayPage = visitReplayPage;
    }

    public void setMoveList(ArrayList<Move> moveListSaved) {
        this.moveListSaved = moveListSaved;
        moveIndex = 0;
    }

    public boolean setNextMove(){
        moveIndex ++;
        if(moveIndex > moveListSaved.size()){
            moveIndex = moveListSaved.size();
            return false;
        }
        makeMove(moveListSaved.get(moveIndex));
        return true;
    }
    
    public boolean setPreviousMove(){
        moveIndex --;
        if (moveIndex < 0){
            moveIndex = 0;
            return false;
        }
        curPlayer.setGame(savedInitialGame);
        for(int i = 0; i < moveIndex; i++){
            makeMove(moveListSaved.get(i));
        }
        return true;
    }

    public boolean saveReplay(){
        if (moveList.size() == 0){
            return false;
        }
        else{
            //savedInitialGame = new Game(redPlayer().clone(), whitePlayer());
            moveListSaved = moveList;
            return true;
        }

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

    public void removeLastMove(){
        curMoveSequence.remove(curMoveSequence.size()-1);
    }

    //public Move getNextMove(){
    //    return curMoveSequence.get(0);
    //}

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

    public Color curTurnColor(){
        return curPlayer.game().getCurrentPlayerColor();
    }


    public void becomeSpectator(String toSpectateId){
        Player toSpectate = playerLobby.getPlayer(toSpectateId);
        curPlayer.setGame(toSpectate.game());
        try {
            lastKnown = (Board)toSpectate.game().getBoard().clone();
        }catch(CloneNotSupportedException e){
            System.err.println("PlayerServices.java:131: " + e.getMessage());
        }
    }

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
            else if(curPlayer.getColor() == Color.WHITE){
                curPlayer.game().setPlayer(Color.WHITE, null);
            }
            curPlayer.setColor(null);
            curPlayer.setGame(null);
            viewMode = null;
            curMoveSequence.clear();
            lastKnown = null;
            visitReplayPage = true;
            return true;
        }
    }

    public Boolean getWonGame(){
        return wonGame;
    }

    public void setWonGame(Boolean won, Message winMssg){
        wonGame = won;
        gameEndMessage = winMssg;
    }

    public void setWonGame(Boolean won){
        wonGame = won;
        gameEndMessage = new Message("Game ended for unknown reasons", Message.Type.ERROR);
    }

    public Message getGameEndMessage() {
        return gameEndMessage;
    }

    public Boolean getResigned(){
        return curPlayer.getResigned();
    }

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
        moveList.add(move);
        curPlayer.game().makeMove(move, multiJump);
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
        }
        return Message.info("Success");
    }

    /**
     * Helper method for validate move. Checks to see if it is possible for the piece at a position to make a
     * jump
     * @param pos we check for valid jumps from here
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
