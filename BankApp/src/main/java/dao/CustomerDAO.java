package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CustomerModel;

public class CustomerDAO implements DAOInterface<CustomerModel, String, Integer> {

	@Override
	public void create(CustomerModel element) {
		
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
			PreparedStatement statement = connection.prepareStatement("insert into customers (username, password) values (?, ?)");
			
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
	public CustomerModel get(String customer) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from customers where username = ?");
			
			//Step 2
			statement.setString(1, customer);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				CustomerModel c = new CustomerModel();
				c.customerNumber = rs.getInt("customer_number");
				c.username = customer; // rs.getString("username"); would be equivalent code here
				c.password = rs.getString("password");
				
				return c;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(CustomerModel element) {
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("update customers " 
					+ "set password = ?, first_name = ?, last_name = ?, address = ? "
					+ "where username = ?");
			
			statement.setString(1, element.password);
			statement.setString(5, element.username);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(CustomerModel element) {
		
		Connection connection = ConnectionManager.getConnection();
			
		try {
			PreparedStatement statement = connection.prepareStatement("delete from customers where username = ?");
		
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
			PreparedStatement statement = connection.prepareStatement("select * from customers where username = ?");
			
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
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from customers");
			
			//Step 2
			//statement.setNull(1, 1);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("customer_number") + ". " + rs.getString("username") + " " + rs.getString("password"));
			}
			/*if (rs.next()) {
				ApplicationModel app = new ApplicationModel();
				app.applicationNumber = rs.getInt("application_number"); // rs.getString("username"); would be equivalent code here
				app.applicationOwner = rs.getString("application_owner");
				
				return app;
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return null;
		
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
	public CustomerModel getCust(Integer customerNumber) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from customers where customer_number = ?");
			
			//Step 2
			statement.setInt(1, customerNumber);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				CustomerModel c = new CustomerModel();
				c.customerNumber = customerNumber;
				c.username = rs.getString("username"); // rs.getString("username"); would be equivalent code here
				c.password = rs.getString("password");
				
				return c;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public void getAllMinus(String customer) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from customers where username != ?");
			
			//Step 2
			statement.setString(1, customer);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("customer_number") + ". " + rs.getString("username"));
			}
			/*if (rs.next()) {
				ApplicationModel app = new ApplicationModel();
				app.applicationNumber = rs.getInt("application_number"); // rs.getString("username"); would be equivalent code here
				app.applicationOwner = rs.getString("application_owner");
				
				return app;
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return null;
		
	}

	public boolean existNum(int customerNumber) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from customers where customer_number = ?");
			
			//Step 2
			statement.setInt(1, customerNumber);
			
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
	public int getNum(Integer id2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
