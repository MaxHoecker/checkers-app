package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * displays the game that was selected to replay
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class PostReplayGameRoute implements Route {

    static final String REPLAYING_PARAM = "replay";

    //Attributes
    private TemplateEngine templateEngine;
    private Gson gson;

    /**
     * Constructor that saves the template engine and the gson message
     *
     * @param templateEngine
     * @param gson
     */
    public PostReplayGameRoute(TemplateEngine templateEngine, final Gson gson) {
        this.templateEngine = templateEngine;
        this.gson = gson;
    }

    /**
     * displays the replay that you selected
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered game.ftl file
     */
    public String handle(Request request, Response response) {

        Session session = request.session();
        PlayerServices playerServices = session.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();
        Map<String, Object> modeOptions = new HashMap<>(2);

        vm.put("title", "REPLAYING");

        if(playerServices.curPlayer().game() == null) { //initial entry to spectator mode
            String replayId = request.queryParams(REPLAYING_PARAM);
            playerServices.enterReplay(Integer.parseInt(replayId));
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
