package com.webcheckers.ui;

import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * proccesses whether or not to save a replay of the last game based on what button they clicked
 *
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
 */
public class PostReplayPromptResponseRoute implements Route {

    //true if they wanted to save the replay, false otherwise
    static final String REPLAYING_PARAM = "answer";

    /**
     * constructor
     */
    public PostReplayPromptResponseRoute() { }


    /**
     * proccesses saving the replay
     *
     * @param request HTTP request
     * @param response HTTP response
     * @return null, due to a redirect to the home page
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
