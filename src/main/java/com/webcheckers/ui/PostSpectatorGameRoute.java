package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostSpectatorGameRoute implements Route{

    static final String SPECTATING_PARAM = "spectating";

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public PostSpectatorGameRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "SPECTATING");

        if(playerServices.curPlayer().game() == null){ //initial entry to spectator mode
            String spectatingId = request.queryParams(SPECTATING_PARAM);
            playerServices.becomeSpectator(spectatingId);

            vm.put("currentUser", playerServices.curPlayer());
            playerServices.setViewMode("SPECTATOR");
            vm.put("viewMode", playerServices.getViewMode());
            vm.put("redPlayer", playerServices.redPlayer());
            vm.put("whitePlayer", playerServices.whitePlayer());
            vm.put("activeColor", playerServices.curTurnColor().name());
            vm.put("board", playerServices.gameBoard());
            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        }else{
            vm.put("currentUser", playerServices.curPlayer());
            vm.put("viewMode", playerServices.getViewMode());
            vm.put("redPlayer", playerServices.redPlayer());
            vm.put("whitePlayer", playerServices.whitePlayer());
            vm.put("activeColor", playerServices.curTurnColor().name());
            vm.put("board", playerServices.gameBoard());
            return templateEngine.render(new ModelAndView(vm, "game.ftl"));
        }
    }
}
