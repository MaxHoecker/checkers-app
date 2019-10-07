package com.webcheckers.appl;
import com.webcheckers.Model.Player;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class PlayerLobby {
    private int numPlayers;
    private Set<Player> players;

    public PlayerLobby(){
        numPlayers = 0;
        players = new TreeSet<Player>((o1, o2) -> o1.getId().compareTo(o2.getId()));
    }
    public synchronized Set<Player> getPlayers(){
        return players;
    }
    public synchronized int getNum(){
        return numPlayers;
    }
    // TODO add functionality to check before adding
    public synchronized boolean addPlayer(Player x){
        boolean added = players.add(x);
        if(added){
            numPlayers++;
        }
        return added;
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
        return players.contains(player);
    }
}
