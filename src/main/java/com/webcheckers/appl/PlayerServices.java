package com.webcheckers.appl;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;

public class PlayerServices {

    //Attributes
    private Player curPlayer = null; //represents this session's player
    private Boolean signedIn = null;
    private PlayerLobby playerLobby;

    //Constants
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String INVALID_NAME_MSG = "Name missing an alphanumeric character. Please enter another name.";


    public PlayerServices(final PlayerLobby playerLobby){
        this.playerLobby = playerLobby;
    }

    public Boolean signedIn(){
        return signedIn;
    }

    public void setSignedIn(Boolean signedIn){
        this.signedIn = signedIn;
    }

    public Player curPlayer(){
        return curPlayer;
    }

    public Player opponent(){
        return curPlayer.getOpponent();
    }

    public Board gameBoard(){
        return curPlayer.getBoard();
    }

    public String curPlayerName(){
        return curPlayer.getName();
    }

    public Player.Color curPlayerColor(){
        return curPlayer.getColor();
    }

    public String setCurPlayer(String username){
        if(username == null){
            if(curPlayer.getOpponent() != null) {
                curPlayer.getOpponent().setOpponent(null);
            }
            curPlayer = null;
            return null;
        }else if(!username.matches(".*\\w+.*")){
            return INVALID_NAME_MSG;
        }
        Player currentPlayer = new Player(username);
        if(!playerLobby.addPlayer(username, currentPlayer)) {
            return NAME_TAKEN_MSG;
        }else{
            setSignedIn(true);
            curPlayer = currentPlayer;

            return null;
        }
    }

    public boolean setUpGame(String opponentName){
        Player op = playerLobby.getPlayer(opponentName);
        if(op.getColor() != null){
            return false;
        }else{
            op.setColor(Player.Color.WHITE);
            curPlayer.setOpponent(op);
            curPlayer.setColor(Player.Color.RED);
            op.setOpponent(curPlayer);
            Board board = new Board();
            curPlayer.setBoard(board);
            op.setBoard(board);
            return true;
        }
    }
}
