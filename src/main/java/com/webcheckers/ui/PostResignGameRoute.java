package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Remove a player who is in the middle of a checkers game
 */
public class PostResignGameRoute implements Route {
    private Gson gson;

    /**
     * Constructor
     * @param gson WebServer's instance of Gson
     */
    public PostResignGameRoute(Gson gson) {
        this.gson = gson;
    }

    /**
     * Remove the current user from their game, tell the client if successful
     * @param request HTTP request
     * @param response HTTP response
     * @return message to client in json format
     */
    @Override
    public String handle(Request request, Response response) {

        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");
        boolean removed = playerServices.removeFromGame();
        playerServices.setResigned(true);
        Message endMssg = new Message("Successfully resigned", Message.Type.INFO);
        if(removed){
            playerServices.setWonGame(false, endMssg);
            return gson.toJson(endMssg);
        }
        else{
            return gson.toJson(Message.error("Unable to end"));
        }
    }
}
