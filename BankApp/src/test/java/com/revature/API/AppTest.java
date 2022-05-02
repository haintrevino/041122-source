package com.revature.API;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dao.AccountDAO;
import dao.AdminDAO;
import dao.ApplicationDAO;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import model.AccountModel;
import model.AdminModel;
import model.ApplicationModel;
import model.CustomerModel;
import model.EmployeeModel;

public class AppTest	{
    
	public CustomerDAO customerDAO = new CustomerDAO();
	public CustomerModel newCustomer = new CustomerModel();
	public EmployeeDAO employeeDAO = new EmployeeDAO();
	public EmployeeModel newEmployee = new EmployeeModel();
	public AdminDAO adminDAO = new AdminDAO();
	public AdminModel newAdmin = new AdminModel();
	public AccountDAO accountDAO = new AccountDAO();
	public AccountModel newAccount = new AccountModel();
	public ApplicationDAO applicationDAO = new ApplicationDAO();
	public ApplicationModel newApplication = new ApplicationModel();
	
    @Test
    public void customerModelTest() {
    	newCustomer.username = "bond007";
		assertEquals("bond007", newCustomer.username);
    }
    
    @Test
    public void customerDaoTest() {
    	newCustomer.username = "bond007";
    	customerDAO.create(newCustomer);
    	assertTrue(customerDAO.exist("bond007"));
    }
    
    @Test
    public void employeeModelTest() {
    	newEmployee.username = "employee";
		assertEquals("employee", newEmployee.username);
    }
    
    @Test
    public void employeeDaoTest() {
    	newEmployee.username = "employee";
    	employeeDAO.create(newEmployee);
    	assertFalse(employeeDAO.exist("password"));
    }
    
    @Test
    public void adminModelTest() {
    	newAdmin.username = "admin";
		assertEquals("admin", newAdmin.username);
    }
    
    @Test
    public void adminDaoTest() {
    	newAdmin.username = "admin";
    	adminDAO.create(newAdmin);
		assertTrue(adminDAO.exist("admin"));
    }
    
    @Test
    public void accountModelTest() {
    	newAccount.balance = 15;
    	assertEquals(15, newAccount.balance);
    }
    
    @Test
    public void accountDaoTest() {
    	newAccount.balance = 15;
    	accountDAO.create(newAccount);
    	assertFalse(accountDAO.exist(99));
    }
    
    @Test
    public void applicationModelTest() {
    	newApplication.applicationOwner = "bond007";
    	assertEquals("bond007", newApplication.applicationOwner);
    }
    
    @Test
    public void applicationDaoTest() {
    	newApplication.applicationOwner = "bond007";
    	applicationDAO.create(newApplication);
    	assertFalse(applicationDAO.exist(99));
    }
}
