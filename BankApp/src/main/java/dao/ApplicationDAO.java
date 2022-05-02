package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ApplicationModel;

public class ApplicationDAO implements DAOInterface<ApplicationModel, Integer, String> {

	@Override
	public void create(ApplicationModel element) {
		
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
			PreparedStatement statement = connection.prepareStatement("insert into applications (application_owner) values (?)");
			
			//Step 2
			//statement.setInt(1, 1);
			statement.setString(1, element.applicationOwner);
			
			//Step 3
			statement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ApplicationModel get(Integer application) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from applications where application_number = ?");
			
			//Step 2
			statement.setInt(1, application);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				ApplicationModel app = new ApplicationModel();
				app.applicationNumber = application; // rs.getString("username"); would be equivalent code here
				app.applicationOwner = rs.getString("application_owner");
				
				return app;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(ApplicationModel element) {
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("update applications " 
					+ "set application_owner = ? "
					+ "where application_number = ?");
			
			statement.setString(1, element.applicationOwner);
			statement.setInt(2, element.applicationNumber);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ApplicationModel element) {
		
		Connection connection = ConnectionManager.getConnection();
			
		try {
			PreparedStatement statement = connection.prepareStatement("delete from applications where application_number = ?");
		
			statement.setInt(1, element.applicationNumber);
			
			statement.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean exist(Integer applicationNumber) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from applications where application_number = ?");
			
			//Step 2
			statement.setInt(1, applicationNumber);
			
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
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from applications");
			
			//Step 2
			//statement.setNull(1, 1);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("application_number") + ". " + rs.getString("application_owner"));
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
	public void getAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMyMinus(String id2, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNum(String id2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ApplicationModel getCust(String id2) {
		// TODO Auto-generated method stub
		return null;
	}
}
