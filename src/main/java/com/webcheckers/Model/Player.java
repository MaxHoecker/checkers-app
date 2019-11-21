package com.webcheckers.Model;

/**
 * Represents a player (user)
 *
 * @author <a href="jak3703@rit.edu">Jacob Kobrak</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class Player implements Cloneable{

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

    /**
     * Get the game this player is involved in
     * @return a Game
     */
    public Game game(){
        return game;
    }

    /**
     * Assign this player a game to interact with
     * @param game a Game
     */
    public void setGame(Game game){
        this.game = game;
    }

    /**
     * Get this player's name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Assign this player a color
     * @param color color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Get this player's color
     * @return color
     */
    public Color getColor(){
        return color;
    }


    /**
     * Check if this player has resigned from a game they are playing
     * @return a Boolean, null if they are not in game, true if they resigned, false otherwise
     */
    public Boolean getResigned() {
        return resigned;
    }


    /**
     * Update this player's resigned status
     * @param resigned a Boolean; null, true, or false
     */
    public void setResigned(Boolean resigned) {
        this.resigned = resigned;
    }


    /**
     * Check if it is the turn of this player who is in a game
     * @return true if it is this player's turn, false otherwise
     */
    public boolean isMyTurn(){
        if(game == null){
            return false;
        }
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


    public Player clone()throws CloneNotSupportedException{
        Player result = new Player(name);
        result.setColor(null);
        result.setGame(null);
        result.setResigned(null);
        return result;
    }
}
