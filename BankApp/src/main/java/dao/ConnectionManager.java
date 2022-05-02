package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	
	private static String connectionURL = "jdbc:postgresql://salt.db.elephantsql.com:5432/tgrsxdpq",
		username = "tgrsxdpq",
		password = "Xa4eKUWswFGhQx20Cg0Xk2QjMIOrAik4";
	
	private static Connection connection;
	
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(connectionURL, username, password);
			}
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
