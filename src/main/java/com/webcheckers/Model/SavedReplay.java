package com.webcheckers.Model;
import java.util.ArrayList;

/**
 * The saved replay objects
 *
 *  * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class SavedReplay {
    private Game savedInitialGame;
    private ArrayList<Move> moveListSaved;
    private int replayNum;

    /**
     * Constructor
     *
     * @param savedInitialGame the initial game state with you and your opponent
     * @param moveListSaved the array of moves that were made in the game
     * @param replayNum the replay id to identify the replay
     */
    public SavedReplay(Game savedInitialGame, ArrayList<Move> moveListSaved, int replayNum) {
        this.replayNum = replayNum;
        this.savedInitialGame = savedInitialGame;
        this.moveListSaved = moveListSaved;
    }

    /**
     * gets the array of moves made
     * @return the array of moves made
     */
    public ArrayList<Move> getMoveListSaved() {
        return moveListSaved;
    }

    /**
     * gets the saved initial game state
     * @return the savedInitialGame
     */
    public Game getSavedInitialGame(){
        return savedInitialGame;
    }

    /**
     * gets the replay ID number
     * @return the replayNum
     */
    public int getReplayNum() {
        return replayNum;
    }

    /**
     * toString gives the ID num of the replay
     * @return the string of the id num
     */
    @Override
    public String toString() {
        return String.valueOf(getReplayNum());
    }
}
