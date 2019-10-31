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
        Space start = game.getAtPosition(move.getStart().getRow(), move.getStart().getCell() );
        Space end = game.getAtPosition(move.getEnd().getRow(), move.getEnd().getCell());
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
            /*we should implement method to measure distance between start and end cells(aka valid simple move, invalid move(moving more than one space at a time), or a valid jump)
            if(start.distance(end)==1){
                return gson.toJson(Message.info("Valid Move"));
            }
            else if(start.distance(end) == 3){
                int mrow = move.getStart().getRow() + (move.getStart().getRow() - move.getEnd().getRow())/2;
                int mcell = move.getStart().getCell() + (move.getStart().getCell() - move.getEnd().getCell())/2;
                Space mid = game.getAtPosition(mrow, mcell);
                if(mid.getOcuupant() != null && mid.getOccupant().getColor() == name.curPLayer.getColor()){
                    return gson.toJson(Message.info("Valid Move"));
                }
                else{
                    return gson.toJson(Message.error("Invalid move:Jumped piece is wrong color or non-existant"));  
                }
            }
            else{
                return gson.toJson(Message.error("Invalid Move: Invalid distance"));  
            }
            */
            return gson.toJson(Message.info("Valid Move"));
        }
    }
}
