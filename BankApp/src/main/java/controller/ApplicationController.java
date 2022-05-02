package controller;

import dao.ApplicationDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.ApplicationModel;

public class ApplicationController {

	ApplicationDAO dao;
	
	public ApplicationController(Javalin app) {
		
		dao = new ApplicationDAO();
		
		app.get("/applications/{application}", getHandler);
		app.post("/applications", postHandler);
		app.put("/applications/{application}", putHandler);
		app.delete("/applications/{application}", deleteHandler);
	}
	
	/* The 'Algorithm' for controllers
	 * 0. Determine if data is flowing in (to your db) 
	 * 	  or out (back to the requester) 
	 * 1. Get data (either from the body (if data is flowing in)
	 * 	  or path (if data is flowing out)
	 * 2. Use data with your DAO
	 * 3. Return desired result
	 */
	
	public Handler getHandler = ctx -> {
		
		//Get the path's account
		Integer application = Integer.parseInt(ctx.pathParam("application"));
				
		//Get the account based on the username
		ApplicationModel ac = dao.get(application);
		
		//Turn the given Java object into JSON format,
		//which is a text format understood by almost everybody and
		//can be used in any system running any technology
		//think of json as a universal data transfer format
		ctx.json(ac);
	};
	
	//The 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON (ie text) into
		//a real Java object
		ApplicationModel app = ctx.bodyAsClass(ApplicationModel.class);
		
		dao.create(app);
		
		ctx.status(201);
	};
	
	//'update' command'
	public Handler putHandler = ctx -> {
		
		ApplicationModel app = ctx.bodyAsClass(ApplicationModel.class);
		
		dao.update(app);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		ApplicationModel app = ctx.bodyAsClass(ApplicationModel.class);
				
		dao.delete(app);
		
		ctx.status(200);
	};
	
}
