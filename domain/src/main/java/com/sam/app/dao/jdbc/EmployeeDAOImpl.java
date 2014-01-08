package com.sam.app.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.dao.DataController;
import com.sam.app.dao.EmployeeDAO;
import com.sam.app.dao.hibernate.EmployeeDAOHibernate;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(EmployeeDAO.class);

	private final Connection connection;

	private DataController dataController;
	
	public EmployeeDAOImpl(Connection connection) {
		this.connection = connection;
	}

	public void setDataController(DataController dataController) {
		this.dataController = dataController;
	}

	//JDBC
	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from employee");
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getLong("id"));
				employee.setName(rs.getString("name"));
				for (Role role : dataController.getRolesByEmployee(employee
						.getId())) {
//					employee.addRole(role);
				}
				employees.add(employee);
			}
		} catch (SQLException e) {
			logger.error("Getting all employees failed");
		}
		return employees;
	}

	//JDBC
	@Override
	public List<Employee> getEmployeeByRole(long roleId) {
		List<Employee> employees = new ArrayList<Employee>();
		try {
			PreparedStatement statement = connection
					.prepareStatement("select * from employee where id in("
							+ "select emp_id from employee_role where role_id=?)");
			statement.setLong(1, roleId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setId(rs.getLong("id"));
				employee.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(String.format("getEmployeeByRole failed"));
		}
		return employees;
	}
	
	//JDBC
	@Override
	public Employee get(long id) {
		Employee employee = new Employee();
		employee.setId(id);
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from employee where id=?");
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				employee.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(String
					.format("Getting employee with id %d failed", id));
		}
		return employee;
	}
	
	//HIBERNATE
	@Override
	public Long create(Employee employee) {
		return new EmployeeDAOHibernate().create(employee);

		// try {
		// PreparedStatement preparedStatement = connection
		// .prepareStatement("insert into employee(name) values (?)",
		// Statement.RETURN_GENERATED_KEYS);
		// preparedStatement.setString(1, employee.getName());
		// int affectedRows = preparedStatement.executeUpdate();
		// if (affectedRows == 0) {
		// logger.error(String.format("Creating employee %s failed, no rows affected",
		// employee));
		// return null;
		// }
		// ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		// if (generatedKeys.next()) {
		// employee.setId(generatedKeys.getLong(1));
		// } else {
		// logger.error(String.format("Creating employee %s failed, no generated keys obtained",
		// employee));
		// return null;
		// }
		// } catch (SQLException e) {
		// logger.error(String.format("Creating employee %s failed", employee),
		// e);
		// return null;
		// }
		// return employee.getId();
	}

	//JDBC
	@Override
	public void update(Employee employee) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update employee set name=? "
							+ "where id=?");
			preparedStatement.setString(1, employee.getName());
			preparedStatement.setLong(2, employee.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Updating employee %s failed", employee));
		}
	}

	//JDBC
	@Override
	public void delete(long id) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from employee where id=?");
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Deleting employee with id %d failed",
					id));
		}
	}

	//JDBC
	@Override
	public void addRole(long empId, long roleId) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into employee_role values(?, ?)");
			preparedStatement.setLong(1, empId);
			preparedStatement.setLong(2, roleId);
			preparedStatement.executeUpdate();
			logger.info(String.format(
					"role %d for emp %d was added successfully", roleId, empId));
		} catch (SQLException e) {
			logger.error(String
					.format("Adding role id %d to employee id %d failed",
							roleId, empId));
		}
	}

	//JDBC
	@Override
	public void deleteRole(long empId, long roleId) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from employee_role where emp_id=? and role_id=?");
			preparedStatement.setLong(1, empId);
			preparedStatement.setLong(2, roleId);
			preparedStatement.executeUpdate();
			logger.info(String.format(
					"role %d for emp %d was removed successfully", roleId,
					empId));
		} catch (SQLException e) {
			logger.error(String.format(
					"Removing role id %d from employee id %d failed", roleId,
					empId));
		}
	}

}
