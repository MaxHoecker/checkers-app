package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * UI Controller for rendering the signin page
 *
 * @author <a href='jak3703@rit.edu'>Jacob Kobrak</a>
 */
public class GetSigninRoute implements Route {

    //Constants
    static final String VIEW_NAME = "signin.ftl";
    static final String TITLE = "Sign-in Page";


    //Session Attributes

    //Class Attributes
    private TemplateEngine templateEngine;

    /**
     * Construct GetSigninRoute
     * @param templateEngine WebServer's TemplateEngine instance
     */
    public GetSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    /**
     * Render the signin page
     * @param request HTTP request
     * @param response HTTP response
     * @return String used by outside code to render the page
     */
    @Override
    public String handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", TITLE);
        vm.put("page", WebServer.SIGNIN_URL);



        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
