package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.webcheckers.Model.Player;
import com.webcheckers.appl.PlayerLobby;
import javafx.geometry.Pos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;
/*
@Tag("UI-Tier")
public class PostSigninRouteTest {

    private static final String TEST = "Test123";
    private static final String TEST_NO = "   ";
    private static final String INVALID_U = "!!!!222";
    private static final String SAME_U = "Same username";

    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private Player player;
    private Player playerWrong;

    private PostSigninRoute CuT;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);

        playerLobby = mock(PlayerLobby.class);
        player = new Player(TEST);
        playerWrong = new Player(TEST_NO);

        CuT = new PostSigninRoute(playerLobby,engine);

    }

    @Test
    public void bad_username(){
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(INVALID_U);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelExists();

        //testHelper.assertViewName(PostSigninRoute.INVALID_NAME_MSG)


        testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.NAME_TAKEN_MSG);
        testHelper.assertViewModelAttributeIsAbsent(PostSigninRoute.USERNAME_PARAM);



    }

    @Test
    public void same_username(){
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(SAME_U);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request,response);

        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelExists();

        //testHelper.assertViewName(PostSigninRoute.NAME_TAKEN_MSG);

    }

    @Test
    public void correct_username(){
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(TEST);


        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();


    }





}
*/