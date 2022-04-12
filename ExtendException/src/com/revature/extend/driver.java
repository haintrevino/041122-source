package com.revature.extend;

import java.util.Scanner;

public class driver {

	public static void main(String[] args) {
		try {
			System.out.println("How many pokemon do you have?");
			Scanner userObj = new Scanner(System.in);
			int myInput = checkPokemon(userObj.nextInt());
			System.out.format("You have " + myInput + " pokemon.");
		} catch(myException x) {
			System.out.println(x.getMessage());
		}
	}
	public static int checkPokemon(int pokemon) throws myException {
		if (pokemon > 6) {
			throw new myException("You can't havem more than 6 pokemon!");
		}
		return pokemon;
	}

}
