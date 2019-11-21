package com.webcheckers.ui;

import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * removes you from the game you're currently replaying
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class GetReplayStopRoute implements Route {

    /**
     * removes you from the replay game and redirects you back home
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   null because the session is redirected
     */
    public String handle(Request request, Response response){

        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Message endMssg = new Message("No longer replaying your game", Message.Type.INFO);
        playerServices.setWonGame(null, endMssg);
        playerServices.removeFromGame();
        playerServices.setVisitReplayPage(false);
        response.redirect(WebServer.HOME_URL);
        return null;
    }

}
