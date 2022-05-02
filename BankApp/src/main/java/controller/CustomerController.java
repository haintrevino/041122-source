package controller;

import dao.CustomerDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.CustomerModel;

public class CustomerController {

	CustomerDAO dao;
	
	public CustomerController(Javalin app) {
		
		dao = new CustomerDAO();
		
		app.get("/customers/{customer}", getHandler);
		app.post("/customers", postHandler);
		app.put("/customers/{customer}", putHandler);
		app.delete("/customers/{customer}", deleteHandler);
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
		
		//Get the path's customer
		String customer = ctx.pathParam("customer");
				
		//Get the customer based on the username
		CustomerModel c = dao.get(customer);
		
		//Turn the given Java object into JSON format,
		//which is a text format understood by almost everybody and
		//can be used in any system running any technology
		//think of json as a universal data transfer format
		ctx.json(c);
	};
	
	//The 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON (ie text) into
		//a real Java object
		CustomerModel c = ctx.bodyAsClass(CustomerModel.class);
		
		dao.create(c);
		
		ctx.status(201);
	};
	
	//'update' command'
	public Handler putHandler = ctx -> {
		
		CustomerModel c = ctx.bodyAsClass(CustomerModel.class);
		
		dao.update(c);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		CustomerModel c = ctx.bodyAsClass(CustomerModel.class);
				
		dao.delete(c);
		
		ctx.status(200);
	};
	
}
