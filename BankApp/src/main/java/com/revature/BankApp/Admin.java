package com.revature.BankApp;

import dao.AccountDAO;
import dao.AdminDAO;
import dao.ApplicationDAO;
import model.AccountModel;
import model.AdminModel;

public class Admin extends Console {
	
	static AdminDAO adminDAO = new AdminDAO();
	static AccountDAO accountDAO = new AccountDAO();
	static ApplicationDAO applicationDAO = new ApplicationDAO();
	
	public static void query() {
		while(loop) {
			System.out.println("Enter your username:");
			username = sc.nextLine();
			logger.info("Checking if the admin's username exists...");
			if(!adminDAO.exist(username)) {
				System.out.println("Wrong username!");
				continue;
			}
			
			while(loop) {
				System.out.println("Enter your password:");
				password = sc.nextLine();
				logger.info("Checking if password matches the admin's username...");
				AdminModel admin = adminDAO.get(username);
				if(admin.password.equals(password)) {
					break;
				} else {
					System.out.println("Wrong password!");
					continue;
				}
			}
			
			System.out.println("Welcome: " + username + "!");
			System.out.println("1. Withdraw\n" + "2. Deposit\n" + "3. Transfer\n" + "4. Approve applications\n" + "5. Deny applications\n" + "6. Cancel accounts");
			input = sc.nextLine();
			
			if (input.equals("1")) {
				withdraw();
			} else if (input.equals("2")) {
				deposit();
			} else if (input.equals("3")) {
				//check if more than 1 account
				logger.info("Checking if the customer has more than 1 account...");
				if(accountDAO.getNumAll() < 2) {
					System.out.println("More than 1 account needed to transfer!");
					continue;
				} else {
					transfer();
				}
			} else if (input.equals("4")) {
				approve();
			} else if (input.equals("5")) {
				deny();
			} else if (input.equals("6")) {
				cancelAccounts();
			} else {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
	
	public static void withdraw() {
		while(loop) {
			try {
				System.out.println("Which account would you like to withdraw from?");
				logger.info("Getting all accounts...");
				accountDAO.getAll();
				AccountModel newAccount = new AccountModel();
				input = sc.nextLine();
				//check if input account exists
				logger.info("Checking if the account exists...");
				if(!accountDAO.exist(Integer.parseInt(input))) {
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
				logger.info("Getting all accounts...");
				accountDAO.getAll();
				AccountModel newAccount = new AccountModel();
				input = sc.nextLine();
				//check if input account exists
				logger.info("Checking if the account exists...");
				if(!accountDAO.exist(Integer.parseInt(input))) {
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
				logger.info("Getting all accounts...");
				accountDAO.getAll();
				AccountModel newAccount = new AccountModel();
				transferFrom = sc.nextLine();
				String input1 = transferFrom;
				//check if input account exists
				logger.info("Checking if the account exists...");
				if(!accountDAO.exist(Integer.parseInt(transferFrom))) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Assigning the account to an account model...");
				newAccount = accountDAO.get(Integer.parseInt(transferFrom));
				System.out.println("Which account would you like to transfer to?");
				logger.info("Getting all accounts minus the account they're transferring from...");
				accountDAO.getAllMinus(Integer.parseInt(transferFrom));
				AccountModel newAccount2 = new AccountModel();
				transferTo = sc.nextLine();
				String input2 = transferTo;
				//check if input account exists and isn't the one they're transferring from)
				logger.info("Checking if the account exists and if it isn't the same account they're transferring from...");
				if(!accountDAO.exist(Integer.parseInt(transferTo)) | input1.equals(input2)) {
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
	
	public static void cancelAccounts() {
		while(loop) {
			try {
				System.out.println("Which account would you like to cancel?");
				logger.info("Getting all accounts...");
				accountDAO.getAll();
				input = sc.nextLine();
				//check if input account exists
				logger.info("Checking if the account exists...");
				if(!accountDAO.exist(Integer.parseInt(input))) {
					System.out.println("Invalid option!");
					continue;
				}
				logger.info("Deleting the account...");
				accountDAO.delete(accountDAO.get(Integer.parseInt(input)));
			} catch(NumberFormatException e) {
				System.out.println("Invalid option!");
				continue;
			}
		}
	}
}
