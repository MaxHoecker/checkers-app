package com.webcheckers.ui;

import com.webcheckers.Model.Color;
import com.webcheckers.appl.PlayerServices;
import spark.*;

import java.util.HashMap;
import java.util.Map;


public class GetGameRoute implements Route {

    //attributes
    private TemplateEngine templateEngine;

    //constants
    static final String CUR_USER_ATTR = "currentUser";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String VIEW_MODE = "viewMode";
    static final String VIEW_NAME = "game.ftl";



    public GetGameRoute(final TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String handle(Request request, Response response){
        Session curSession = request.session();
        PlayerServices playerServices = curSession.attribute("playerServices");
        Map<String, Object> vm = new HashMap<>();

        vm.put(CUR_USER_ATTR, playerServices.curPlayer());
        vm.put(RED_PLAYER_ATTR, playerServices.redPlayer());
        vm.put(WHITE_PLAYER_ATTR, playerServices.whitePlayer());
        vm.put(BOARD_ATTR, playerServices.gameBoard());
        vm.put(VIEW_MODE, playerServices.getViewMode());
        if((playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.RED) ||
                (!playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.WHITE)){
            vm.put(ACTIVE_COLOR_ATTR, "RED");
        }else if((playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.WHITE) ||
                (!playerServices.curPlayer().isMyTurn() && playerServices.curPlayer().getColor() == Color.RED)){
            vm.put(ACTIVE_COLOR_ATTR, "WHITE");
        }
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
