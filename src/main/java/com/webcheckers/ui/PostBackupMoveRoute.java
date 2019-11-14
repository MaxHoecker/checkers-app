package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;


public class PostBackupMoveRoute implements Route {

    private Gson gson;

    public PostBackupMoveRoute(final Gson gson){
        this.gson = gson;
    }


    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        playerServices.removeLastMove(); //inputting null here removes most recent in sequence

        return gson.toJson(Message.info(""));
    }
}
