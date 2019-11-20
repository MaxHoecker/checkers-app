package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostReplayGameRoute implements Route {

    static final String REPLAYING_PARAM = "replaying";

    private TemplateEngine templateEngine;

    public PostReplayGameRoute(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response) {
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "REPLAYING");

        if(playerServices.curPlayer().game() == null) { //initial entry to spectator mode
            //String replayId = request.queryParams(REPLAYING_PARAM);
            playerServices.enterReplay();
            playerServices.setViewMode("REPLAY");
        }
            vm.put("currentUser", playerServices.curPlayer());
            vm.put("viewMode", playerServices.getViewMode());
            vm.put("redPlayer", playerServices.redPlayer());
            vm.put("whitePlayer", playerServices.whitePlayer());
            vm.put("activeColor", playerServices.curTurnColor().name());
            vm.put("board", playerServices.gameBoard());
            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

}
