package com.sam.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.model.Role;

public class RoleDAO implements ICRUD4DAO<Role> {

	private static final Logger logger = LoggerFactory.getLogger(RoleDAO.class);

	private final Connection connection;
	
	public RoleDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Role> getAll() {
		List<Role> roles = new ArrayList<Role>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from role");
//			System.out.println("Roles from db");
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("name"));
				roles.add(role);
//				System.out.println(role);
			}
		} catch (SQLException e) {
			logger.error("Getting all roles failed");
		}
		return roles;
	}
	
	public List<Role> getRolesByEmployee(long empId) {
		List<Role> roles = new ArrayList<Role>();
		try {
			PreparedStatement statement = connection
					.prepareStatement("select * from role where id in("
							+ "select role_id from employee_role where emp_id=?)");
			statement.setLong(1, empId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("name"));
				roles.add(role);
			}
		} catch (SQLException e) {
			logger.error(String
					.format("getRolesByEmployee failed"));
		}
		return roles;
	}

	@Override
	public Role get(long id) {
		Role role = new Role();
		role.setId(id);
		try {
			PreparedStatement statement = connection
					.prepareStatement("select * from role where id=?");
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				role.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(String
					.format("Getting employee with id %d failed", id));
		}
		return role;
	}

	@Override
	public Long create(Role role) {
		if (isRoleExists(role)) {
			logger.warn(String.format("Role %s already exists", role));
			return null;
		}
		try {
			PreparedStatement statement = connection.prepareStatement(
					"insert into role(name) values (?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, role.getName());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				logger.error(String.format(
						"Creating role %s failed, no rows affected", role));
				return null;
			}
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				role.setId(generatedKeys.getLong(1));
			} else {
				logger.error(String.format(
						"Creating role %s failed, no generated keys obtained",
						role));
				return null;
			}
		} catch (SQLException e) {
			logger.error(String.format("Creating employee %s failed", role), e);
			return null;
		}
		return role.getId();
	}
	
	private boolean isRoleExists(Role role) {
		List<Role> roles = getAll();
		return roles.contains(role);
	}

	@Override
	public void update(Role role) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("update role set name=? where id=?");
			statement.setString(1, role.getName());
			statement.setLong(2, role.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Updating employee %s failed", role));
		}

	}

	@Override
	public void delete(long id) {
		try {
			PreparedStatement statement = connection
					.prepareStatement("delete from role where id=?");
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(String.format("Deleting role with id %d failed", id));
		}
	}

}
