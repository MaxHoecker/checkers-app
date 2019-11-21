package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;


import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * Called whenever the client tries to move a piece somewhere on the board in the game view.
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostValidateMoveRoute implements Route {

    //constants
    static final String ACTION_DATA_PARAM = "actionData";

    //attributes
    private Gson gson;

    /**
     * Constructor
     * @param gson WebServer's instance of Gson
     */
    public PostValidateMoveRoute(final Gson gson){
        this.gson = gson;
    }

    /**
     * Tell the client if the move made was valid or not
     * @param request HTTP request
     * @param response HTTP response
     * @return message to client, in json format
     */
    @Override
    public String handle(Request request, Response response){
        String param = request.queryParams(ACTION_DATA_PARAM);
        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");

        Message msg = playerServices.validateMove(param);
        return gson.toJson(msg);
    }
}
