package com.webcheckers.ui;

import com.webcheckers.Model.Color;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * UI Controller for rendering the game page when the user is a spectator
 */
public class PostSpectatorGameRoute implements Route{

    static final String SPECTATING_PARAM = "spectating";

    private TemplateEngine templateEngine;

    /**
     * Constructor
     * @param templateEngine WebServer's instance of FreeMarkerEngine
     */
    public PostSpectatorGameRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    /**
     * Render the game page for a spectator. Includes procedure for setting up a player's spectator status
     * @param request HTTP request
     * @param response HTTP response
     * @return the rendered game page
     */
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
            if(playerServices.curPlayer().game().getPlayer(Color.RED) == null || playerServices.curPlayer().game().getPlayer(Color.WHITE) == null){
                response.redirect(WebServer.SPECTATOR_STOP_URL);
                return null;
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
}
