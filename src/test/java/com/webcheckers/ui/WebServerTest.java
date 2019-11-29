package com.webcheckers.ui;


import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.template.freemarker.FreeMarkerEngine;

import static org.mockito.Mockito.mock;

@Tag("UI-tier")
public class WebServerTest {
    private WebServer CuT;

    //mocked dependencies
    private FreeMarkerEngine templateEngine;

    //trusted dependencies
    private Gson gson;

    @BeforeEach
    public void setup(){
        templateEngine = mock(FreeMarkerEngine.class);
        gson = new Gson();

        CuT = new WebServer(templateEngine, gson);
    }

    @Test
    public void initialize_test(){
        CuT.initialize();
    }
}
