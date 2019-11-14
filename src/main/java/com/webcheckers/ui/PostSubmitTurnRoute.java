package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostSubmitTurnRoute implements Route {

    private Gson gson;

    public PostSubmitTurnRoute(Gson gson){
        this.gson = gson;
    }
    @Override
    public String handle(Request request, Response response){
        System.err.println("Entering SubmitTurnRoute");
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");

        Message msg = playerServices.submitTurn();
        
        return gson.toJson(msg);
    }
}
