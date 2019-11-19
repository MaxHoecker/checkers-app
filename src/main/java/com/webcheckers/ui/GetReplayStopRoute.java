package com.webcheckers.ui;

import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

public class GetReplayStopRoute implements Route {

    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Message endMssg = new Message("No longer replaying your game", Message.Type.INFO);
        playerServices.setWonGame(null, endMssg);
        playerServices.removeFromGame();
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
