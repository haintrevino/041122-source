package com.revature.BankApplication;

public class UserModel {
	
	public String username;
	public String password;
			
	public UserModel(String username, String password) {
				
		this.username = username;
		this.password = password;			
	}

	@Override
	public String toString() {
		return "UserModel [username=" + username + ", password=" + password + "]";
	}
			
}
