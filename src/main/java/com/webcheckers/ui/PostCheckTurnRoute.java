package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Move;
import com.webcheckers.appl.*;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class PostCheckTurnRoute implements Route {
    //constants
    static final String ACTION_DATA_PARAM = "actionData";

    //attributes
    private Gson gson;

    public PostCheckTurnRoute(final Gson gson){
        this.gson = gson;
    }
    @Override
    public String handle(Request request, Response response){
        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");

        boolean x = true;
        //x == true if valid
        //x == false if not

        if(!playerServices.curPlayer().isMyTurn()){
            x = false;
        }

        if(x)
        {
            return gson.toJson( Message.info("true"));
        }
        else
        {
            return gson.toJson(Message.info("false"));
        }
    }
}
