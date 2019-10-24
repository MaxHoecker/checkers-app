package com.webcheckers.ui;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

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
    private String SIGNEDIN = GetHomeRoute.SIGNEDIN;


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        session = mock(Session.class);

        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    @Test
    public void render_page(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", GetHomeRoute.TITLE_ATTR);
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);

    }

    @Test
    public void test_NO_SIGNIN_FIRST_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetHomeRoute.VIEW_NAME);

        request.session().attribute(SIGNEDIN, null);
        CuT.handle(request, response);

        final TemplateEngine engine = new FreeMarkerEngine();
        String viewHtml = engine.render(modelAndView);

        testHelper.assertViewModelAttribute(TITLE_ATTR, GetHomeRoute.TITLE_ATTR);
        testHelper.assertViewModelAttribute(PLAYER_LIST_ATTR, Message.info("no players yet!"));
        testHelper.assertViewModelAttribute(NUM_PLAYERS_ATTR, 0);
        testHelper.assertViewModelAttribute(IS_SIGNED_IN, false);
    }

      @Test
    public void test_SIGNIN_FIRST_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
      
        request.session().attribute(SIGNEDIN, true);
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(TITLE_ATTR, GetHomeRoute.TITLE_ATTR);
        testHelper.assertViewModelAttribute(NUM_PLAYERS_ATTR, 1);
        testHelper.assertViewModelAttribute(IS_SIGNED_IN, true);
    }

      @Test
    public void test_NOT_SIGNED_IN_SECOND_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        request.session().attribute(SIGNEDIN, false);
        playerLobby.addPlayer("Josh", new Player("Josh"));
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(TITLE_ATTR, GetHomeRoute.TITLE_ATTR);
        testHelper.assertViewModelAttribute(NUM_PLAYERS_ATTR, 1);
        testHelper.assertViewModelAttribute(IS_SIGNED_IN, false);
    }

     @Test
    public void test_SIGNED_IN_SECOND_USER(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();

        request.session().attribute(SIGNEDIN, true);
        playerLobby.addPlayer("Josh", new Player("Josh"));
        CuT.handle(request, response);

        testHelper.assertViewModelAttribute(TITLE_ATTR, GetHomeRoute.TITLE_ATTR);
        testHelper.assertViewModelAttribute(NUM_PLAYERS_ATTR, 1);
        testHelper.assertViewModelAttribute(IS_SIGNED_IN, true);
        testHelper.assertViewModelAttribute(PLAYER_LIST_ATTR, "Josh");
    }
}
