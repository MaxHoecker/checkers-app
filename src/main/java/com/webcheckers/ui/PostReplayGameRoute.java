package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostReplayGameRoute implements Route {

    private TemplateEngine templateEngine;

    public PostReplayGameRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response) {
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "SPECTATING");

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

}
