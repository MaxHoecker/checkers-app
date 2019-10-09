package com.webcheckers.Model;

public class Player {

    private String name;
    private Color color;
    private Player opponent;
    private Board board;

    public enum Color {RED, WHITE}

    public Player(String name){
        this.name = name;
        color = null;
        opponent = null;
        board = null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public String getName(){
        return this.name;
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
        return other.getName().equals(this.name);
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}
