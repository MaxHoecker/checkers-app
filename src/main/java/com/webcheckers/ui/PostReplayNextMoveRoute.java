package com.webcheckers.ui;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostReplayNextMoveRoute implements Route {
    //attributes
    private Gson gson;

    public PostReplayNextMoveRoute(final Gson gson){
        this.gson = gson;
    }

    public String handle(Request request, Response response){
        return null;
    }
}
