package com.webcheckers.ui;

import static org.mockito.Mockito.mock;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UI-tier")
public class PostSignoutRouteTest {
    private PostSignoutRoute CuT;

    private Request request;
    private Response response;
    private TemplateEngine templateEngine;
    private PlayerLobby playerLobby;

    @BeforeEach
    public void setup() {
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        request = mock(Request.class);
        response = mock(Response.class);
        CuT = new PostSignoutRoute(playerLobby, templateEngine);
    }

    @Test
    public void test_handle(){

    }



}
