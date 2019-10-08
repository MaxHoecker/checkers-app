package com.webcheckers.appl;
import com.webcheckers.Model.Player;

import java.util.*;

public class PlayerLobby {
    private int numPlayers;
    private HashMap<String, Player> players;

    public PlayerLobby(){
        numPlayers = 0;
        players = new HashMap<String, Player>();
    }
    public synchronized Set<Player> getPlayers(){
        Set<Player> playerList = new HashSet<>(players.values());
        return playerList;
    }

    public synchronized Set<String> getPlayersString(){
        Set<Player> playerList = new HashSet<>(players.values());
        Set<String> playerListString = new HashSet<>();
        for (Player player : playerList){
            playerListString.add(player.getId());
        }
        return playerListString;
    }

    public synchronized int getNumPlayers(){
        return numPlayers;
    }

    public synchronized Player getCurrentPlayer(String s){
        return players.get(s);
    }

    // TODO add functionality to check before adding
    public synchronized boolean addPlayer(String s, Player x){
        if (players.containsKey(s)){
            return false;
        }
        else{
            players.put(s, x);
            numPlayers++;
            return true;
        }
    }
    //TODO add functionality to check before deleting
    public synchronized void remPlayer(Player x){
        if(numPlayers > 0){
            numPlayers--;
            players.remove(x);
        }
        else{
            System.out.println("error! No players to remove");
        }
    }

    public synchronized boolean containsPlayer(Player player){
        return players.containsValue(player);
    }

    //@Override
    //public String toString() {

    //}
}
