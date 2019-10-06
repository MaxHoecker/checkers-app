package com.webcheckers.appl;
import com.webcheckers.Model.Player;
import java.util.Set;
public class PlayerLobby {
    private int numplayers;
    private Set<Player> players;

    public PlayerLobby(){
        numplayers = 0;
    }
    public Set<Player> getPlayers(){
        return players;
    }
    public int getNum(){
        return numplayers;
    }
    // need to add functionality to check before adding
    public void addPlayer(Player x){
        numplayers++;
        players.add(x);
    }
    // add funcitnality to check before deleting
    public void remPlayer(Player x){
        if(numplayers > 0){
            numplayers--;
            players.remove(x);
        }
        else{
            System.out.println("error! No players to remove");
        }
    }
}
