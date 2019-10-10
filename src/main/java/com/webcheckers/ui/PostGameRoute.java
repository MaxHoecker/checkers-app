package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PostGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostGameRoute.class.getName());


    //Constants
    static final String OPPONENT_PARAM = "opponent";
    static final String CUR_USER_ATTR = "currentUser";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String VIEW_NAME = "game.ftl";
    static final String VIEW_MODE = "viewMode";



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
            LOG.info(curPlayer.getName() + " clicked on " + opponentId);
            if(opponent.getColor() != null){ //clicked on opponent is in a game
                response.redirect(WebServer.HOME_URL);
                return null;
            }
            opponent.setColor(Player.Color.WHITE);
            curPlayer.setColor(Player.Color.RED);
            curPlayer.setOpponent(opponent);
            opponent.setOpponent(curPlayer);
            Board board = new Board();
            curPlayer.setBoard(board);
            opponent.setBoard(board);

            vm.put(VIEW_MODE, "");
            vm.put(CUR_USER_ATTR, curPlayer);
            vm.put(RED_PLAYER_ATTR, curPlayer);
            vm.put(WHITE_PLAYER_ATTR, opponent);
            vm.put(ACTIVE_COLOR_ATTR, "red");
            LOG.info(curPlayer.getBoard().toString());
            vm.put(BOARD_ATTR, curPlayer.getBoard());
            //TODO fill out vm stuff, render game page
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

        }else{ //case where curPlayer is the one clicked on
            vm.put(VIEW_MODE, "");
            vm.put(CUR_USER_ATTR, curPlayer);
            vm.put(RED_PLAYER_ATTR, curPlayer.getOpponent());
            vm.put(WHITE_PLAYER_ATTR, curPlayer);
            vm.put(ACTIVE_COLOR_ATTR, "red");
            LOG.info(curPlayer.getBoard().toString());
            vm.put(BOARD_ATTR, curPlayer.getBoard());
            //TODO fill out vm stuff, render game page
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}
