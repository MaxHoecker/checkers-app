package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

/**
 * UI Controller for signing out
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostSignoutRoute implements Route {
    //Attributes
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    /**
     * Construct PostSignoutRoute
     * @param playerLobby WebServer's PlayerLobby instance
     * @param templateEngine WebServer's TemplateEngine instance
     */
    public PostSignoutRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Remove curPlayer from playerlobby, send them back to the home page
     * @param request
     * @param response
     * @return
     */
    @Override
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        playerLobby.remPlayer(playerServices.curPlayerName());
        playerServices.setCurPlayer(null);
        playerServices.setSignedIn(false);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
