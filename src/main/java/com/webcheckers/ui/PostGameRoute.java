package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Color;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * UI Controller for rendering the game page
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 * @author <a href='mjh9131@rit.edu'>Max Hoecker</a>
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

    static final String ALL_CAPTURED_MSG = "%s has captured all of the pieces.";


    //Attributes
    private Gson gson;
    private TemplateEngine templateEngine;

    /**
     * Construct PostGameRoute
     * @param gson WebServer's Gson instance
     * @param templateEngine WebServer's TemplateEngine instance
     */
    public PostGameRoute(final Gson gson, final TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
        this.gson = gson;
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
        LOG.info("entering postGameRoute");
        Map<String, Object> vm = new HashMap<>();
        Session curSession = request.session();

        PlayerServices playerServices = curSession.attribute("playerServices");

        vm.put("title", "Game Page");
        Map<String, Object> modeOptions = new HashMap<>(2);

        if(playerServices.curPlayer().game() != null){ //getting game route

            vm.put(CUR_USER_ATTR, playerServices.curPlayer());
            vm.put(RED_PLAYER_ATTR, playerServices.redPlayer());
            vm.put(WHITE_PLAYER_ATTR, playerServices.whitePlayer());
            vm.put(BOARD_ATTR, playerServices.gameBoard());
            vm.put(VIEW_MODE, "PLAY");
            if((playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.RED) ||
                (!playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.WHITE)){
                vm.put(ACTIVE_COLOR_ATTR, "RED");
            }else if((playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.WHITE) ||
                (!playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.RED)){
                vm.put(ACTIVE_COLOR_ATTR, "WHITE");
            }

            if(playerServices.opponent() == null){
                playerServices.setVisitReplayPage(true);
                Message winMssg = new Message("Your opponent left the game, so you win by default!", Message.Type.INFO);
                playerServices.setWonGame(true, winMssg);
                playerServices.setResigned(false);

                response.redirect(WebServer.HOME_URL);
                return null;
            }

            //for reconstructing the game for the replay mode
            playerServices.saveRed();
            playerServices.saveWhite();

            if(playerServices.curPlayer().game().numRedPieces() == 0){
                String endtext = String.format(ALL_CAPTURED_MSG, playerServices.whitePlayer().getName());
                Message winMssg = new Message(endtext, Message.Type.INFO);
                playerServices.setVisitReplayPage(true);
                if(playerServices.curPlayer().getColor() == Color.WHITE){

                    //ToDo
                    playerServices.setWonGame(true, winMssg);
                }
                else{
                    playerServices.setWonGame(false, winMssg);
                }
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOverMessage", playerServices.getGameEndMessage().getText());
                vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            }
            else if(playerServices.curPlayer().game().numWhitePieces() == 0){
                String endtext = String.format(ALL_CAPTURED_MSG, playerServices.redPlayer().getName());
                Message winMssg = new Message(endtext, Message.Type.INFO);
                playerServices.setVisitReplayPage(true);
                if(playerServices.curPlayer().getColor() == Color.RED){
                    playerServices.setWonGame(true, winMssg);
                }
                else{
                    playerServices.setWonGame(false, winMssg);
                }
                modeOptions.put("isGameOver", true);
                modeOptions.put("gameOVerMessage", playerServices.getGameEndMessage().getText());
                vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
            }
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        if(playerServices.curPlayerColor() == null){ // case where curPlayer is the one who clicked
            String opponentId = request.queryParams(OPPONENT_PARAM);
            LOG.info(playerServices.curPlayerName() + " clicked on " + opponentId);
            LOG.info(request.queryParams().toString());


            if(!playerServices.setUpGame(opponentId)){
                System.err.println(opponentId + " is in a game.");
                response.redirect(WebServer.HOME_URL);
                return null;
            }

            playerServices.saveRed();
            playerServices.saveWhite();

            playerServices.setViewMode("PLAY");
            vm.put(VIEW_MODE, playerServices.getViewMode());
            vm.put(CUR_USER_ATTR, playerServices.curPlayer());
            vm.put(RED_PLAYER_ATTR, playerServices.curPlayer());
            vm.put(WHITE_PLAYER_ATTR, playerServices.opponent());
            vm.put(ACTIVE_COLOR_ATTR, "RED");
            LOG.info(playerServices.gameBoard().toString());
            vm.put(BOARD_ATTR, playerServices.gameBoard());
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));

        }else{ //case where curPlayer is the one clicked on


            playerServices.setViewMode("PLAY");
            vm.put(VIEW_MODE, playerServices.getViewMode());
            vm.put(CUR_USER_ATTR, playerServices.curPlayer());
            vm.put(RED_PLAYER_ATTR, playerServices.opponent());
            vm.put(WHITE_PLAYER_ATTR, playerServices.curPlayer());
            vm.put(ACTIVE_COLOR_ATTR, "RED");
            LOG.info(playerServices.gameBoard().toString());
            vm.put(BOARD_ATTR, playerServices.gameBoard());
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }
    }
}
