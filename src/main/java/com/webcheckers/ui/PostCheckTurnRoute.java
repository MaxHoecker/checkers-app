package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Move;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Session;

public class PostCheckTurnRoute {
    //constants
    static final String ACTION_DATA_PARAM = "actionData";

    //attributes
    private Gson gson;

    public PostCheckTurnRoute(final Gson gson){
        this.gson = gson;
    }

    public String handle(Request request, Response response){

        String param = request.queryParams(ACTION_DATA_PARAM);
        System.err.println(param);
        Move move = gson.fromJson(param, Move.class);
        System.err.println("move: " + move.toString());
        Session curSession = request.session();
        PlayerServices name = curSession.attribute("playerServices");

        boolean x = true;
        //x == true if valid
        //x == false if not

        if(! name.curPlayer().isMyTurn()){
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
