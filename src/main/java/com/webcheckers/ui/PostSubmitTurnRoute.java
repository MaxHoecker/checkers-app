package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Called when client clicks the Submit Turn button in the game view
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostSubmitTurnRoute implements Route {

    private Gson gson;

    /**
     * Constructor
     * @param gson WebServer's instance of Gson
     */
    public PostSubmitTurnRoute(Gson gson){
        this.gson = gson;
    }

    /**
     * Attempt to submit turn, tell the client if successful or not
     * @param request HTTP request
     * @param response HTTP response
     * @return message to client, in json format
     */
    @Override
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        Message msg = playerServices.submitTurn();
        
        return gson.toJson(msg);
    }
}
