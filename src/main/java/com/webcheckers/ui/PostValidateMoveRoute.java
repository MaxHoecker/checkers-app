package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.Model.Move;
import com.webcheckers.Model.Position;
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
        /* We can get individual json elements this way as well
            we aren't since gson is already injected, making testing easier.

        JsonObject obj = new JsonParser().parse(param).getAsJsonObject();
        int row = obj.getAsJsonObject("start").get("row").getAsInt();
        System.err.println("row: "+ row);
         */
        response.body("ERROR");
        return null;
    }
}
