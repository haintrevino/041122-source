package com.revature.BankApp;

import dao.AccountDAO;
import dao.ApplicationDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import model.AccountModel;
import model.EmployeeModel;

public class Employee extends Console {

	static EmployeeDAO employeeDAO = new EmployeeDAO();
	static ApplicationDAO applicationDAO = new ApplicationDAO();
	static AccountDAO accountDAO = new AccountDAO();
	static CustomerDAO customerDAO = new CustomerDAO();
	
	public static void query() {
		while(loop) {
			System.out.println("Enter your username:");
			username = sc.nextLine();
			logger.info("Checking if the employee's username exists...");
			if(!employeeDAO.exist(username)) {
				System.out.println("Wrong username!");
				continue;
			}
			
			while(loop) {
				System.out.println("Enter your password:");
				password = sc.nextLine();
				logger.info("Checking if password matches the employee's username...");
				EmployeeModel employee = employeeDAO.get(username);
				if(employee.password.equals(password)) {
					break;
				} else {
					System.out.println("Wrong password!");
					continue;
				}
			}
			
			System.out.println("Welcome: " + username + "!");
			System.out.println("1. View customer information\n" + "2. Approve Applications\n" + "3. Deny Applications");
			input = sc.nextLine();
			
			if (input.equals("1")) {
				viewInfo();
			} else if (input.equals("2")) {
				approve();
			} else if (input.equals("3")) {
				deny();
			} else {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void viewInfo() {
		while(loop) {
			System.out.println("1. View all customer account info\n" + "2. View all customer personal info");
			input = sc.nextLine();
			
			if (input.equals("1")) {
				logger.info("Getting all accounts of all customers...");
				accountDAO.getAll();
			} else if (input.equals("2")) {
				logger.info("Getting all info of all customers...");
				customerDAO.getAll();
			} else {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void approve() {
		while(loop) {
			try {
				logger.info("Getting all open applications...");
				applicationDAO.getOpen();
				input = sc.nextLine();
				//check if valid
				logger.info("Checking if the application exists...");
				if(!applicationDAO.exist(Integer.parseInt(input))) {
					System.out.println("Invalid option!");
					continue;
				}
				//if valid then approve
				AccountModel newAccount = new AccountModel();
				logger.info("Assigning the new account owner...");
				newAccount.accountOwner = applicationDAO.get(Integer.parseInt(input)).applicationOwner;
				logger.info("Creating the new account...");
				accountDAO.create(newAccount);
				logger.info("Deleting the application...");
				applicationDAO.delete(applicationDAO.get(Integer.parseInt(input)));
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void deny() {
		while(loop) {
			try {
				logger.info("Getting all open applications...");
				applicationDAO.getOpen();
				input = sc.nextLine();
				//check if valid
				logger.info("Checking if the application exists...");
				if(!applicationDAO.exist(Integer.parseInt(input))) {
					System.out.println("Invalid option!");
					continue;
				}
				//if valid then deny
				logger.info("Deleting the application...");
				applicationDAO.delete(applicationDAO.get(Integer.parseInt(input)));
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
}
