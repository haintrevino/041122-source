package com.revature.BankApp;

import java.util.Scanner;

public class Console extends App {
		
	static Scanner sc = new Scanner(System.in);
	
	static String input = null;
	static String username = null;
	static String password = null;
	static String transferFrom = null;
	static String transferTo = null;
	
	static boolean loop = true;
	
	public static void query() {
		while(loop) {
			System.out.println("Welcome to Revature Bank!");
	    	System.out.println("1. Customer\n" + "2. Employee\n" + "3. Admin");
	    	input = sc.nextLine();
	    	
	    	if (input.equals("1")) {
	    		Customer.query();
	    	} else if (input.equals("2")) {
	    		Employee.query();
	    	} else if (input.equals("3")) {
	    		Admin.query();
	    	} else {
	    		System.out.println("Invalid option!");
	    		continue;
	    	}
		}
	}
}
