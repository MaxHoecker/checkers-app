package com.webcheckers.ui;

import spark.*;

public class GetSigninRoute implements Route {

    //Constants
    static final String VIEW_NAME = "/signin.ftl";

    //Attributes
    private TemplateEngine templateEngine;

    public GetSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        return templateEngine.render(new ModelAndView(null, VIEW_NAME));
    }
}
