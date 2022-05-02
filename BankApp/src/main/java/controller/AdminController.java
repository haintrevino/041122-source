package controller;

import dao.AdminDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.AdminModel;

public class AdminController {

	AdminDAO dao;
	
	public AdminController(Javalin app) {
		
		dao = new AdminDAO();
		
		app.get("/admins/{admin}", getHandler);
		app.post("/admins", postHandler);
		app.put("/admins/{admin}", putHandler);
		app.delete("/admins/{admin}", deleteHandler);
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
		
		//Get the path's admin
		String admin = ctx.pathParam("admin");
				
		//Get the admin based on the username
		AdminModel a = dao.get(admin);
		
		//Turn the given Java object into JSON format,
		//which is a text format understood by almost everybody and
		//can be used in any system running any technology
		//think of json as a universal data transfer format
		ctx.json(a);
	};
	
	//The 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON (ie text) into
		//a real Java object
		AdminModel a = ctx.bodyAsClass(AdminModel.class);
		
		dao.create(a);
		
		ctx.status(201);
	};
	
	//'update' command'
	public Handler putHandler = ctx -> {
		
		AdminModel a = ctx.bodyAsClass(AdminModel.class);
		
		dao.update(a);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		AdminModel a = ctx.bodyAsClass(AdminModel.class);
				
		dao.delete(a);
		
		ctx.status(200);
	};
	
}
