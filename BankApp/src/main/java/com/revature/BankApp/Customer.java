package com.revature.BankApp;

import dao.AccountDAO;
import dao.ApplicationDAO;
import dao.CustomerDAO;
import model.AccountModel;
import model.ApplicationModel;
import model.CustomerModel;

public class Customer extends Console {

	static CustomerDAO customerDAO = new CustomerDAO();
	static ApplicationDAO applicationDAO = new ApplicationDAO();
	static AccountDAO accountDAO = new AccountDAO();
	
	public static void query() {
		while(loop) {
			System.out.println("1. Login\n" + "2. Signup");
	    	input = sc.nextLine();
	    	
	    	if (input.equals("1")) {
	    		logIn();
	    	} else if (input.equals("2")) {
	    		signUp();
	    	} else {
	    		System.out.println("Invalid option!");
	    		continue;
	    	}
		}
	}
	
	public static void logIn() {
		while(loop) {
			System.out.println("Enter your username:");
			username = sc.nextLine();
			logger.info("Checking if the customer's username exists...");
			if(!customerDAO.exist(username)) {
				System.out.println("Wrong username!");
				continue;
			}
			
			while(loop) {
				System.out.println("Enter your password:");
				password = sc.nextLine();
				logger.info("Checking if password matches the customer's username...");
				CustomerModel customer = customerDAO.get(username);
				if(customer.password.equals(password)) {
					break;
				} else {
					System.out.println("Wrong password!");
					continue;
				}
			}
			
			while(loop) {
				System.out.println("Welcome: " + username + "!");
				System.out.println("1. Withdraw\n" + "2. Deposit\n" + "3. Transfer\n" + "4. Apply for account\n" + "5. Apply for joint account");
				input = sc.nextLine();
				
				if (input.equals("1")) {
					withdraw();
				} else if (input.equals("2")) {
					deposit();
				} else if (input.equals("3")) {
					//check if username has more than 1 account
					logger.info("Checking if the customer has more than 1 account...");
					if(accountDAO.getNum(username) < 2) {
						System.out.println("Need more than 1 account to transfer!");
						continue;
					} else {
						transfer();
					}
				} else if (input.equals("4")) {
					apply();
				} else if (input.equals("5")) {
					applyJoint();
				} else {
					System.out.println("Invalid option!");
					continue;
				}
			}
		}
	}
	
	public static void signUp() {
		while(loop) {
			CustomerModel newCustomer = new CustomerModel();
			System.out.println("Enter a username:");
			username = sc.nextLine();
			logger.info("Checking if customer's username already exists...");
			if(customerDAO.exist(username)) {
				System.out.println("Username taken!");
				continue;
			}
			newCustomer.username = username;
			
			System.out.println("Enter a password:");
			password = sc.nextLine();
			newCustomer.password = password;
			logger.info("Creating a new customer...");
			customerDAO.create(newCustomer);
			
			while(loop) {
				System.out.println("Welcome " + username + "!");
				System.out.println("1. Apply for account\n" + "2. Apply for joint account");
				input = sc.nextLine();
				
				if (input.equals("1")) {
					apply();
				} else if (input.equals("2")) {
					applyJoint();
				} else {
					System.out.println("Invalid option!");
					continue;
				}
			}
		}
	}
	
	public static void withdraw() {
		while(loop) {
			try {
				System.out.println("Which account would you like to withdraw from?");
				logger.info("Getting all of the customer's accounts...");
				accountDAO.getMy(username);
				AccountModel newAccount = new AccountModel();
				input = sc.nextLine();
				//check if input account exists with same account owner as username
				logger.info("Checking if the account exists and if it belongs to the customer...");
				if(!accountDAO.existPlus(Integer.parseInt(input), username)) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Assigning the account to an account model...");
				newAccount = accountDAO.get(Integer.parseInt(input));
				System.out.println("How much would you like to withdraw?");
				input = sc.nextLine();
				//check if valid input (greater than 0, less than or equal to account balance, is a number)
				if(Integer.parseInt(input) <= 0 | Integer.parseInt(input) > newAccount.balance) {
					System.out.println("Invalid option!");
					continue;
				}
				newAccount.balance = newAccount.balance - Integer.parseInt(input);
				logger.info("Updating the account with the new balance...");
				accountDAO.update(newAccount);
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
		
	}
	
	public static void deposit() {
		while(loop) {
			try {
				System.out.println("Which account would you like to deposit to?");
				logger.info("Getting all of the customer's accounts...");
				accountDAO.getMy(username);
				AccountModel newAccount = new AccountModel();
				input = sc.nextLine();
				//check if input account exists with same account owner as username
				logger.info("Checking if the account exists and if it belongs to the customer...");
				if(!accountDAO.existPlus(Integer.parseInt(input), username)) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Assigning the account to an account model...");
				newAccount = accountDAO.get(Integer.parseInt(input));
				System.out.println("How much would you like to deposit?");
				input = sc.nextLine();
				//check if valid input (greater than 0, is a number)
				if(Integer.parseInt(input) <= 0) {
					System.out.println("Invalid option!");
					continue;
				}
				newAccount.balance = newAccount.balance + Integer.parseInt(input);
				logger.info("Updating the account with the new balance...");
				accountDAO.update(newAccount);
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void transfer() {
		while(loop) {
			try {
				System.out.println("Which account would you like to transfer from?");
				logger.info("Getting all of the customer's accounts...");
				accountDAO.getMy(username);
				AccountModel newAccount = new AccountModel();
				transferFrom = sc.nextLine();
				String input1 = transferFrom;
				//check if input account exists with same account owner as username
				logger.info("Checking if the account exists and if it belongs to the customer...");
				if(!accountDAO.existPlus(Integer.parseInt(transferFrom), username)) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Assigning the account to an account model...");
				newAccount = accountDAO.get(Integer.parseInt(transferFrom));
				System.out.println("Which account would you like to transfer to?");
				logger.info("Getting all of the customer's accounts minus the account they're transferring from...");
				accountDAO.getMyMinus(username, Integer.parseInt(transferFrom));
				AccountModel newAccount2 = new AccountModel();
				transferTo = sc.nextLine();
				String input2 = transferTo;
				//check if input account exists with same account owner as username and isn't the one they're transferring from)
				logger.info("Checking if the account exists, if it belongs to the customer and if it isn't the same account they're transferring from...");
				if(!accountDAO.existPlus(Integer.parseInt(transferTo), username) | input1.equals(input2)) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Assigning the account to another account model...");
				newAccount2 = accountDAO.get(Integer.parseInt(transferTo));
				System.out.println("How much would you like to transfer?");
				input = sc.nextLine();
				//check if valid input (greater than 0, less than or equal to balance of account they're transferring from)
				if(Integer.parseInt(input) <= 0 | Integer.parseInt(input) > newAccount.balance) {
					System.out.println("Invalid option!");
					continue;
				}
				newAccount.balance = newAccount.balance - Integer.parseInt(input);
				newAccount2.balance = newAccount2.balance + Integer.parseInt(input);
				logger.info("Updating both accounts with their new balances...");
				accountDAO.update(newAccount);
				accountDAO.update(newAccount2);
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void apply() {
		ApplicationModel newApplication = new ApplicationModel();
		newApplication.applicationOwner = username;
		logger.info("Creating a new account application...");
		applicationDAO.create(newApplication);
		System.out.println("Application submitted.");
	}
	
	public static void applyJoint() {
		while(loop) {
			try {
				System.out.println("Which customer would you like to open a joint account with?");
				//get all customers minus the current one
				logger.info("Getting all of the customers minus the current customer...");
				customerDAO.getAllMinus(username);
				input = sc.nextLine();
				//check if customer_number exists and also isn't the current customer_number
				logger.info("Checking if the customer number exists and that it isn't the current customer's number...");
				if(!customerDAO.existNum(Integer.parseInt(input)) | input.equals(customerDAO.get(username).customerNumber)) {
					System.out.println("Invalid option!");
					continue;
				}
				ApplicationModel newJointApplication = new ApplicationModel();
				logger.info("Adding the two customers to the same joint account model...");
				newJointApplication.applicationOwner = username + ", " + customerDAO.getCust(Integer.parseInt(input)).username;
				logger.info("Creating a new joint account application...");
				applicationDAO.create(newJointApplication);
				System.out.println("Application submitted.");
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
}
