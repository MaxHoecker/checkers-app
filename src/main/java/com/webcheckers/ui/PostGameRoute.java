package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostGameRoute implements Route {

    //Constants
    static final String RED_PLAYER_PARAM = "redPlayer";
    static final String WHITE_PLAYER_PARAM = "whitePlayer";

    //Attributes
    private PlayerLobby lobby;

    public PostGameRoute(final PlayerLobby lobby){
        this.lobby = lobby;
    }

    @Override
    public String handle(Request request, Response response){
        return null;
    }
}
