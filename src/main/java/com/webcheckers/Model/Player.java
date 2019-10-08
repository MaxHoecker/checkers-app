package com.webcheckers.Model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.Session;

public class Player {

    private String id;
    private Color color;
    private Session session;

    public enum Color {RED, WHITE}

    public Player(String id){
        this.id = id;
        color = null;
    }

    public String getId(){
        return this.id;
    }

    /**
     * Assign a specific session to a player, and give that player their own player services instance
     * @param playerSession: session
     * @param lobby: WebServer's PlayerLobby instance
     */
    public void definePlayerServices(final Session playerSession, final PlayerLobby lobby){
        session = playerSession;
        session.attribute("playerServices", new PlayerServices(lobby));
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
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
