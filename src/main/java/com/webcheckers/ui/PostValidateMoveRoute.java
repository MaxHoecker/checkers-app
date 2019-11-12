package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.Model.Move;
import com.webcheckers.Model.Position;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostValidateMoveRoute implements Route {

    //constants
    static final String ACTION_DATA_PARAM = "actionData";

    //attributes
    private Gson gson;

    public PostValidateMoveRoute(final Gson gson){
        this.gson = gson;
    }

    public String handle(Request request, Response response){

        String param = request.queryParams(ACTION_DATA_PARAM);
        System.err.println(param);
        Move move = gson.fromJson(param, Move.class);
        System.err.println("move: " + move.toString());


        boolean x = true;
        //x == true if valid
        //x == false if not
        if(x)
        {
            return gson.toJson( new Message("Valid Move", Message.Type.INFO));
        }
        else
        {
            //ToDo include why it's invalid
            return gson.toJson(new Message("Invalid Move", Message.Type.ERROR));
        }
    }
}
