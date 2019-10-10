package com.webcheckers.Model;

/**
 * Represents a player (user)
 *
 * @author <a href="jak3703@rit.edu">Jacob Kobrak</a>
 */
public class Player {

    private String name;
    private Color color;
    private Player opponent;
    private Board board;

    /**
     * Enum used to assign a color to a player
     */
    public enum Color {RED, WHITE}

    /**
     * Construct a player object. All attributes apart from name are null until
     * the player enters a game
     * @param name player's username
     */
    public Player(String name){
        this.name = name;
        color = null;
        opponent = null;
        board = null;
    }

    /**
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param board board
     */
    public void setBoard(Board board){
        this.board = board;
    }

    /**
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @param color color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * @return color
     */
    public Color getColor(){
        return color;
    }

    /**
     * @param op opponent
     */
    public void setOpponent(Player op){
        this.opponent = op;
    }

    /**
     * @return opponent
     */
    public Player getOpponent(){
        return this.opponent;
    }

    /**
     * Returns true if the player's username equals the other player's username
     * @param that player compared to this
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object that){
        if(!(that instanceof Player)) return false;
        if(that == this) return true;
        Player other = (Player)that;
        return other.getName().equals(this.name);
    }

    /**
     * Get a hashcode for this class
     * @return the hashcode of the username
     */
    @Override
    public int hashCode(){
        return this.name.hashCode();
    }
}
