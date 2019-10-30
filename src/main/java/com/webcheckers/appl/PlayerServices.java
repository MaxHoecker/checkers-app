package com.webcheckers.appl;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Game;
import com.webcheckers.Model.Player;

public class PlayerServices {

    //Attributes
    private Player curPlayer = null; //represents this session's player
    private Boolean signedIn = null;
    private PlayerLobby playerLobby;

    //Constants
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String INVALID_NAME_MSG = "Invalid username. Please enter another name.";

    /**
     * Constructs PlayerServices
     * @param playerLobby server's player lobby
     */
    public PlayerServices(final PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
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
    public Player.Color curPlayerColor(){
        return curPlayer.getColor();
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
            /*
            op.setColor(Player.Color.WHITE);
            curPlayer.setOpponent(op);
            curPlayer.setColor(Player.Color.RED);
            op.setOpponent(curPlayer);
            Board board = new Board();
            curPlayer.setBoard(board);
            op.setBoard(board);
            return true;
            */
            op.setColor(Player.Color.WHITE);
            curPlayer.setColor(Player.Color.RED);
            Game game = new Game(curPlayer, op);
            op.setGame(game);
            curPlayer.setGame(game);
            return true;
        }
    }
}
