package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;

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
    private Session session;

    //HTML constant
    private String TITLE_ATTR = GetHomeRoute.TITLE_ATTR;
    private String TITLE_TAGS = "<title>Web Checkers | " + TITLE_ATTR + "</title>";
    private String PLAYER_LIST_ATTR = GetHomeRoute.PLAYER_LIST_ATTR;
    private String CUR_PLAYER_ATTR = GetHomeRoute.CUR_PLAYER_ATTR;
    private String NUM_PLAYERS_ATTR = GetHomeRoute.NUM_PLAYERS_ATTR;
    private String IS_SIGNED_IN = GetHomeRoute.IS_SIGNED_IN;


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    @Test
    public void render_page(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
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
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute(SIGNEDIN, false);
       
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("numPlayers", 0);
        testHelper.assertViewModelAttribute("isSignedIn", false);
    }

    @Test
    public void test_SIGNIN_FIRST_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute(SIGNEDIN, true);
        
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("numPlayers", 0);
        //testHelper.assertViewModelAttribute("isSignedIn", true);
    }

    @Test
    public void test_NOT_SIGNED_IN_SECOND_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute(SIGNEDIN, false);
        playerLobby.addPlayer("Josh", new Player("Josh"));
        request.session().attribute(NUM_PLAYERS_ATTR, playerLobby.getNumPlayers());
        
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute("title", "Welcome!");
        //testHelper.assertViewModelAttribute("numPlayers", 1);
        testHelper.assertViewModelAttribute("isSignedIn", false);
    }

    @Test
    public void test_SIGNED_IN_SECOND_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        request.session().attribute(SIGNEDIN, true);
        playerLobby.addPlayer("Josh", new Player("Josh"));
        request.session().attribute(NUM_PLAYERS_ATTR, playerLobby.getNumPlayers());
        
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute("title", "Welcome!");
        //testHelper.assertViewModelAttribute("numPlayers", 1);
        //testHelper.assertViewModelAttribute("isSignedIn", true);
        //testHelper.assertViewModelAttribute("playerList", "Josh");
    }
}