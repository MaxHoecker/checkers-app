package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostSigninRoute implements Route {

    //Constants
    static final String USERNAME_PARAM = "signinField";

    static final String MSG_ATTR = "message";
    static final String INIT_SIGNIN_MSG = "Make sure your username has at least 1 alphanumeric character!";
    static final String INVALID_NAME_MSG = "Name missing an alphanumeric character. Please enter another name.";
    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String BACK_LINK = " <a href=\"signin\">BACK</a> ";


    //Attributes
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    public PostSigninRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    @Override
    public String handle(Request request, Response response) {
        final Map<String, Object> vm = new HashMap<>();

        String username = request.queryParams(USERNAME_PARAM);
        Player currentPlayer = new Player(username);
        if(!username.matches(".*\\w+.*")){ // <- regex: at least 1 alphanumeric char with any # of chars before and after
            vm.put(MSG_ATTR, Message.error(INVALID_NAME_MSG + BACK_LINK));
            return templateEngine.render(new ModelAndView(vm, "message.ftl"));
        }
        else if(!playerLobby.addPlayer(currentPlayer)) {
            vm.put(MSG_ATTR, Message.error(NAME_TAKEN_MSG + BACK_LINK));
            return templateEngine.render(new ModelAndView(vm, "message.ftl"));
        }else{

        }



        return null;
    }
}
