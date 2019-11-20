package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The UI Controller to GET the spectator page
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class GetSpectatorRoute implements Route {

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    /**
     * Constructor
     * @param playerLobby WebServer's one instance of PlayerLobby
     * @param templateEngine WebServer's one instance of FreeMarkerEngine
     */
    public GetSpectatorRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Render the spectator page where players choose a player to spectate (as opposed to play against)
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered page
     */
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        vm.put("playerList", playerLobby.playersInGame());
        vm.put("title", "Welcome!");
        vm.put("currentUser", playerServices.curPlayer());

        return templateEngine.render(new ModelAndView(vm, "spectator.ftl"));
    }
}
