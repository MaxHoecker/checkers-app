package com.webcheckers.ui;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

public class PostSigninRoute implements Route {

    //Attributes
    private TemplateEngine templateEngine;

    public PostSigninRoute(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response){
        return null;
    }
}
