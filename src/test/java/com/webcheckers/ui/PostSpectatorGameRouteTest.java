package com.webcheckers.ui;


import com.webcheckers.Model.Board;
import com.webcheckers.Model.Color;
import com.webcheckers.Model.Game;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class PostSpectatorGameRouteTest {
    private PostSpectatorGameRoute CuT;

    //mocked dependencies
    private FreeMarkerEngine templateEngine;
    private Request request;
    private Response response;
    private PlayerServices playerServices;
    private Player player;
    private Session session;
    private Board board;
    private Game game;

    @BeforeEach
    public void setup(){
        templateEngine = mock(FreeMarkerEngine.class);
        request = mock(Request.class);
        response = mock(Response.class);
        playerServices = mock(PlayerServices.class);
        player = mock(Player.class);
        session = mock(Session.class);
        board = mock(Board.class);
        game = mock(Game.class);

        CuT = new PostSpectatorGameRoute(templateEngine);
        when(request.session()).thenReturn(session);
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(request.queryParams(any(String.class))).thenReturn("name");
        when(playerServices.curPlayer()).thenReturn(player);
        when(playerServices.redPlayer()).thenReturn(player);
        when(playerServices.whitePlayer()).thenReturn(player);
        when(playerServices.curTurnColor()).thenReturn(Color.RED);
        when(playerServices.gameBoard()).thenReturn(board);
        when(playerServices.getViewMode()).thenReturn("SPECTATOR");
    }

    @Test
    public void game_is_null(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(null);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("whitePlayer", player);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("board", board);
        testHelper.assertViewModelAttribute("activeColor", "RED");
        testHelper.assertViewModelAttribute("viewMode", "SPECTATOR");
    }

    @Test
    public void game_is_over(){
        when(player.game()).thenReturn(game);
        when(game.getPlayer(any(Color.class))).thenReturn(null);

        String html = CuT.handle(request, response);
        assertNull(html);
    }

    @Test
    public void game_in_progress(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(player.game()).thenReturn(game);
        when(game.getPlayer(any(Color.class))).thenReturn(player);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("currentUser", player);
        testHelper.assertViewModelAttribute("whitePlayer", player);
        testHelper.assertViewModelAttribute("redPlayer", player);
        testHelper.assertViewModelAttribute("board", board);
        testHelper.assertViewModelAttribute("activeColor", "RED");
        testHelper.assertViewModelAttribute("viewMode", "SPECTATOR");
    }
}
