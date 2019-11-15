package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostReplayRoute implements Route {
    private Gson gson;
    private TemplateEngine templateEngine;

    public PostReplayRoute(Gson gson, TemplateEngine templateEngine) {
        this.gson = gson;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");

        return templateEngine.render(new ModelAndView(vm, GetSigninRoute.VIEW_NAME));
    }

}
