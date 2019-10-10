package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;


import java.util.HashMap;
import java.util.Map;

/**
 * UI Controller for rendering the home or sign-in pages depending upon how the sign-in goes
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class PostSigninRoute implements Route {

    //Constants
    static final String USERNAME_PARAM = "signinField";


    static final String INVALID_NAME_MSG = "Name missing an alphanumeric character. Please enter another name.";
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";


    //Attributes
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    /**
     * Construct PostSigninRoute
     * @param playerLobby WebServer's PlayerLobby instance
     * @param templateEngine WebServer's TemplateEngine instance
     */
    public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    /**
     * Render the home page upon a successful sign in, re-render the sign-in page upon a
     * failed sign in
     * @param request HTTP request
     * @param response HTTP response
     * @return a string used by outside code to render the page
     */
    @Override
    public String handle(Request request, Response response) {

        Map<String, Object> vm = new HashMap<>();
        Session curSession = request.session();

        vm.put("title", GetSigninRoute.TITLE);
        vm.put("page", WebServer.SIGNIN_URL);

        String username = request.queryParams(USERNAME_PARAM);
        Player currentPlayer = new Player(username);
        if(!username.matches(".*\\w+.*")){ // <- regex: at least 1 alphanumeric char with any # of chars before and after
            vm.put("message", Message.error(INVALID_NAME_MSG));
            return templateEngine.render(new ModelAndView(vm, GetSigninRoute.VIEW_NAME));
        }
        else if(!playerLobby.addPlayer(username, currentPlayer)) {
            vm.put("message", Message.error(NAME_TAKEN_MSG));
            return templateEngine.render(new ModelAndView(vm, GetSigninRoute.VIEW_NAME));
        }else{
            curSession.attribute(GetHomeRoute.SIGNEDIN, Boolean.TRUE);
            curSession.attribute(GetHomeRoute.CUR_PLAYER_ATTR, currentPlayer);

            response.redirect(WebServer.HOME_URL);
            return null;
        }

    }
}
