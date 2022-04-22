package com.revature.BankApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn = ConnectionManager.getConnection();
	
	public ArrayList<UserModel> getAllUsers() {
		try {
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from User");
			ArrayList<UserModel> users = new ArrayList<UserModel>();
			
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				
				users.add(new UserModel(username, password));
			} 
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addUser(UserModel newUser) {
		try {
			String query = "insert into User (username, password) values (?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, newUser.username);
			pstmt.setString(2, newUser.password);
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
