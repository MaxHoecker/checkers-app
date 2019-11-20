package com.webcheckers.ui;

import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostReplayPromptResponseRoute implements Route {

    static final String REPLAYING_PARAM = "answer";

    public PostReplayPromptResponseRoute() { }


    /**
     * Render the home page upon a successful sign in, re-render the sign-in page upon a
     * failed sign in
     * @param request HTTP request
     * @param response HTTP response
     * @return a string used by outside code to render the page
     */
    @Override
    public String handle(Request request, Response response) {

        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");

        String answer = request.queryParams(REPLAYING_PARAM);
        boolean result = Boolean.parseBoolean(answer);
        if (result) {
            playerServices.saveReplay();
        }
        playerServices.setVisitReplayPage(false);
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
