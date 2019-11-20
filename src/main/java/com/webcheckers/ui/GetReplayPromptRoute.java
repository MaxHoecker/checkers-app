package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetReplayPromptRoute implements Route{
    private TemplateEngine templateEngine;

    public GetReplayPromptRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response){

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        return templateEngine.render(new ModelAndView(vm, "replayPrompt.ftl"));
    }
}
