package com.webcheckers.Model;

/**
 * Represents a player (user)
 *
 * @author <a href="jak3703@rit.edu">Jacob Kobrak</a>
 */
public class Player {

    private String name;
    private Color color;
    private Game game;
    private Boolean resigned;


    /**
     * Construct a player object. All attributes apart from name are null until
     * the player enters a game
     * @param name player's username
     */
    public Player(String name){
        this.name = name;
        color = null;
        game = null;
        resigned = null;
    }

    public Game game(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
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


    public Boolean getResigned() {
        return resigned;
    }


    public void setResigned(Boolean resigned) {
        this.resigned = resigned;
    }



    public boolean isMyTurn(){
        if(game.getCurrentPlayerColor() == color){
            return true;
        }
        return false;
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
