package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostReplayGameRoute implements Route {

    static final String REPLAYING_PARAM = "replaying";

    private TemplateEngine templateEngine;
    private Gson gson;

    public PostReplayGameRoute(TemplateEngine templateEngine, final Gson gson) {
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    public String handle(Request request, Response response) {

        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        Map<String, Object> modeOptions = new HashMap<>(2);

        vm.put("title", "REPLAYING");

        if(playerServices.curPlayer().game() == null) { //initial entry to spectator mode
            //String replayId = request.queryParams(REPLAYING_PARAM);
            playerServices.enterReplay();
            playerServices.setViewMode("REPLAY");
        }

        modeOptions.put("hasNext", playerServices.hasNext());
        modeOptions.put("hasPrevious", playerServices.hasPrevious());
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));

        vm.put("currentUser", playerServices.curPlayer());
        vm.put("viewMode", playerServices.getViewMode());
        vm.put("redPlayer", playerServices.redPlayer());
        vm.put("whitePlayer", playerServices.whitePlayer());
        vm.put("activeColor", playerServices.curTurnColor().name());
        vm.put("board", playerServices.gameBoard());
        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }

}
