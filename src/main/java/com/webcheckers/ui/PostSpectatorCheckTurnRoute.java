package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSpectatorCheckTurnRoute implements Route {

    private Gson gson;

    public PostSpectatorCheckTurnRoute(Gson gson){
        this.gson = gson;
    }

    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        Message msg = playerServices.spectatorCheckTurn();
        return gson.toJson(msg);
    }
}
