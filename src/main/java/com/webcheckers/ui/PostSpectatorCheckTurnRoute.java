package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Check if there has been a turn made in the checkers game being spectated since the last time the spectator's view
 * was updated
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostSpectatorCheckTurnRoute implements Route {

    private Gson gson;

    /**
     * Constructor
     * @param gson WebServer's instance of Gson
     */
    public PostSpectatorCheckTurnRoute(Gson gson){
        this.gson = gson;
    }

    /**
     * Tell the client it should re-render the game based on whether there has been an update in the game state
     * @param request HTTP request
     * @param response HTTP reponse
     * @return message to client, in json format
     */
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        Message msg = playerServices.spectatorCheckTurn();
        return gson.toJson(msg);
    }
}
