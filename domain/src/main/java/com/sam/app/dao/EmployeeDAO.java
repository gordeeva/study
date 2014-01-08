package com.sam.app.dao;

import java.util.List;

import com.sam.app.model.Employee;

public interface EmployeeDAO extends ICRUD4DAO<Employee> {
	
	@Override
	List<Employee> getAll(); 
	
	@Override
	Employee get(long id);
	
	List<Employee> getEmployeeByRole(long roleId);
	
	@Override
	Long create(Employee employee);
	
	@Override
	void update(Employee employee);
	
	@Override
	void delete(long id);
	
	void addRole(long empId, long roleId);
	
	void deleteRole(long empId, long roleId);

}
