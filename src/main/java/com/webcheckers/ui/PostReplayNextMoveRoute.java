package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * does the next move in the saved move list
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class PostReplayNextMoveRoute implements Route {

    //attributes
    private Gson gson;

    /**
     * Constructor that saves the passed in gson
     * @param gson
     */
    public PostReplayNextMoveRoute(final Gson gson){
        this.gson = gson;
    }

    /**
     * does the next move in the saved move list
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   a message showing true if the game successfully made the next move that was saved
     */
    public String handle(Request request, Response response){
        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");
        boolean next = playerServices.setNextMove();

        if(next){
            return gson.toJson(Message.info("true"));
        }
        else{
            return gson.toJson(Message.error("No moves left"));
        }
    }
}
