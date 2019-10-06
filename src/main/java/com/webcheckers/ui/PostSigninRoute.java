package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostSigninRoute implements Route {

    //Attributes
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR, GetSigninRoute.TITLE);
        vm.put(GetHomeRoute.NEW_PLAYET_ATTR, GetSigninRoute.VIEW_NAME);

        final Session session = request.session();



        return null;
    }
}
