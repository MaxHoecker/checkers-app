package com.webcheckers.ui;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import com.webcheckers.appl.PlayerServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

@Tag("UI-tier")
public class GetSigninRouteTest {
    //Component under test
    private GetSigninRoute CuT;

    //mock objects
    private Request request;
    private Response response;
    private TemplateEngine templateEngine;
    private Session session;

    //HTML constant
    private String TITLE = GetSigninRoute.TITLE;
    private String TITLE_TAGS = "<title>Web Checkers | " + TITLE + "</title>";
    private String PAGE = WebServer.SIGNIN_URL;
    private String PAGE_TAGS = "<form action=" + PAGE + " method=\"post\">";


    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        session = mock(Session.class);

        CuT = new GetSigninRoute(templateEngine);
    }

    @Test
    public void render_page(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", GetSigninRoute.TITLE);
        testHelper.assertViewModelAttribute("page", WebServer.SIGNIN_URL);

        testHelper.assertViewName(GetSigninRoute.VIEW_NAME);

    }

    @Test
    public void test_html(){
        final Map<String, Object> vm = new HashMap<>();
        final ModelAndView modelAndView = new ModelAndView(vm, GetSigninRoute.VIEW_NAME);

        vm.put("title", GetSigninRoute.TITLE);
        vm.put("page", WebServer.SIGNIN_URL);

        final TemplateEngine engine = new FreeMarkerEngine();
        String viewHtml = engine.render(modelAndView);

        assertTrue(viewHtml.contains(TITLE));
        assertTrue(viewHtml.contains(TITLE_TAGS));
        assertTrue(viewHtml.contains(PAGE));
        assertTrue(viewHtml.contains(PAGE_TAGS));
    }
}
