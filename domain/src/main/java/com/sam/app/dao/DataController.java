package com.sam.app.dao;

import java.util.Collections;
import java.util.List;

import com.sam.app.dao.jdbc.DepartmentDAO;
import com.sam.app.dao.jdbc.RoleDAO;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;

public class DataController {
	
	private EmployeeDAO employeeDAO;
	
	private DepartmentDAO departmentDAO;
	
	private RoleDAO roleDAO;

	
	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public List<Role> getRoles() {
		return roleDAO.getAll();
	}
	
	public List<Role> getRolesByEmployee(long empId) {
//		return roleDAO.getRolesByEmployee(empId);
		return Collections.emptyList();
	}
	
	public List<Employee> getEmployeeByRole(long roleId) {
//		return employeeDAO.getEmployeeByRole(roleId);
		return Collections.emptyList();
	}
	
	public static class DataControllerBuilder {
		private EmployeeDAO employeeDAO;
		private DepartmentDAO departmentDAO;
		private RoleDAO roleDAO;

		public void setEmployeeDAO(EmployeeDAO employeeDAO) {
			this.employeeDAO = employeeDAO;
		}

		public void setDepartmentDAO(DepartmentDAO departmentDAO) {
			this.departmentDAO = departmentDAO;
		}

		public void setRoleDAO(RoleDAO roleDAO) {
			this.roleDAO = roleDAO;
		}

		public DataController build() {
			if (employeeDAO != null && departmentDAO != null && roleDAO != null) {
				DataController dataController = new DataController();
				dataController.employeeDAO = employeeDAO;
				dataController.departmentDAO = departmentDAO;
				dataController.roleDAO = roleDAO;
				return dataController;
			} else {
				return null;
			}
		}

	}

}
