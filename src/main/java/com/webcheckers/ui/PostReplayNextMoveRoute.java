package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostReplayNextMoveRoute implements Route {
    //attributes
    private Gson gson;

    public PostReplayNextMoveRoute(final Gson gson){
        this.gson = gson;
    }

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
