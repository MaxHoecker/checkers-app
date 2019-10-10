package com.webcheckers.appl;
import com.webcheckers.Model.Player;

import java.util.*;

/**
 * The Lobby object that stores all the logged in players
 *  @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class PlayerLobby {

    /**
     * Attributes
     */
    private int numPlayers;
    private HashMap<String, Player> players;

    /**
     * Constructor
     */
    public PlayerLobby(){
        numPlayers = 0;
        players = new HashMap<String, Player>();
    }

    /**
     * gets a set of all the player objects in the lobby
     * @return Set of Players
     */
    public synchronized Set<Player> getPlayers(){
        Set<Player> playerList = new HashSet<>(players.values());
        return playerList;
    }

    /**
     * gets a set of all the player's id Strings in the lobby
     * @return set of player names
     */
    public synchronized Set<String> getPlayersString(){
        Set<Player> playerList = new HashSet<>(players.values());
        Set<String> playerListString = new HashSet<>();
        for (Player player : playerList){
            playerListString.add(player.getName());
        }
        return playerListString;
    }

    /**
     * gets the number of players in the lobby
     * @return int of num players
     */
    public synchronized int getNumPlayers(){
        return numPlayers;
    }

    /**
     * when given a string of the Player's username, retrieves that Player object
     * @param s the player's username
     * @return the player object associated with the username string
     */
    public synchronized Player getCurrentPlayer(String s){
        return players.get(s);
    }

    /**
     * when given a player object and it's username, it adds them to the player lobby
     * @param s the Player's username
     * @param p the player object
     * @return true if the player was added, otherwise false
     */
    public synchronized boolean addPlayer(String s, Player p){
        if (players.containsKey(s)){
            return false;
        }
        else{
            players.put(s, p);
            numPlayers++;
            return true;
        }
    }

    /**
     * removes a player from the lobby
     * @param x the player to be removed
     */
    public synchronized void remPlayer(Player x){
        if(numPlayers > 0){
            numPlayers--;
            players.remove(x.getName());
        }
        else{
            System.out.println("error! No players to remove");
        }
    }

    /**
     * determines if a player exists in a lobby
     * @param player the player to be checked
     * @return true if its in the lobby, otherwise false
     */
    public synchronized boolean containsPlayer(Player player){
        return players.containsValue(player);
    }
}
