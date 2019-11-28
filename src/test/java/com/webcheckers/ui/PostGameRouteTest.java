package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.Model.Board;
import com.webcheckers.Model.Color;
import com.webcheckers.Model.Game;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerServices;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-Tier")
public class PostGameRouteTest {

    private PostGameRoute CuT;

    //constants
    static final String CUR_USER_ATTR = "currentUser";
    static final String RED_PLAYER_ATTR = "redPlayer";
    static final String WHITE_PLAYER_ATTR = "whitePlayer";
    static final String ACTIVE_COLOR_ATTR = "activeColor";
    static final String BOARD_ATTR = "board";
    static final String VIEW_MODE = "viewMode";

    //mocked dependencies
    private Request request;
    private Response response;
    private Session session;
    private PlayerServices playerServices;
    private Player player;
    private Board board;
    private Game game;
    private Message message;
    private FreeMarkerEngine templateEngine;

    //trusted dependencies
    private Gson gson = new Gson();

    @BeforeEach
    public void setup(){
        templateEngine = mock(FreeMarkerEngine.class);
        CuT = new PostGameRoute(gson, templateEngine);

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        playerServices = mock(PlayerServices.class);
        player = mock(Player.class);
        board = mock(Board.class);
        game = mock(Game.class);
        message = mock(Message.class);

        when(request.session()).thenReturn(session);
        when(session.attribute("playerServices")).thenReturn(playerServices);
        when(playerServices.curPlayer()).thenReturn(player);
        when(playerServices.redPlayer()).thenReturn(player);
        when(playerServices.whitePlayer()).thenReturn(player);
        when(playerServices.gameBoard()).thenReturn(board);
        when(playerServices.getGameEndMessage()).thenReturn(message);
        when(message.getText()).thenReturn("sample message");
        when(playerServices.getViewMode()).thenReturn("PLAY");
    }

    // Get route branches

    @Test
    public void regular_rendering_red_turn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(true);
        when(player.getColor()).thenReturn(Color.RED);
        when(playerServices.opponent()).thenReturn(player);
        when(game.numWhitePieces()).thenReturn(1);
        when(game.numRedPieces()).thenReturn(1);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game Page");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "RED");
    }

    @Test
    public void null_opponent_is_reds_turn1(){
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(true);
        when(player.getColor()).thenReturn(Color.RED);
        when(playerServices.opponent()).thenReturn(null);

        String html = CuT.handle(request, response);

        assertNull(html);
    }

    @Test
    public void null_opponent_is_reds_turn2(){
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(false);
        when(player.getColor()).thenReturn(Color.WHITE);
        when(playerServices.opponent()).thenReturn(null);

        String html = CuT.handle(request, response);

        assertNull(html);
    }

    @Test
    public void null_opponent_is_whites_turn1(){
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(true);
        when(player.getColor()).thenReturn(Color.WHITE);
        when(playerServices.opponent()).thenReturn(null);

        String html = CuT.handle(request, response);

        assertNull(html);
    }

    @Test
    public void null_opponent_is_whites_turn2(){
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(false);
        when(player.getColor()).thenReturn(Color.RED);
        when(playerServices.opponent()).thenReturn(null);

        String html = CuT.handle(request, response);

        assertNull(html);
    }

    @Test
    public void no_more_red_pieces_player_is_white(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(false);
        when(player.getColor()).thenReturn(Color.WHITE);
        when(playerServices.opponent()).thenReturn(player);
        when(game.numRedPieces()).thenReturn(0);
        when(player.getName()).thenReturn("a name");
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game Page");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "RED");
    }

    @Test
    public void no_more_red_pieces_player_is_red(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(true);
        when(player.getColor()).thenReturn(Color.RED);
        when(playerServices.opponent()).thenReturn(player);
        when(game.numRedPieces()).thenReturn(0);
        when(player.getName()).thenReturn("a name");
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game Page");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "RED");
    }

    @Test
    public void no_more_white_pieces_player_is_white(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(true);
        when(player.getColor()).thenReturn(Color.WHITE);
        when(playerServices.opponent()).thenReturn(player);
        when(game.numRedPieces()).thenReturn(1);
        when(game.numWhitePieces()).thenReturn(0);
        when(player.getName()).thenReturn("a name");
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game Page");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "WHITE");
    }

    @Test
    public void no_more_white_pieces_player_is_red(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(player.isMyTurn()).thenReturn(false);
        when(player.getColor()).thenReturn(Color.RED);
        when(playerServices.opponent()).thenReturn(player);
        when(game.numRedPieces()).thenReturn(1);
        when(game.numWhitePieces()).thenReturn(0);
        when(player.getName()).thenReturn("a name");
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Game Page");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "WHITE");
    }

    //case where player clicked

    @Test
    public void successful_setup(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(null);
        when(playerServices.curPlayerColor()).thenReturn(null);
        when(request.queryParams(any(String.class))).thenReturn("a name");
        when(playerServices.setUpGame(any(String.class))).thenReturn(true);
        when(playerServices.opponent()).thenReturn(player);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "RED");
    }

    @Test
    public void unsuccessful_setup(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(null);
        when(playerServices.curPlayerColor()).thenReturn(null);
        when(request.queryParams(any(String.class))).thenReturn("a name");
        when(playerServices.setUpGame(any(String.class))).thenReturn(false);
        when(playerServices.opponent()).thenReturn(player);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        String html = CuT.handle(request, response);

        assertNull(html);
    }

    // case where player is clicked on

    @Test
    public void clicked_on(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(null);
        when(playerServices.curPlayerColor()).thenReturn(Color.WHITE);
        when(playerServices.opponent()).thenReturn(player);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(CUR_USER_ATTR, player);
        testHelper.assertViewModelAttribute(WHITE_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(RED_PLAYER_ATTR, player);
        testHelper.assertViewModelAttribute(BOARD_ATTR, board);
        testHelper.assertViewModelAttribute(VIEW_MODE, "PLAY");
        testHelper.assertViewModelAttribute(ACTIVE_COLOR_ATTR, "RED");
    }

}
