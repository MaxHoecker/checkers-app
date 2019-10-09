package com.webcheckers.Model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.Session;

public class Player {

    private String id;
    private Color color;
    private Player opponent;

    public enum Color {RED, WHITE}

    public Player(String id){
        this.id = id;
        color = null;
        opponent = null;
    }

    public String getId(){
        return this.id;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public void setOpponent(Player op){
        this.opponent = op;
    }

    public Player getOpponent(){
        return this.opponent;
    }

    @Override
    public boolean equals(Object that){
        if(!(that instanceof Player)) return false;
        if(that == this) return true;
        Player other = (Player)that;
        return other.getId().equals(this.id);
    }

    @Override
    public int hashCode(){
        return this.id.hashCode();
    }
}
