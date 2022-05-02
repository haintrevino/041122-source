package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AdminModel;

public class AdminDAO implements DAOInterface<AdminModel, String, Integer> {

	@Override
	public void create(AdminModel element) {
		
		/* The 'Algorithm'
		 * 
		 * 1. Create Prepared Statement
		 * 2. Set the ?'s for the PreparedStatement using inputs
		 * 3. Execute query and, if relevant, get ResultSet
		 * 4. If I have one, process ResultSet
		 */
		
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("insert into admins (username, password) values (?, ?)");
			
			//Step 2
			statement.setString(1, element.username);
			statement.setString(2, element.password);
			
			//Step 3
			statement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public AdminModel get(String admin) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from admins where username = ?");
			
			//Step 2
			statement.setString(1, admin);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				AdminModel a = new AdminModel();
				a.username = admin; // rs.getString("username"); would be equivalent code here
				a.password = rs.getString("password");
				
				return a;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(AdminModel element) {
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("update admins " 
					+ "set password = ? "
					+ "where username = ?");
			
			statement.setString(1, element.password);
			statement.setString(2, element.username);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(AdminModel element) {
		
		Connection connection = ConnectionManager.getConnection();
			
		try {
			PreparedStatement statement = connection.prepareStatement("delete from admins where username = ?");
		
			statement.setString(1, element.username);
			
			statement.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exist(String username) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from admins where username = ?");
			
			//Step 2
			statement.setString(1, username);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				//username exists
				return true;
			} else {
				//username doesn't exist
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
		//return null;
	}

	@Override
	public void getOpen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMy(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMyMinus(Integer id2, String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNum(Integer id2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdminModel getCust(Integer id2) {
		// TODO Auto-generated method stub
		return null;
	}

}
