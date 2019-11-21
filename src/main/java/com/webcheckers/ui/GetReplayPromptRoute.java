package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Renders the page that asks if you want to save a replay
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class GetReplayPromptRoute implements Route{
    private TemplateEngine templateEngine;

    /**
     * Constructor that takes in the template engine
     * @param templateEngine
     */
    public GetReplayPromptRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    /**
     * renders the page from the replayPrompt.ftl file
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the replay prompt page
     */
    public String handle(Request request, Response response){

        Map<String, Object> vm = new HashMap<>();
        Message mssg = new Message("Would you like to save a replay of your game?", Message.Type.INFO);
        vm.put("title", "Welcome!");
        vm.put("message", mssg);


        return templateEngine.render(new ModelAndView(vm, "replayPrompt.ftl"));
    }
}
