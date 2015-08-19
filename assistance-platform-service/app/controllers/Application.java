package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public Result index() {
       // return ok(index.render("Your new application is ready."));
    	return ok("Your new application is ready.");
    }
    
    public Result swagger() {
    	return ok(views.html.swagger.render());
    }
}