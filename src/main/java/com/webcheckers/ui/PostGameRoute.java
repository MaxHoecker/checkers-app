package com.webcheckers.ui;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class PostGameRoute implements Route {

    //Constants
    static final String OPPONENT_PARAM = "opponent";
    static final String CURRENT_PLAYER_ATTR = "curPlayer";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String VIEW_NAME = "game.ftl";

    //Attributes
    private PlayerLobby lobby;
    private TemplateEngine templateEngine;

    public PostGameRoute(final PlayerLobby lobby, final TemplateEngine templateEngine){
        this.lobby = lobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();
        Session curSession = request.session();

        Player curPlayer = curSession.attribute(GetHomeRoute.CUR_PLAYER_ATTR);
        vm.put("title", "Game Page");

        if(curPlayer.getColor() == null) { // case where curPlayer is the one who clicked
            String opponentId = request.queryParams(OPPONENT_PARAM);
            Player opponent = lobby.getCurrentPlayer(opponentId);
            if(opponent.getColor() != null){ //clicked on opponent is in a game
                response.redirect(WebServer.HOME_URL);
                return null;
            }
            opponent.setColor(Player.Color.WHITE);
            curPlayer.setColor(Player.Color.RED);
            curPlayer.setOpponent(opponent);
            opponent.setOpponent(curPlayer);

            vm.put(CURRENT_PLAYER_ATTR, curPlayer);
            vm.put(RED_PLAYER_ATTR, curPlayer);
            vm.put(WHITE_PLAYER_ATTR, opponent);
            vm.put(ACTIVE_COLOR_ATTR, "red"); //TODO figure out what the "activeColor" attribute is expecting
            //TODO fill out vm stuff, render game page
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

        }else{ //case where curPlayer is the one clicked on
            vm.put(CURRENT_PLAYER_ATTR, curPlayer);
            vm.put(RED_PLAYER_ATTR, curPlayer.getOpponent());
            vm.put(WHITE_PLAYER_ATTR, curPlayer);
            vm.put(ACTIVE_COLOR_ATTR, "white"); //TODO figure out what the "activeColor" attribute is expecting
            //TODO fill out vm stuff, render game page
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}
