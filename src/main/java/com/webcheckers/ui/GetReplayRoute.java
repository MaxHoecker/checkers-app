package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetReplayRoute implements Route {
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public GetReplayRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Welcome!");
        vm.put("currentUser", playerServices.curPlayer());


        return templateEngine.render(new ModelAndView(vm, "replaySelection.ftl"));
    }
}
