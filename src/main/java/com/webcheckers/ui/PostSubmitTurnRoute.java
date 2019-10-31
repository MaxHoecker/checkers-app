package com.webcheckers.ui;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostSubmitTurnRoute implements Route {

    private Gson gson;

    public PostSubmitTurnRoute(Gson gson){
        this.gson = gson;
    }
    @Override
    public String handle(Request request, Response response){
        return null;
    }
}
