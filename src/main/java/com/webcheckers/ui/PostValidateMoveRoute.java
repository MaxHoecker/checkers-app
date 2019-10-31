package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Move;
import com.webcheckers.Model.Position;
import com.webcheckers.Model.Space;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;


import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

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
        Session curSession = request.session();
        PlayerServices name = curSession.attribute("playerServices");
        Board game = name.gameBoard();
        Space start = game.getAtPosition(move.getStart().getCell(), move.getStart().getRow());
        Space end = game.getAtPosition(move.getEnd().getCell(), move.getEnd().getRow());
        boolean validMove = true;
        if(start.isValid() == false) {
            return gson.toJson(Message.error("Invalid Move:Starting Square is Invalid"));
        }
        else if(end.isValid() == false) {
            return gson.toJson(Message.error("Invalid Move:Ending Square is Invalid"));
        }
        else if(start.getOccupant() == null)
        {
            return gson.toJson(Message.error("Invalid Move:No Piece in Starting Position"));
        }
        else if(end.getOccupant() != null)
        {
            return gson.toJson(Message.error("Invalid Move:Target position already occupied"));  
        }
        else
        {
            return gson.toJson(Message.info("Valid Move"));
        }
    }
}
