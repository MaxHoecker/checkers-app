package com.webcheckers.ui;

import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The UI Controller for removing a player from a game they are spectating. Redirects users to home page
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class GetSpectatorStopRoute implements Route {

    /**
     * Remove spectators from whichever game they are spectating and bring them to the home page
     * @param request HTTP request
     * @param response HTTP response
     * @return null
     */
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Message endMssg = new Message("No longer a spectator", Message.Type.INFO);
        playerServices.setWonGame(null, endMssg);
        playerServices.removeFromGame();
        playerServices.setVisitReplayPage(false);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
