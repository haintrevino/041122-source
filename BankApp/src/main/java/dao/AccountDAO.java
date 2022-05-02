package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AccountModel;

public class AccountDAO implements DAOInterface<AccountModel, Integer, String> {

	@Override
	public void create(AccountModel element) {
		
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
			PreparedStatement statement = connection.prepareStatement("insert into accounts (account_owner, balance) values (?, ?)");
			
			//Step 2
			statement.setString(1, element.accountOwner);
			statement.setInt(2, 0);
			
			//Step 3
			statement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public AccountModel get(Integer account) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_number = ?");
			
			//Step 2
			statement.setInt(1, account);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			if (rs.next()) {
				AccountModel ac = new AccountModel();
				ac.accountNumber = account; // rs.getString("username"); would be equivalent code here
				ac.accountOwner = rs.getString("account_owner");
				ac.balance = rs.getInt("balance");
				
				return ac;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void update(AccountModel element) {
		Connection connection = ConnectionManager.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement("update accounts " 
					+ "set balance = ? "
					+ "where account_number = ?");
			
			statement.setInt(1, element.balance);
			statement.setInt(2, element.accountNumber);
			
			statement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(AccountModel element) {
		
		Connection connection = ConnectionManager.getConnection();
			
		try {
			PreparedStatement statement = connection.prepareStatement("delete from accounts where account_number = ?");
		
			statement.setInt(1, element.accountNumber);
			
			statement.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean exist(Integer account) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_number = ?");
			
			//Step 2
			statement.setInt(1, account);
			
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
			PreparedStatement statement = connection.prepareStatement("select * from accounts");
			
			//Step 2
			//statement.setNull(1, 1);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("account_number") + ". " + rs.getString("account_owner") + " " + rs.getInt("balance"));
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
	public void getMy(String accountOwner) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_owner like ?");
			
			//Step 2
			statement.setString(1, "%" + accountOwner + "%");
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("account_number") + ". " + rs.getString("account_owner") + " " + rs.getInt("balance"));
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
	public void getMyMinus(String owner, Integer account) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_owner like ? and account_number != ?");
			
			//Step 2
			statement.setString(1, "%" + owner + "%");
			statement.setInt(2, account);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("account_number") + ". " + rs.getString("account_owner") + " " + rs.getInt("balance"));
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
	public int getNum(String accountOwner) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_owner like ?");
			
			//Step 2
			statement.setString(1, "%" + accountOwner + "%");
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			int rowCount = 0;
			while(rs.next()) {
				rowCount++;
			}
			return rowCount;
			/*if (rs.next()) {
				ApplicationModel app = new ApplicationModel();
				app.applicationNumber = rs.getInt("application_number"); // rs.getString("username"); would be equivalent code here
				app.applicationOwner = rs.getString("application_owner");
				
				return app;
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public boolean existPlus(int account, String owner) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_owner like ? and account_number = ?");
			
			//Step 2
			statement.setString(1, "%" + owner + "%");
			statement.setInt(2, account);
			
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

	public void getAllMinus(int account) {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts where account_number != ?");
			
			//Step 2
			statement.setInt(1, account);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			while(rs.next()) {
				System.out.println(rs.getInt("account_number") + ". " + rs.getString("account_owner") + " " + rs.getInt("balance"));
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

	public int getNumAll() {
		Connection connection = ConnectionManager.getConnection();
		try {
			//Step 1
			PreparedStatement statement = connection.prepareStatement("select * from accounts");
			
			//Step 2
			//statement.setString(1, accountOwner);
			
			//Step 3
			ResultSet rs = statement.executeQuery();
			
			//Step 4
			int rowCount = 0;
			while(rs.next()) {
				rowCount++;
			}
			return rowCount;
			/*if (rs.next()) {
				ApplicationModel app = new ApplicationModel();
				app.applicationNumber = rs.getInt("application_number"); // rs.getString("username"); would be equivalent code here
				app.applicationOwner = rs.getString("application_owner");
				
				return app;
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public AccountModel getCust(String id2) {
		// TODO Auto-generated method stub
		return null;
	}

}
