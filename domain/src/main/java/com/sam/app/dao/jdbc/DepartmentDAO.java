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

import com.sam.app.dao.ICRUD4DAO;
import com.sam.app.domain.Department;

public class DepartmentDAO implements ICRUD4DAO<Department> {

	private static final Logger logger = LoggerFactory
			.getLogger(DepartmentDAO.class);

	private final Connection connection;

	public DepartmentDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Department> getAll() {
		List<Department> departments = new ArrayList<Department>();
		try {
			Statement statement = connection.createStatement();
			statement.execute("select * from department");
			ResultSet rs = statement.getResultSet();
			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getLong("id"));
				department.setName(rs.getString("name"));
				departments.add(department);
			}
		} catch (SQLException e) {
			logger.error("Getting all departments failed");
		}
		return departments;
	}

	@Override
	public Department get(long id) {
		Department department = new Department();
		department.setId(id);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select * from department where id=?");
			preparedStatement.setLong(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				department.setName(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(String.format("Getting department with id %d failed", id));
		}
		return department;
	}

	@Override
	public Long create(Department department) {
		try {
			PreparedStatement statement = connection.prepareStatement("insert into department(name) values (?)", 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, department.getName());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				logger.error(String.format("Creating department %s failed, no rows affected", department));
				return null;
			}
			ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	department.setId(generatedKeys.getLong(1));
	        } else {
	        	logger.error(String.format("Creating department %s failed, no generated keys obtained", department));
	        	return null;
	        }
		} catch (SQLException e) {
			logger.error(String.format("Creating department %s failed", department), e);
			return null;			
		}
		return department.getId();
	}

	@Override
	public void update(Department department) {
		try {
			PreparedStatement statement = connection.prepareStatement("update department set name=? where id=?");
			statement.setString(1, department.getName());
			statement.setLong(2, department.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Updating department %s failed", department));
		}
	}

	@Override
	public void delete(long id) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from department where id=?");
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Deleting department with id %d failed", id));
		}
	}

}
