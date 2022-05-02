package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.EmployeeModel;

public class EmployeeDAO implements DAOInterface<EmployeeModel, String, Integer> {

	@Override
	public void create(EmployeeModel element) {
		
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
			PreparedStatement statement = connection.prepareStatement("insert into employees (username, password) values (?, ?)");
			
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
	public EmployeeModel get(String employee) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from employees where username = ?");
			
			//Step 2
			statement.setString(1, employee);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				EmployeeModel e = new EmployeeModel();
				e.username = employee; // rs.getString("username"); would be equivalent code here
				e.password = rs.getString("password");
				
				return e;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(EmployeeModel element) {
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("update employees " 
					+ "set password = ? "
					+ "where username = ?");
			
			statement.setString(1, element.password);
			statement.setString(5, element.username);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(EmployeeModel element) {
		
		Connection connection = ConnectionManager.getConnection();
			
		try {
			PreparedStatement statement = connection.prepareStatement("delete from employees where username = ?");
		
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
			PreparedStatement statement = connection.prepareStatement("select * from employees where username = ?");
			
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
	public EmployeeModel getCust(Integer id2) {
		// TODO Auto-generated method stub
		return null;
	}

}
