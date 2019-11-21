package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.appl.PlayerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

@Tag("UI-Tier")
public class PostSigninRouteTest {

    private static final String TEST = "Test123";
    private static final String TEST_NO = "   ";
    private static final String INVALID_U = "\"Josh\"";
    private static final String SAME_U = "Same username";
    private static final String VALID = "Jake";

    static final String NAME_TAKEN_MSG = "Username taken. Please enter another name.";
    static final String INVALID_NAME_MSG = "Invalid username. Please enter another name.";

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;
    private Player playerWrong;
    private PlayerServices playerServices;

    private PostSigninRoute CuT;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        playerServices = mock(PlayerServices.class);
        engine = mock(FreeMarkerEngine.class);
        when(request.session()).thenReturn(session);
        when(session.attribute(any(String.class))).thenReturn(playerServices);
        response = mock(Response.class);

        playerLobby = mock(PlayerLobby.class);
        player = new Player(TEST);
        playerWrong = new Player(TEST_NO);

        CuT = new PostSigninRoute(playerLobby,engine);
    }

    @Test
    public void first_successful(){
        when(request.queryParams(any(String.class))).thenReturn(VALID);
        when(playerServices.setCurPlayer(VALID)).thenReturn(null);

        String result = CuT.handle(request, response);

        assertNull(result);
    }

    @Test
    public void invalid_username(){
        when(request.queryParams(any(String.class))).thenReturn(INVALID_U);
        when(playerServices.setCurPlayer(INVALID_U)).thenReturn(INVALID_NAME_MSG);
        when(engine.render(any(ModelAndView.class))).thenReturn("any(String.class)");

        String result = CuT.handle(request, response);

        assertNotNull(result);
    }

    @Test
    public void username_taken(){
        when(request.queryParams(any(String.class))).thenReturn(SAME_U);
        when(playerServices.setCurPlayer(SAME_U)).thenReturn(NAME_TAKEN_MSG);
        when(engine.render(any(ModelAndView.class))).thenReturn("any(String.class)");

        String result = CuT.handle(request, response);

        assertNotNull(result);

    }





}
