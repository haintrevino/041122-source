package controller;

import dao.EmployeeDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.EmployeeModel;

public class EmployeeController {

	EmployeeDAO dao;
	
	public EmployeeController(Javalin app) {
		
		dao = new EmployeeDAO();
		
		app.get("/employees/{employee}", getHandler);
		app.post("/employees", postHandler);
		app.put("/employees/{employee}", putHandler);
		app.delete("/employees/{employee}", deleteHandler);
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
		
		//Get the path's employee
		String employee = ctx.pathParam("employee");
				
		//Get the employee based on the username
		EmployeeModel e = dao.get(employee);
		
		//Turn the given Java object into JSON format,
		//which is a text format understood by almost everybody and
		//can be used in any system running any technology
		//think of json as a universal data transfer format
		ctx.json(e);
	};
	
	//The 'create' command
	public Handler postHandler = ctx -> {
		
		//Turn the body of the request from JSON (ie text) into
		//a real Java object
		EmployeeModel e = ctx.bodyAsClass(EmployeeModel.class);
		
		dao.create(e);
		
		ctx.status(201);
	};
	
	//'update' command'
	public Handler putHandler = ctx -> {
		
		EmployeeModel e = ctx.bodyAsClass(EmployeeModel.class);
		
		dao.update(e);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		EmployeeModel e = ctx.bodyAsClass(EmployeeModel.class);
				
		dao.delete(e);
		
		ctx.status(200);
	};
	
}
