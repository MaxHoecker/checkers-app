package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;


/**
 * Called by client when user want to undo a move made on the checker board
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostBackupMoveRoute implements Route {

    private Gson gson;

    /**
     * Constructor
     * @param gson WebServer's instance of Gson, used to send info to the client script
     */
    public PostBackupMoveRoute(final Gson gson){
        this.gson = gson;
    }


    /**
     * Removes the last move the user made from the record and sends an all clear message to the client
     * @param request HTTP request
     * @param response HTTP response
     * @return an all-clear message in json format
     */
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        playerServices.removeLastMove();

        return gson.toJson(Message.info(""));
    }
}
