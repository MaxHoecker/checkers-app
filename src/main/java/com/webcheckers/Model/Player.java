package com.webcheckers.Model;

public class Player {

    private String id;
    private boolean inGame;

    public Player(String id){
        this.id = id;
        this.inGame = false;
    }

    public String getId(){
        return this.id;
    }

    public boolean inGame(){
        return this.inGame;
    }

    public void toggleInGame(){
        this.inGame = !this.inGame;
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
