package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostResignGameRoute implements Route {
    private Gson gson;

    public PostResignGameRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String handle(Request request, Response response) {

        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");
        boolean removed = playerServices.removeFromGame();
        playerServices.setResigned(true);
        Message endMssg = new Message("Successfully resigned", Message.Type.INFO);
        if(removed){
            playerServices.setWonGame(false, endMssg);
            return gson.toJson(endMssg);
        }
        else{
            return gson.toJson(Message.error("Unable to end"));
        }
    }
}
