package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.webcheckers.Model.Color;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


@Tag("UI-tier")
public class GetHomeRouteTest {
    //Component under test
    private GetHomeRoute CuT;

    //mock objects
    private Request request;
    private Response response;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;
    private PlayerServices playerServices;
    private Session session;

    //HTML constant
    private String TITLE_ATTR = GetHomeRoute.TITLE_ATTR;
    private String TITLE_TAGS = "<title>Web Checkers | " + TITLE_ATTR + "</title>";
    private String PLAYER_LIST_ATTR = GetHomeRoute.PLAYER_LIST_ATTR;
    private String CUR_PLAYER_ATTR = GetHomeRoute.CUR_PLAYER_ATTR;
    private String NUM_PLAYERS_ATTR = GetHomeRoute.NUM_PLAYERS_ATTR;
    private String IS_SIGNED_IN = GetHomeRoute.IS_SIGNED_IN;

    //Friendly
    private Gson gson = new Gson();


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        playerServices = mock(PlayerServices.class);
        CuT = new GetHomeRoute(playerLobby, templateEngine, gson);
    }

    @Test
    public void render_page(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }

    @Test
    public void test_NO_SIGNIN_FIRST_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(null);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    public void test_signedout(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(false);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    @Test
    public void test_nullwongame(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(true);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    @Test
    public void testyouwon(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(true);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(playerServices.getWonGame()).thenReturn(true);
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    @Test
    public void testyoulost() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(true);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(playerServices.getWonGame()).thenReturn(true);
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }
    @Test
    public void testcolorclick() {
        Set<String> players = new HashSet<String>(); 
        players.add("Josh");
        players.add("Dan");
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        final Color color = Color.RED;
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        when(playerServices.signedIn()).thenReturn(true);
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(playerServices.getWonGame()).thenReturn(null);
        when(playerServices.curPlayerColor()).thenReturn(color);
        when(playerLobby.getNumPlayers()).thenReturn(2);
        when(playerServices.curPlayerName()).thenReturn("Josh");
        CuT.handle(request, response);
    }
}
