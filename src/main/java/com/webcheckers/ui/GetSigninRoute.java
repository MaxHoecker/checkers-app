package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetSigninRoute implements Route {

    //Constants
    static final String VIEW_NAME = "signin.ftl";
    static final String TITLE = "Sign-in Page";


    //Session Attributes

    //Class Attributes
    private TemplateEngine templateEngine;

    public GetSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        Map<String, Object> vm = new HashMap<>();

        vm.put("title", TITLE);
        vm.put("page", WebServer.SIGNIN_URL);



        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
