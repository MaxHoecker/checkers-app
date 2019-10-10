package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

public class PostSignoutRoute implements Route {
    //Attributes
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public PostSignoutRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        Session session = request.session();
        Player curPlayer = session.attribute(GetHomeRoute.CUR_PLAYER_ATTR);
        playerLobby.remPlayer(curPlayer);
        session.attribute(GetHomeRoute.CUR_PLAYER_ATTR, null);
        session.attribute(GetHomeRoute.SIGNEDIN, false);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
