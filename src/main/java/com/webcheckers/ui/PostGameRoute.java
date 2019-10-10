package com.webcheckers.ui;

import com.webcheckers.Model.Board;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * UI Controller for rendering the game page
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
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

    /**
     * Construct PostGameRoute
     * @param lobby WebServer's PlayerLobby instance
     * @param templateEngine WebServer's TemplateEngine instance
     */
    public PostGameRoute(final PlayerLobby lobby, final TemplateEngine templateEngine){
        this.lobby = lobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Render the game page for both the player who clicks on another player and the player
     * that got clicked on
     * @param request HTTP request
     * @param response HTTP response
     * @return a string used by outside code to render the page
     */
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
