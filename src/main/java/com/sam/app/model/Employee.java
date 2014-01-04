package com.sam.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "employee")
public final class Employee implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
//	@OneToMany(mappedBy = "role")
//	private final Map<Long, Role> roles = new HashMap<>(); 
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
//	public void addRole(Role role) {
//		roles.put(role.getId(), role);
//	}
//	
//	public void removeRole(long id) {
//		roles.remove(id);
//	}
//	
//	public Collection<Role> getRoles() {
//		return roles.values();
//	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}
	
}
