package com.webcheckers.ui;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostReplayPreviousMoveRoute implements Route {
    //attributes
    private Gson gson;

    public PostReplayPreviousMoveRoute(final Gson gson){
        this.gson = gson;
    }

    public String handle(Request request, Response response){
        return null;
    }
}
