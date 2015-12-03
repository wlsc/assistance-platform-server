package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public Result index() {
       // return ok(index.render("Your new application is ready."));
    	return ok("Your new application is ready.");
    }
    
    public Result ip() {
    	InetAddress thisIp = null;
		try {
			thisIp = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return ok(thisIp.getHostAddress());
    }
}