package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Renders the page that shows your saved replay
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class GetReplayRoute implements Route {
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    /**
     * Constructor that takes in the template engine and player lobby

     * @param playerLobby
     * @param templateEngine
     */
    public GetReplayRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * renders the page from the replaySelection.ftl file
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the replay selection page
     */
    public String handle(Request request, Response response){
        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", "Welcome!");
        vm.put("currentUser", playerServices.curPlayer());
        vm.put("hasSaved", playerServices.hasSaved());


        return templateEngine.render(new ModelAndView(vm, "replaySelection.ftl"));
    }
}
