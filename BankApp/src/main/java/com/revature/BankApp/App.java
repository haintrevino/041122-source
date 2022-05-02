package com.revature.BankApp;

import controller.AccountController;
import controller.AdminController;
import controller.ApplicationController;
import controller.CustomerController;
import controller.EmployeeController;
import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.BankApp.App;

public class App {
  
	public static final Logger logger = LogManager.getLogger(App.class);
	
	public static void main(String[] args)	{
		
        Javalin app = Javalin.create().start(7070);
        
        CustomerController customerController = new CustomerController(app);
        
        EmployeeController employeeController = new EmployeeController(app);
        
        AdminController adminController = new AdminController(app);
        
        AccountController accountController = new AccountController(app);
        
        ApplicationController applicationController = new ApplicationController(app);
    	
    	Console.query();
    }
}
