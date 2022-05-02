package controller;

import dao.AccountDAO;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import model.AccountModel;

public class AccountController {

	AccountDAO dao;
	
	public AccountController(Javalin app) {
		
		dao = new AccountDAO();
		
		app.get("/accounts/{account}", getHandler);
		app.post("/accounts", postHandler);
		app.put("/accounts/{account}", putHandler);
		app.delete("/accounts/{account}", deleteHandler);
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
		Integer account = Integer.parseInt(ctx.pathParam("account"));
				
		//Get the account based on the username
		AccountModel ac = dao.get(account);
		
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
		AccountModel ac = ctx.bodyAsClass(AccountModel.class);
		
		dao.create(ac);
		
		ctx.status(201);
	};
	
	//'update' command'
	public Handler putHandler = ctx -> {
		
		AccountModel ac = ctx.bodyAsClass(AccountModel.class);
		
		dao.update(ac);
		
		ctx.status(200);
	};
	
	public Handler deleteHandler = ctx -> {
		AccountModel ac = ctx.bodyAsClass(AccountModel.class);
				
		dao.delete(ac);
		
		ctx.status(200);
	};
	
}